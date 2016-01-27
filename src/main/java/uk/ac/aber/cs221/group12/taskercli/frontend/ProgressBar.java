/*
 * @(#) ProgressBar.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.frontend;

import javax.swing.*;
import java.awt.*;

/**
 * ProgressBar class is responsible for creating a pop up dialog which notifies the
 * user about a possibly lengthy process of either syncing or logging into the
 * system. It provides 2 static methods which can be used to either show the dialog
 * with provided message, or hide it.
 *
 * @author Joshua Mir
 * @version 1.0
 */
public class ProgressBar extends JPanel {

   private static JDialog dialog;

   /**
    * Shows the {@link JDialog} that displays the {@link JProgressBar}. Also
    * sets the progress bar to indeterminate mode so that it does not try to 
    * show accurate progress, and gives the dialog a title depending on what 
    * action is happening.
    * 
    * @param status The name of the current action taken by the software that 
    * requires a progress bar, to inform the user what is happening.
    */
   public static void showGui(String status) {
      if (dialog == null) {
         JProgressBar progressBar = new JProgressBar();
         progressBar.setIndeterminate(true);

         dialog = new JDialog();
         dialog.setModalityType(Dialog.ModalityType.MODELESS);
         dialog.add(progressBar);
         dialog.pack();
         dialog.setLocationRelativeTo(null);
         dialog.setResizable(false);
      }

      dialog.setTitle(status);
      dialog.setVisible(true);
   }

   /**
    * Hides the progress bar. Is usually called when whatever action has finished.
    */
   public static void hideGui() {
      if (dialog == null) {
         return;
      }
      dialog.setVisible(false);
   }

}
