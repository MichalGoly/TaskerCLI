/*
 * @(#) Syncer.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.logic;

import java.awt.GridLayout;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import uk.ac.aber.cs221.group12.taskercli.business.Task;
import uk.ac.aber.cs221.group12.taskercli.business.TaskElement;
import uk.ac.aber.cs221.group12.taskercli.business.TaskStatus;
import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;
import uk.ac.aber.cs221.group12.taskercli.data.ConnectionManager;
import uk.ac.aber.cs221.group12.taskercli.data.TaskDB;
import uk.ac.aber.cs221.group12.taskercli.data.TaskElementDB;
import uk.ac.aber.cs221.group12.taskercli.data.TeamMemberDB;
import uk.ac.aber.cs221.group12.taskercli.frontend.MainFrame;
import uk.ac.aber.cs221.group12.taskercli.frontend.OnlineIndicatorPanel;
import uk.ac.aber.cs221.group12.taskercli.frontend.ProgressBar;

/**
 * Syncer class contains the logic for conflict resolution between the contents
 * of the two databases within its {@link #sync(TeamMember, TeamMember)} method. 
 * The sync method takes two {@link TeamMember} objects, generates a canonical 
 * TeamMember object, which is supplied to the databases and UI. The Syncer class 
 * also contains the {@link logIn()} method,displaying a dialog for user login 
 * and returning an appropriate TeamMember object.
 *
 * @author Michal Goly 
 * @author Joshua Mir 
 * @author Tom Mills 
 * @author Adam Neaves
 * @version 1.0 Initial Release
 * 
 * @see ConnectionManager
 * @see TaskDB
 * @see TaskElementDB
 * @see TeamMemberDB
 * @see TeamMember
 */
public class Syncer {

   /**
    * Logs into the system by asking the user for email and password. This method
    * will pop up a JDialogBox to achieve it and will keep popping it up as long as
    * the user does not manage to log in.
    *
    * @return The TeamMember object of the logged in team member
    */
   public static TeamMember logIn() {
      TeamMember teamMember = null;
      boolean loggedIn = false;

      while (!loggedIn) {
         JPanel inputPanel = new JPanel();
         JLabel emailLabel = new JLabel("Email");
         JTextField email = new JTextField(10);
         JLabel passwordLabel = new JLabel("Password");
         JPasswordField password = new JPasswordField(10);

         inputPanel.setLayout(new GridLayout(2, 2));
         inputPanel.add(emailLabel);
         inputPanel.add(email);
         inputPanel.add(passwordLabel);
         inputPanel.add(password);

         int result = JOptionPane.showConfirmDialog(null, inputPanel,
                 "Please log in", JOptionPane.OK_CANCEL_OPTION,
                 JOptionPane.PLAIN_MESSAGE);

         if (result == JOptionPane.OK_OPTION) {
            // pop up the loading dialog
            ProgressBar.showGui("Logging in...");

            /*
             1. Try to select a team member from the remote Database
             2. If succeed:
               - Retrieve local copy
               - Check if they are equal
                  + log in 
                  - sync and log in using merged Bob :)
             3. If unsuccessful:
               - Log in using the local Bob
             */
            TeamMember remote;
            TeamMember local;

            try {
               remote = TeamMemberDB.selectTeamMemberByEmail(email.getText().trim(),
                       ConnectionManager.MYSQL);
               if (remote != null) {
                  // retireve local copy
                  local = TeamMemberDB.selectTeamMemberByEmail(email.getText().trim(),
                          ConnectionManager.SQLITE);
                  OnlineIndicatorPanel.setOnline();

                  if (local != null) {
                     // check if they're equal
                     if (remote.equals(local)) {
                        // log in using remote or local, does not matter
                        teamMember = remote;
                        loggedIn = Authenticator.authenticate(teamMember,
                                password.getPassword());
                     } else {
                        // sync and log in using merged Bob
                        teamMember = sync(remote, local);
                        loggedIn = Authenticator.authenticate(teamMember,
                                password.getPassword());
                     }
                  } else {
                     // local copy with this email does not exist, sync to put it 
                     // in the local db
                     teamMember = sync(remote, null);
                     loggedIn = Authenticator.authenticate(teamMember,
                             password.getPassword());
                  }
               } else {
                  // TeamMember with this email address does not exist remotely
                  // but there is a connection to the remote database. It is also
                  // possible that the TeamMember with specified email address
                  // exists in the local database so we need to clean it
                  TeamMember dummy = TeamMemberDB.selectTeamMemberByEmail(
                          email.getText(), ConnectionManager.SQLITE);
                     
                  if (dummy != null) {
                     // clean up
                     TeamMemberDB.deleteTeamMember(dummy, ConnectionManager.SQLITE);
                  }
                  JOptionPane.showMessageDialog(null, "Invalid email or password",
                             null, JOptionPane.ERROR_MESSAGE);
               }

            } catch (SQLException | IOException e) {
               // Was not able to connect to the remote database, or remote = null
               OnlineIndicatorPanel.setOffline();
               try {
                  local = TeamMemberDB.selectTeamMemberByEmail(email.getText().trim(),
                          ConnectionManager.SQLITE);
                  if (local != null) {
                     // log in using local bob
                     teamMember = local;
                     loggedIn = Authenticator.authenticate(teamMember,
                             password.getPassword());
                  } else {
                     // Team Member with given email address does not exist
                     // ignore and wait for new credentials
                     JOptionPane.showMessageDialog(null, "Invalid email or password",
                             null, JOptionPane.ERROR_MESSAGE);
                  }
               } catch (SQLException | IOException ex) {
                  JOptionPane.showMessageDialog(null,
                          "Not able to connect to either remote or local DB!");
               }
            }
         } else {
            System.exit(0);
         }

         // hide the loading dialog
         ProgressBar.hideGui();
      }

      return teamMember;
   }

