/*
 * @(#) MainFrame.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.frontend;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import uk.ac.aber.cs221.group12.taskercli.business.Task;
import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;
import uk.ac.aber.cs221.group12.taskercli.logic.Syncer;
import uk.ac.aber.cs221.group12.taskercli.logic.TimerManager;
import uk.ac.aber.cs221.group12.taskercli.util.GBC;

/**
 * MainFrame extends JFrame class is the primary view after the login menu, and
 * displays a TeamMember object’s Tasks in a sortable JTable, clicking on this
 * invokes TaskFrame. Also displays SidebarPanel.
 *
 * @author Michal Goly
 * @version 1.0
 * @see JFrame
 * @see Task
 * @see TeamMember
 * @see Syncer
 * @see TimerManager
 * @see GBC
 */
public class MainFrame extends JFrame {

   /**
    * Default width of the MainFrame
    */
   public static final int DEFAULT_WIDTH = 800;

   /**
    * Default width of the MainFrame
    */
   public static final int DEFAULT_HEIGHT = 600;

   private JTable table;
   private TaskFrame taskFrame;
   private volatile TeamMember teamMember;
   private TaskTableModel taskTableModel;
   private SidebarPanel sidebarPanel;

   private static MainFrame mainFrame;
   
   /**
    * Constructor
    * 
    * @param teamMember The TeamMember that logged in, whose tasks should be 
    * displayed
    */
   public MainFrame(TeamMember teamMember) {
      mainFrame = this;
      this.teamMember = teamMember;
      initComponents();
      initFrame();
      taskFrame = new TaskFrame();
      TimerManager timerManager = new TimerManager();
   }

   public static MainFrame getMainFrame() {
      return mainFrame;
   }

   public TeamMember getTeamMember() {
      return teamMember;
   }

   
   /**
    * Updates the main frame when syncing with the remote database. 
    * @param teamMember The TeamMember whose tasks should be displayed
    */
   public void updateMainFrame(TeamMember teamMember) {
      this.teamMember = teamMember;

      // update the front end
      taskTableModel.setTasksList(teamMember.getTaskList());
      taskTableModel.fireTableDataChanged();

      sidebarPanel.updateTaskCount();
   }

   /**
    * Initialises both the {@link SidebarPanel} and the {@link JTable} and 
    * positions them within the MainFrame using the GridBagLayout.
    */
   private void initComponents() {
      setLayout(new GridBagLayout());

      // setup the JTable within the MainFrame
      taskTableModel = new TaskTableModel(teamMember);
      table = new JTable(taskTableModel);
      table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      table.setAutoCreateRowSorter(true);
      table.addMouseListener(new MouseAdapter() {

         @Override
         public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            int rowIndex = table.convertRowIndexToModel(table.getSelectedRow());

            Task task = taskTableModel.getTaskAt(rowIndex);

            // display the TaskFrame populated with data about the selected Task
            taskFrame.showDialog(MainFrame.this, task);

            // The following code is invoked after user closes the TaskFrame
            task = taskFrame.getTask();

            teamMember.getTaskList().set(rowIndex, task);
            Syncer.doUpdate(teamMember);
         }
      });

      JScrollPane scrollPane = new JScrollPane(table);
      add(scrollPane, new GBC(4, 0, 8, 8).setFill(GBC.BOTH).setWeight(100, 100));

      // setup the sidebar within the MainFrame
      sidebarPanel = new SidebarPanel(table);
      add(sidebarPanel, new GBC(0, 0, 4, 1).setWeight(0, 0)
              .setFill(GBC.BOTH));
   }

   /**
    * Initialises the Frame, giving it a size and a title.
    */
   private void initFrame() {
      setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
      setLocationRelativeTo(null);
      setTitle("Tasker - Welcome, " + teamMember.getFirstName());
      setVisible(true);
   }

   // http://stackoverflow.com/questions/12559287/how-to-set-a-custom-object-in-a-jtable-row
   /**
    * Nested class that creates the table used to display the tasks.
    */
   public class TaskTableModel extends AbstractTableModel {

      private List<Task> tasks;

      /**
       * Constructor.
       * <p>
       * Create the table model for the {@link JTable} within the MainFrame and 
       * extract a list of the tasks from the provided teamMember object.
       *
       * @param teamMember the TeamMember who the tasks to display are assigned
       * to.
       */
      public TaskTableModel(TeamMember teamMember) {
         tasks = teamMember.getTaskList();
      }

      public void setTasksList(List<Task> tasks) {
         this.tasks = tasks;
      }

      @Override
      public int getRowCount() {
         return tasks.size();
      }

      @Override
      public int getColumnCount() {
         return 5;
      }

      @Override
      public String getColumnName(int columnIndex) {
         String columnName;
         switch (columnIndex) {
            case 0:
               columnName = "Title";
               break;
            case 1:
               columnName = "Start Date";
               break;
            case 2:
               columnName = "End Date";
               break;
            case 3:
               columnName = "Sub-tasks";
               break;
            case 4:
               columnName = "Status";
               break;
            default:
               columnName = "#";
               break;
         }
         return columnName;
      }

      @Override
      public Object getValueAt(int rowIndex, int columnIndex) {

         Object value;
         Task task = tasks.get(rowIndex);
         switch (columnIndex) {
            case 0:
               value = task.getTitle();
               break;
            case 1:
               value = task.getStartDate().toString();
               break;
            case 2:
               value = task.getEndDate().toString();
               break;
            case 3:
               value = task.getTaskElementList().size();
               break;
            case 4:
               value = task.getStatus().toString();
               break;
            default:
               value = null;
               break;
         }

         return value;
      }

      @Override
      public Class<?> getColumnClass(int columnIndex) {
         return String.class;
      }

      public Task getTaskAt(int row) {
         return tasks.get(row);
      }

   }
}
