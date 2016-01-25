package uk.ac.aber.cs221.group12.taskercli.frontend;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;
import uk.ac.aber.cs221.group12.taskercli.logic.Syncer;

/**
 * SidebarPanel is the panel on the left side of the MainFrame. It allows the user to
 * filter through the tasks using the search box provided, and log out of the system.
 * 
 * // TODO
 * It also indicates whether there's is an access to the remote database (whether
 * the system works online/offline).
 * 
 * @author Michal Goly
 */
public class SidebarPanel extends JPanel {

   private JTable mainFrameTable;
   private TeamMember teamMember;

   private JTextField searchField;
   private JLabel taskNumberLabel;
   private TableRowSorter<TableModel> sorter;

   private JButton searchButton;
   private JButton logoutButton;
   
   
   private OnlineIndicatorPanel onlinePanel;

   public SidebarPanel(JTable mainFrameTable, TeamMember teamMember) {
      this.teamMember = teamMember;
      sorter = new TableRowSorter<TableModel>(mainFrameTable.getModel());
      mainFrameTable.setRowSorter(sorter);
      initComponents();
   }

   private void initComponents() {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      
      add(Box.createRigidArea(new Dimension(10, 20)));

      searchField = new JTextField("Search...", 200);
      add(searchField);

      searchButton = new JButton("Submit");
      searchButton.addActionListener(new SearchButtonListener());
      add(searchButton);
      
      add(Box.createRigidArea(new Dimension(10, 20)));

      taskNumberLabel
              = new JLabel("Number of tasks: " + teamMember.getTaskList().size());
      add(taskNumberLabel);
      
      add(Box.createRigidArea(new Dimension(10, 15)));

      logoutButton = new JButton("Logout");
      logoutButton.addActionListener(new LogOutButtonListener());
      add(logoutButton);
      
      add(Box.createRigidArea(new Dimension(10, 20)));
      
      onlinePanel = new OnlineIndicatorPanel();
      onlinePanel.setOnline();
      add(onlinePanel);   
   }

   private class SearchButtonListener implements ActionListener {

      @Override
      public void actionPerformed(ActionEvent e) {
         //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    	  String searchText = searchField.getText();
    	  sorter.setRowFilter(RowFilter.regexFilter(searchText, 0));
      }
   }

   private class LogOutButtonListener implements ActionListener {

      @Override
      public void actionPerformed(ActionEvent e) {
         //sync on log out
         Syncer.doUpdate(teamMember);
         System.exit(0);
      }
   }
}
