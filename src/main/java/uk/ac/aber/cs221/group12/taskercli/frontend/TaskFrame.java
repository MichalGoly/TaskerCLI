/*
 * @(#) TaskFrame.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.frontend;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import uk.ac.aber.cs221.group12.taskercli.business.Task;
import uk.ac.aber.cs221.group12.taskercli.business.TaskElement;
import uk.ac.aber.cs221.group12.taskercli.business.TaskStatus;

/**
 * TaskFrame is responsible for displaying the list of task elements of a task that
 * has been selected in the MainFrame. User can edit comments associated with each of
 * the task elements and mark the whole task as complete.
 *
 * @author Michal Goly
 * @version 1.0
 * @see JPanel
 * @see Task
 */
public class TaskFrame extends JPanel {

   private JDialog dialog;
   private Task task;

   private TaskTopPanel taskTopPanel;
   private JTable taskElementsTable;

   private JButton completeButton;
   private JButton closeButton;

   public TaskFrame() {
      initFrame();
      initComponents();
   }

   public Task getTask() {
      return task;
   }
   /**
    * Method for displaying the TaskFrame to the user. Creates the frame when
    * first run, and fills it with the information about the specified 
    * {@link task}.
    * 
    * @param parent The parent of the taskFrame so that it can load in the correct
    * position.
    * @param task The {@link task} that needs to be displayed to the user.
    */
   public void showDialog(Component parent, Task task) {
      Frame owner;

      if (parent instanceof Frame) {
         owner = (Frame) parent;
      } else {
         owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);
      }

      // if dialog showed for the first time, create the dialog, adding the
      //TaskFrame as part of it.
      if (dialog == null || dialog.getOwner() != owner) {
         dialog = new JDialog(owner, true);
         dialog.add(this);
         dialog.getRootPane().setDefaultButton(closeButton);
         dialog.pack();
         dialog.setResizable(false);
      }

      this.task = task;
      taskElementsTable.setModel(new TaskElementTableModel(task));

      taskTopPanel.getStartDate().setText(task.getStartDate().toString());
      taskTopPanel.getEndDate().setText(task.getEndDate().toString());
      taskTopPanel.getStatus().setText(task.getStatus().toString());

      if (task.getStatus() != TaskStatus.ALLOCATED) {
         completeButton.setEnabled(false);
      } else {
         completeButton.setEnabled(true);
      }

      dialog.setLocationRelativeTo(parent);
      dialog.setTitle("Tasker - " + task.getTitle());
      dialog.setVisible(true);
   }

   /**
    * Initialises the layout for the frame.
    */
   private void initFrame() {
      setLayout(new BorderLayout());
   }

   /**
    * Initialises the components used in the TestFrame, including the 
    * {@link JTable} used to show {@link TaskElement TaskElements} and the 
    * {@link JPanel} used to display the {@link JButton JButtons}.
    */
   private void initComponents() {
      taskTopPanel = new TaskTopPanel();
      add(taskTopPanel, BorderLayout.NORTH);

      // initialize task elements table
      taskElementsTable = new JTable();
      add(new JScrollPane(taskElementsTable));

      JPanel buttonPanel = initButtonPanel();
      add(buttonPanel, BorderLayout.SOUTH);
   }

   /**
    * Creates and assigns the panel for the buttons, setting the relevant actions
    * for each button
    * 
    * @return The initialised button panel, with all buttons assigned actions.
    */
   private JPanel initButtonPanel() {
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new FlowLayout(SwingUtilities.HORIZONTAL));

      completeButton = new JButton(new AbstractAction("Complete") {

         @Override
         public void actionPerformed(ActionEvent e) {
            task.setStatus(TaskStatus.COMPLETED);
            dialog.setVisible(false);
         }
      });
      buttonPanel.add(completeButton);

      closeButton = new JButton(new AbstractAction("Close") {

         @Override
         public void actionPerformed(ActionEvent e) {
            dialog.setVisible(false);
         }
      });
      buttonPanel.add(closeButton);

      return buttonPanel;
   }

   /**
    * Private inner class used to model the {@link JTable} which will display
    * the {@link TaskElement TaskElements} and comments.
    */
   private class TaskElementTableModel extends AbstractTableModel {

      private List<TaskElement> taskElements;

      /**
       * Gets the list of {@link TaskElement TaskElements} from the task 
       * selected, and stores it in the variable {@link #taskElements}.
       * 
       * @param task The task that we need to get the taskElements from.
       */
      public TaskElementTableModel(Task task) {
         taskElements = task.getTaskElementList();
      }

      @Override
      public int getRowCount() {
         return taskElements.size();
      }

      @Override
      public int getColumnCount() {
         return 2;
      }

      @Override
      public String getColumnName(int columnIndex) {
         if (columnIndex == 0) {
            return "Description";
         } else if (columnIndex == 1) {
            return "Comments";
         } else {
            return "#";
         }
      }

      @Override
      public Object getValueAt(int rowIndex, int columnIndex) {

         Object value = "??";
         TaskElement taskElement = taskElements.get(rowIndex);
         switch (columnIndex) {
            case 0:
               value = taskElement.getDescription();
               break;
            case 1:
               value = taskElement.getComments();
               break;
         }

         return value;
      }

      @Override
      public void setValueAt(Object value, int rowIndex, int columnIndex) {
         taskElements.get(rowIndex).setComments((String) value);
         fireTableCellUpdated(rowIndex, columnIndex);
      }

      /**
       * Defines whether a cell with given row and column index is editable directly
       * by the user.
       *
       * In TaskerCLI the whole 'comments' column should be editable, which
       * corresponds to the column with index 1.
       *
       * @param rowIndex The row index of the selected cell in the JTable
       * @param columnIndex The column index of the selected cell in the JTable
       * @return true for every cell in column 'comments', false otherwise
       */
      @Override
      public boolean isCellEditable(int rowIndex, int columnIndex) {
         return columnIndex == 1;
      }

      @Override
      public Class<?> getColumnClass(int columnIndex) {
         return String.class;
      }
   }
}
