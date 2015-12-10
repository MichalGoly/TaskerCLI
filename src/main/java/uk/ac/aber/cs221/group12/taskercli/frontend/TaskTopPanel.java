/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.aber.cs221.group12.taskercli.frontend;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Michal Goly
 */
public class TaskTopPanel extends JPanel {

   private JLabel startDate;
   private JLabel endDate;
   private JLabel status;

   public TaskTopPanel() {
      initComponents();
   }

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
