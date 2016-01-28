/*
 * @(#) SidebarPanel.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.frontend;

import uk.ac.aber.cs221.group12.taskercli.business.Task;
import uk.ac.aber.cs221.group12.taskercli.business.TaskStatus;
import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;
import uk.ac.aber.cs221.group12.taskercli.logic.Syncer;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * SidebarPanel is the panel on the left side of the MainFrame. It allows the user to
 * filter through the tasks using the search box provided, and log out of the system.
 * It also uses {@link OnlineIndicatorPanel} to tell the user the state of their
 * connection to the remote database, and an indicator that displays the total
 * number of tasks allocated to the teamMember.
 *
 * @author Michal Goly
 * @author Tom Mills
 * @version 1.0
 * @see JPanel
 */
public class SidebarPanel extends JPanel {
    
   private TeamMember teamMember;

   private JTextField searchField;
   private JLabel taskNumberLabel;
   private TableRowSorter<TableModel> sorter;

   private JButton searchButton;
   private JButton viewAllTasks;
   private JButton logoutButton;

   private OnlineIndicatorPanel onlinePanel;

   /**
    * Constructor
    * <p>
    * Creates the SidebarPanel, and also a {@link TableRowSorter} to filter the
    * tasks, so that only tasks with the status ALLOCATED are shown to the user.
    * 
    * @param mainFrameTable The table that the tasks are shown in
    * @param teamMember The team member whose tasks need to be shown.
    */
    public SidebarPanel(JTable mainFrameTable, TeamMember teamMember) {
        this.teamMember = teamMember;
        sorter = new TableRowSorter<TableModel>(mainFrameTable.getModel());
        mainFrameTable.setRowSorter(sorter);
        initComponents();
        sorter.setRowFilter(RowFilter.regexFilter("ALLOCATED"));
    }

    /**
     * This method is used to update the label which displays the number of tasks.
     */
   public void updateTaskCount() {
      int numberAllocatedTasks = teamMember.getTaskList().size();

      for (Task task : teamMember.getTaskList()) {
         if (task.getStatus() != TaskStatus.ALLOCATED) {
            numberAllocatedTasks -= 1;
         }
      }

      taskNumberLabel.setText("Number of Tasks: " + numberAllocatedTasks);
   }

   /**
    * Sets the layout of the GUI components, adding all buttons and initialising
    * any objects required.
    */
   private void initComponents() {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

      add(Box.createRigidArea(new Dimension(10, 20)));

      searchField = new JTextField("Search...", 200);
      add(searchField);

      searchButton = new JButton("Submit");
      searchButton.addActionListener(new SearchButtonListener());
      add(searchButton);

      add(Box.createRigidArea(new Dimension(10, 20)));

      taskNumberLabel = new JLabel();
      updateTaskCount();
      add(taskNumberLabel);

      add(Box.createRigidArea(new Dimension(10, 15)));

      viewAllTasks = new JButton("View All Tasks");
      viewAllTasks.addActionListener(new ViewAllTasksListener());
      add(viewAllTasks);

      add(Box.createRigidArea(new Dimension(10, 15)));

      logoutButton = new JButton("Logout");
      logoutButton.addActionListener(new LogOutButtonListener());
      add(logoutButton);

      add(Box.createRigidArea(new Dimension(10, 20)));

      onlinePanel = new OnlineIndicatorPanel();
      add(onlinePanel);
   }
/**
 * Inner class used to set listeners for the search button, so that clicking it
 * filters the table displaying {@link Task Tasks} with the search term entered.
 * 
 */
   private class SearchButtonListener 
   implements ActionListener {
       
      @Override
      public void actionPerformed(ActionEvent e) {
         String searchText = searchField.getText();
         List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>();
         filters.add(RowFilter.regexFilter("ALLOCATED"));
         filters.add(RowFilter.regexFilter("(?i)" + searchText));
         sorter.setRowFilter(RowFilter.andFilter(filters));
      }
   }

   /**
    * Inner class used to set listeners for the 'View All Tasks' button on the 
    * SidebarPanel, so that clicking it displays all allocated tasks.
    */
   private class ViewAllTasksListener 
   implements ActionListener {

      @Override
      public void actionPerformed(ActionEvent e) {
         sorter.setRowFilter(RowFilter.regexFilter("ALLOCATED"));
      }
   }

   /**
    * Inner class used to set listeners for the 'Log Out' button on the
    * SidebarPanel. 
    */
   private class LogOutButtonListener 
   implements ActionListener {

      @Override
      public void actionPerformed(ActionEvent e) {
         //sync on log out
         Syncer.doUpdate(teamMember);
         System.exit(0);
      }
   }
}
