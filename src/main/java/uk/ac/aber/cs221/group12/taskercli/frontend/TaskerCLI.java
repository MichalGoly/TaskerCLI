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


/**
 * The main class of the software, contains the main method. 
 * 
 * @author Michal Goly 
 * @author Joshua Mir 
 * @author Tom Mills 
 * @author Adam Neaves
 * @since 1.0 Initial Release
 * @version 1.2 Fully functional, bugs fixed
 */
public class TaskerCLI {

    /**
     * Main Method, the first to be called by loading the software
     * @param args the arguments passed to it if loaded from the command line.
     * These are not used in the current implementation.
     */
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

      EventQueue.invokeLater(new Runnable() {
         
         @Override
         public void run() {
            JFrame frame = new MainFrame(teamMember);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         }
      });
   }

}
