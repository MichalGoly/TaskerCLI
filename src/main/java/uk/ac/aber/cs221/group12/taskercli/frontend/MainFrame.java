package uk.ac.aber.cs221.group12.taskercli.frontend;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.*;
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

      table = new JTable(createModel(teamMember));
      table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      table.setAutoCreateRowSorter(true);
      table.addMouseListener(new MouseListener() {
         @Override
         public void mouseClicked(MouseEvent e) {
            taskFrame.openTask(teamMember.getTaskList().get(table.getSelectedRow()));
         }

         @Override
         public void mousePressed(MouseEvent e) {

         }

         @Override
         public void mouseReleased(MouseEvent e) {

         }

         @Override
         public void mouseEntered(MouseEvent e) {

         }

         @Override
         public void mouseExited(MouseEvent e) {

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

   private TableModel createModel(TeamMember teamMember) {
      Vector<String> columnNames = new Vector<>();
      columnNames.add("Task");
      columnNames.add("Start Date");
      columnNames.add("End Date");
      columnNames.add("Sub-tasks");
      columnNames.add("Status");
      
      Vector<Vector<Object>> data = new Vector<>();
      for (Task t : teamMember.getTaskList()) {
          Vector<Object> row = new Vector<>();
          row.add(t.getTitle());
          row.add(t.getStartDate());
          row.add(t.getEndDate());
          row.add(t.getTaskElementList().size());
          row.add(t.getStatus().toString());
          data.add(row);
      }

      return new DefaultTableModel(data, columnNames);
   }
   
   
}
