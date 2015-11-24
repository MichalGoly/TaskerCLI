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
               
            } catch (SQLException | IOException e) {
               // Was not able to connect to the remote database
               // TODO let user know!
               try {
                  local = TeamMemberDB.selectTeamMemberByEmail(email.getText().trim(),
                          ConnectionManager.SQLITE);
                  if (local != null) {
                     // log in using local bob
                     teamMember = local;
                     loggedIn = true;
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
   
   public static TeamMember sync(TeamMember remote, TeamMember local) {
      TeamMember merged = null;
      
      // marge remote and local copy of Bob by considering the precedence imposed
      // in the QA Requirements Specification of the assignment
      
      // try to put merged bob into both local and remote database
      // alert the user in case of problems which can occur !
      
      return merged;
   }
   
}
