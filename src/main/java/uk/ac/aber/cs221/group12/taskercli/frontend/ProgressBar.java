/*
 * @(#) ProgressBar.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.frontend;

import java.awt.Dialog;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * ProgressBar class is responsible for creating a pop up dialog which notifies the
 * user about a possibly lengthy process of either syncing or logging into the
 * system. It provides 2 static methods which can be used to either show the dialog
 * with provided message, or hide it.
 *
 * Created by jam on 1/23/16.
 */
public class ProgressBar extends JPanel {

   private static JDialog dialog;

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

   public static void hideGui() {
      if (dialog == null) {
         return;
      }
      dialog.setVisible(false);
   }

}
