/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.aber.cs221.group12.taskercli.logic;

import java.awt.GridLayout;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;
import uk.ac.aber.cs221.group12.taskercli.data.ConnectionManager;
import uk.ac.aber.cs221.group12.taskercli.data.TeamMemberDB;

/**
 *
 * @author Michal Goly
 */
public class Syncer {

   /**
    * Logs into the system by asking the user for email and password. This method
    * will pop up a JDialogBox to achieve it and will keep popping it up as long as
    * the user manages to log in.
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

                  if (local != null) {
                     // check if they're equal
                     if (remote.equals(local)) {
                        // log in using remote or local, does not matter
                        teamMember = remote;
                        loggedIn = true;
                     } else {
                        // sync and log in using merged Bob
                        teamMember = sync(remote, local);
                        loggedIn = true;
                     }
                  } else {
                     // local copy with this email does not exist, sync to put it 
                     // in the local db
                     teamMember = sync(remote, null);
                     loggedIn = true;
                  }
               } else {
                  // TeamMember with this email address does not exist

                  // dodgy way to get into the second branch, ignore the spaghetti
                  // code for now
                  throw new IOException();
               }

            } catch (SQLException | IOException e) {
               // Was not able to connect to the remote database, or remote = null
               // TODO let user know!
               try {
                  local = TeamMemberDB.selectTeamMemberByEmail(email.getText().trim(),
                          ConnectionManager.SQLITE);
                  if (local != null) {
                     // log in using local bob
                     teamMember = local;
                     loggedIn = true;
                  } else {
                     // Team Member with given email address does not exist
                     // ignore and wait for new credentials
                  }
               } catch (SQLException | IOException ex) {
                  JOptionPane.showMessageDialog(null,
                          "Not able to connect to either remote or local DB!");
               }
            }
         } else {
            System.exit(0);
         }
      }

      return teamMember;
   }

   /**
    * Marge remote and local copy of Bob by considering the precedence imposed in the
    * QA Requirements Specification of the assignment try to put merged bob into both
    * local and remote database alert the user in case of problems which can occur !
    *
    * NOTE: if either of the arguments passed to the method is null, the not null
    * object will be put to both db and treated as the merged one.
    *
    * @param remote The TeamMember object retrieved from remote DB, or null
    * @param local The TeamMember object retrieved from the local DB, or null
    * @return The merged TeamMember object
    */
   public static TeamMember sync(TeamMember remote, TeamMember local) {
      TeamMember merged = null;
      
      if (remote == null && local == null) {
         return null;
      } else if (remote == null) {
         merged = local;
         
      } else if (local == null) {
         merged = remote;
         
      } else {
         
      }
      
      return merged;

   }

}
