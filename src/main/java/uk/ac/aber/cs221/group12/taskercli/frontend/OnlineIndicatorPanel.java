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

/**
 * The panel that displays the indicator to inform the user if they are connected
 * to the remote database. It is placed inside the {@link SidebarPanel}
 * 
 * @author Tom Mills
 * @author Josh Mir
 * @version 1.0 Initial Development
 */
public class OnlineIndicatorPanel extends JPanel {

   /**
    * Create the panel.
    */
   private static JLabel label = new JLabel();

   /**
    * Constructor. Calls the {@link #initialise() initialise} method
    */
   public OnlineIndicatorPanel() {
      initialise();
   }

   /**
    * Called by the constructor, initialises the layout of the {@link JPanel}
    * and adds the label to it.
    */
   public void initialise() {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      add(label);
   }
   
   /**
    * Sets the label to show it is connected to the online database with green 
    * coloured text.
    */
   public static void setOnline() {
      label.setText("Online");
      label.setFont(label.getFont().deriveFont(15f));
      label.setForeground(Color.green);
      label.repaint();
   }

   /**
    * Sets the label to show it is not connected to the online database with 
    *  red coloured text.
    */
   public static void setOffline() {
      label.setText("Offline");
      label.setFont(label.getFont().deriveFont(15f));
      label.setForeground(Color.red);
      label.repaint();
   }
}
