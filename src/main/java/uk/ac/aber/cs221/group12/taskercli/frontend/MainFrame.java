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
import uk.ac.aber.cs221.group12.taskercli.util.GBC;

/**
 *
 * @author Michal Goly
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
            int rowIndex = table.getSelectedRow();
            Task task = taskTableModel.getTaskAt(rowIndex);
            taskFrame.showDialog(MainFrame.this, task);
            
            task = taskFrame.getTask();
            taskTableModel.tasks.set(rowIndex, task);
            taskTableModel.fireTableDataChanged();
         }
      });
      
      JScrollPane scrollPane = new JScrollPane(table);
      add(scrollPane, new GBC(4, 0, 8, 8).setFill(GBC.BOTH).setWeight(100, 100));
   }

   private void initFrame() {
      setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
      setLocationRelativeTo(null);
      setTitle("Tasker - Welcome, " + teamMember.getFirstName());
      setVisible(true);
   }

   //TODO http://stackoverflow.com/questions/12559287/how-to-set-a-custom-object-in-a-jtable-row
   private class TaskTableModel extends AbstractTableModel {

      private List<Task> tasks;

      public TaskTableModel(TeamMember teamMember) {
         tasks = teamMember.getTaskList();
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
