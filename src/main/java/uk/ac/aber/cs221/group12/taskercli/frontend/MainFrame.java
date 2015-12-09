package uk.ac.aber.cs221.group12.taskercli.frontend;

import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import uk.ac.aber.cs221.group12.taskercli.business.Task;
import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;
import uk.ac.aber.cs221.group12.taskercli.util.GBC;



/**
 *
 * @author Michal Goly
 */
public class MainFrame extends JFrame {

   private SidebarPanel sidebarPanel;
   private JTable table;
   private TaskFrame taskFrame;
   private TeamMember teamMember;
   private TaskTableModel taskTableModel;
   
   public MainFrame(TeamMember teamMember) {
      this.teamMember = teamMember;
      initComponents();
      initFrame();
      taskFrame = new TaskFrame();
      taskFrame.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
   }

   private void initComponents() {
      setLayout(new GridBagLayout());
      sidebarPanel = new SidebarPanel();
      add(sidebarPanel, new GBC(0, 0, 4, 1).setWeight(0, 0)
              .setFill(GBC.BOTH));
      
      taskTableModel = new TaskTableModel(teamMember);
      table = new JTable(taskTableModel);
      table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      table.setAutoCreateRowSorter(true);
      table.addMouseListener(new MouseAdapter() {
         
         @Override
         public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            Task task = taskTableModel.getTaskAt(table.getSelectedRow());
            taskFrame.openTask(task);
         }
      });
      JScrollPane scrollPane = new JScrollPane(table);
      add(scrollPane, new GBC(4, 0, 8, 8).setFill(GBC.BOTH).setWeight(100, 100));

   }

   private void initFrame() {
      pack();
      setLocationRelativeTo(null);
      setTitle("Tasker - Welcome, " + teamMember.getFirstName());
      setVisible(true);
   }
   
   //TODO http://stackoverflow.com/questions/12559287/how-to-set-a-custom-object-in-a-jtable-row
   private class TaskTableModel extends AbstractTableModel {

      private List<Task> tasks;

      public TaskTableModel(TeamMember teamMember) {

         this.tasks = teamMember.getTaskList();

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
      public Object getValueAt(int rowIndex, int columnIndex) {

         Object value = "??";
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
         }

         return value;
      }

      @Override
      public Class<?> getColumnClass(int columnIndex) {
         return String.class;
      }

      /**
       * This will return the user at the specified row...
       *
       * @param row
       * @return
       */
      public Task getTaskAt(int row) {
         return tasks.get(row);
      }

   }
}