   /**
    * Merge remote and local copy of Bob by considering the precedence imposed in the
    * QA Requirements Specification of the assignment try to put merged bob into both
    * local and remote database alert the user in case of problems which can occur !
    *
    * NOTE: if either of the arguments passed to the method is null, the not null
    * object will be put to both db and treated as the merged one.
    *
    * @param remote The TeamMember object retrieved from remote DB, or null
    * @param local The TeamMember object retrieved from the local DB, or null
    * @return The merged TeamMember object, null if two arguments are null
    */
   public static TeamMember sync(TeamMember remote, TeamMember local) {
      TeamMember merged;

      if (remote == null && local == null) {
          //Neither TeamMembers to be merged are set, so the merged TeamMember
          //will also just be null
         merged = null;
      } else if (remote == null) {
         //remote teamMember was not set, merged TeamMember is just a copy of 
         //the local
         merged = local;

         try {
            TeamMemberDB.insertTeamMember(merged, ConnectionManager.MYSQL);
         } catch (SQLException | IOException e) {
           // no connection to the remote
           //Inform the user
            OnlineIndicatorPanel.setOffline();
            System.err.println("NO CONNECTION TO REMOTE DATABASE");
         }
      } else if (local == null) {
        //Local TeamMember not set, merged teamMember will just be a copy of
        //the remote teamMember
          merged = remote;
        
         try {
            TeamMemberDB.insertTeamMember(merged, ConnectionManager.SQLITE);
         } catch (SQLException | IOException e) {
            // should never happen
            System.err.println("NO CONNECTION TO LOCAL DATABASE");
            e.printStackTrace();
         }
      } else {
         if (remote.equals(local)) {
            merged = remote;
         } else {
            merged = merge(remote, local);
            // update merged Bob in both databases
            try {
               TeamMemberDB.updateTeamMember(merged, ConnectionManager.SQLITE);
               TeamMemberDB.updateTeamMember(merged, ConnectionManager.MYSQL);
            } catch (SQLException | IOException e) {
               System.err.println("ERROR UPDATING TEAM MEMBERS");
               e.printStackTrace();
            }
         }
      }

      return merged;
   }

