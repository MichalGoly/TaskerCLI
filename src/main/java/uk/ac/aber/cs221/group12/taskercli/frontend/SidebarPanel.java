package uk.ac.aber.cs221.group12.taskercli.frontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;
import uk.ac.aber.cs221.group12.taskercli.logic.Syncer;

/**
 *
 * @author Michal Goly
 */
public class SidebarPanel extends JPanel {

   private JTable mainFrameTable;
   private TeamMember teamMember;
   
   private JTextField searchField;
   private JLabel taskNumberLabel;

   private JButton searchButton;
   private JButton returnButton;
   private JButton logoutButton;

   public SidebarPanel(JTable mainFrameTable, TeamMember teamMember) {
      this.mainFrameTable = mainFrameTable;
      this.teamMember = teamMember;
      initComponents();
   }

   private void initComponents() {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

      searchField = new JTextField("Search...", 200);
      add(searchField);

      searchButton = new JButton("Submit");
      searchButton.addActionListener(new SearchButtonListener());
      add(searchButton);

      returnButton = new JButton("Return");
      returnButton.addActionListener(new ReturnButtonListener());
      add(returnButton);

      taskNumberLabel = new JLabel("Number of tasks: " + "2");
      add(taskNumberLabel);

      logoutButton = new JButton("Logout");
      logoutButton.addActionListener(new LogOutButtonListener());
      add(logoutButton);
   }

   private class SearchButtonListener implements ActionListener {

      @Override
      public void actionPerformed(ActionEvent e) {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }
   }

   private class ReturnButtonListener implements ActionListener {

      @Override
      public void actionPerformed(ActionEvent e) {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
