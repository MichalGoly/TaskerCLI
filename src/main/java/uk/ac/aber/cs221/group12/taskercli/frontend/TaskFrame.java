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
 *
 * @author Michal Goly
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

   public void showDialog(Component parent, Task task) {
      Frame owner = null;

      if (parent instanceof Frame) {
         owner = (Frame) parent;
      } else {
         owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);
      }

      // if dialog showed for the first time
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
      
      dialog.setLocationRelativeTo(parent);
      dialog.setTitle("Tasker - " + task.getTitle());
      dialog.setVisible(true);
   }

   private void initFrame() {
      setLayout(new BorderLayout());
   }

   private void initComponents() {
      taskTopPanel = new TaskTopPanel();
      add(taskTopPanel, BorderLayout.NORTH);

      // initialize task elements table
      taskElementsTable = new JTable();
      add(new JScrollPane(taskElementsTable));

      JPanel buttonPanel = initButtonPanel();
      add(buttonPanel, BorderLayout.SOUTH);
   }

   private JPanel initButtonPanel() {
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new FlowLayout(SwingUtilities.HORIZONTAL));

      completeButton = new JButton(new AbstractAction("Complete") {

         @Override
         public void actionPerformed(ActionEvent e) {
            task.setStatus(TaskStatus.COMPLETED);
            setEnabled(false);
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

   private class TaskElementTableModel extends AbstractTableModel {

      private List<TaskElement> taskElements;

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
       * Defines whether a cell with given row and column index is editable
       * directly by the user. 
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
         if (columnIndex == 1) {
            return true;
         } else {
            return false;
         }
      }
      
      @Override
      public Class<?> getColumnClass(int columnIndex) {
         return String.class;
      }
   }
}