   /**
    * Actual merging of the remote and the local copy of Bob by considering the
    * precedence imposed in the QA Requirements Specification of the assignment. 
    * Data to merge from the local copy is acquired from the local database, 
    * rather than from the passed object.
    *
    * @param remote The TeamMember object from the remote database.
    * @param local The TeamMember object from the local database.
    * @return The merged TeamMember object.
    */
   private static TeamMember merge(TeamMember remote, TeamMember local) {
      //First sets the information for the merged member that should be the
      //same between both
      TeamMember merged = new TeamMember();
      merged.setFirstName(remote.getFirstName());
      merged.setLastName(remote.getLastName());
      merged.setEmail(remote.getEmail());
      merged.setPassword(remote.getPassword());
      
      //Get the task list from the remote teamMember, and compare each task with
      //matching tasks from the local database, changing state and comments for
      //both databases as required in the precedence specifed.
      merged.setTaskList(remote.getTaskList());

      for (Task t : merged.getTaskList()) {
         try {
            Task localTask = TaskDB.selectTaskById(t.getTaskId(),
                    ConnectionManager.SQLITE);

            if (localTask != null) {
               //if the task also exists in the local database we need to make
               //sure the status is changed corretly, so if the user has updated
               //the task to completed, this status is copied to the merged task
               //list.
               TaskStatus localStatus = localTask.getStatus();

               if (localStatus == TaskStatus.COMPLETED
                       && t.getStatus() == TaskStatus.ALLOCATED) {
                  t.setStatus(TaskStatus.COMPLETED);
               }
            }
         } catch (SQLException | IOException ex) {
            // task with the specified id does not exist, so we don't need to
            // alter anything about the task, as we can just copy it from the
            //remote DB directly.
         }

         for (TaskElement te : t.getTaskElementList()) {
            try {
               TaskElement localTaskEle = TaskElementDB
                       .selectTaskElementById(te.getTaskElementId(),
                               ConnectionManager.SQLITE);
               if (localTaskEle != null) {
                  //add any comments to the task element that have been added
                  //locally to the merged task.
                  String comment = localTaskEle.getComments();
                  if (comment != null) {
                     te.setComments(comment);
                  }
               }
            } catch (SQLException | IOException e) {
               // fail silently <3
            }

         }
      }

      return merged;
   }

   /**
    * This method can be used to invoke the synchronisation of the current 
    * {@link TeamMember} object between both the local (SQLite) and the remote 
    * (TaskerSRV) databases. It does this using the 
    * {@link #sync(TeamMember, TeamMember)} method after getting the TeamMember
    * from each database using {@link TeamMemberDB}.
    * <p>
    * If the remote database cannot be connected to, the update will occur only
    * to the local database.
    *
    * @param editedTeamMember The TeamMember that needs to be updated within
    * both databases.
    */
   public static void doUpdate(TeamMember editedTeamMember) {
      
      Executor executor = Executors.newSingleThreadExecutor();
      executor.execute(new Runnable() {
         
         @Override
         public void run() {
            ProgressBar.showGui("Syncing");
            
            try {
               TeamMemberDB.updateTeamMember(editedTeamMember, ConnectionManager.SQLITE);
            } catch (SQLException | IOException e) {
               e.printStackTrace();
            }
            
            try {
               TeamMember remote = TeamMemberDB.selectTeamMemberByEmail(
                       editedTeamMember.getEmail(), ConnectionManager.MYSQL);
               if (remote != null) {
                  TeamMember t = sync(remote, editedTeamMember);
                  MainFrame.getMainFrame().updateMainFrame(t);
                  OnlineIndicatorPanel.setOnline();
               } else {
                  // unable to connect to the database
                  OnlineIndicatorPanel.setOffline();
               }
            } catch (SQLException | IOException e) {
               // TeamMember with this email address does not exit or there was a problem
               // with getting the connection
               OnlineIndicatorPanel.setOffline();
               System.err.println("No access to the remote database!");
               //e.printStackTrace();
            }
            
            ProgressBar.hideGui();
         }
      });

   }

}
