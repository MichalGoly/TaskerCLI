/*
 * @(#) TaskerCLI.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.frontend;

import java.awt.EventQueue;
import javax.swing.*;

import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;
import uk.ac.aber.cs221.group12.taskercli.logic.Syncer;

public class TaskerCLI {

   public static void main(String[] args) {
      // set look and feel of the GUI depending on the OS of the user
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception e) {
         try {
            UIManager.setLookAndFeel(UIManager
                    .getCrossPlatformLookAndFeelClassName());
         } catch (Exception ex) {
            System.exit(-1);
         }
      }

      // log in
      final TeamMember teamMember = Syncer.logIn();

      EventQueue.invokeLater(() -> {
         JFrame frame = new MainFrame(teamMember);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      });
   }

}
