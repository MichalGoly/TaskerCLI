/*
 * @(#) TaskTopPanel.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.frontend;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * TaskTopPanel is responsible for displaying the details (start date, end date and
 * status) of a task within the TaskFrame. TaskTopPanel is situated on the top of the
 * TaskFrame.
 *
 * @author Michal Goly 
 * @author Joshua Mir 
 * @author Tom Mills 
 * @author Adam Neaves
 * @version 1.0 Initial Release
 * 
 * @see JPanel
 * @see Task
 */
public class TaskTopPanel extends JPanel {

   private JLabel startDate;
   private JLabel endDate;
   private JLabel status;

   /**
    * Calls the {@link #initComponents()} method, creating the panel.
    */
   public TaskTopPanel() {
      initComponents();
   }

   /**
    * Creates and formats the components for the TopPanel. Includes 
    * {@link Jlabel Jlabels} for the start and end date, as well as the status.
    */
   private void initComponents() {
      setLayout(new FlowLayout(SwingUtilities.HORIZONTAL));

      JLabel startDateLabel = new JLabel("Start date:");
      startDate = new JLabel();
      JLabel endDateLabel = new JLabel("End date:");
      endDate = new JLabel();
      JLabel statusLabel = new JLabel("Status: ");
      status = new JLabel();

      add(startDateLabel);
      add(startDate);
      add(endDateLabel);
      add(endDate);
      add(statusLabel);
      add(status);
   }

   public JLabel getStartDate() {
      return startDate;
   }

   public JLabel getEndDate() {
      return endDate;
   }

   public JLabel getStatus() {
      return status;
   }

   public void setStartDate(JLabel startDate) {
      this.startDate = startDate;
   }

   public void setEndDate(JLabel endDate) {
      this.endDate = endDate;
   }

   public void setStatus(JLabel status) {
      this.status = status;
   }

}
