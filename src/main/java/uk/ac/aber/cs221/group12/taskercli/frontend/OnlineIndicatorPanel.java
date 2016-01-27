/*
 * @(#) OnlineIndicatorPanel.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.frontend;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OnlineIndicatorPanel extends JPanel {

   /**
    * Create the panel.
    */
   private static JLabel label = new JLabel();

   public OnlineIndicatorPanel() {
      initialise();
   }

   public void initialise() {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      add(label);
   }

   public static void setOnline() {
      System.out.println("setting online");
      label.setText("Online");
      label.setFont(label.getFont().deriveFont(15f));
      label.setForeground(Color.green);
      label.repaint();
   }

   public static void setOffline() {
      System.out.println("setting offline");
      label.setText("Offline");
      label.setFont(label.getFont().deriveFont(15f));
      label.setForeground(Color.red);
      label.repaint();
   }
}
