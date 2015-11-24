package uk.ac.aber.cs221.group12.taskercli.frontend;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Michal Goly
 */
public class SidebarPanel extends JPanel {
   
//   public static final int DEFAULT_WIDTH = 200;
//   public static final int DEFAULT_HEIGHT = 600;
   
   private JTextField searchField;
   private JButton searchButton;
   private JButton returnButton;
   //private JComboBox sortComboBox;
   private JLabel taskNumberLabel;
   private JButton openButton;
   private JButton logoutButton;
   
   public SidebarPanel() {
      initComponents();
   }
   
   private void initComponents() {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      
      searchField = new JTextField("Search...", 200);
      add(searchField);
      
      searchButton = new JButton(new AbstractAction("Submit") {

         @Override
         public void actionPerformed(ActionEvent e) {
            //TODO
         }
      });
      add(searchButton);
      
      returnButton = new JButton(new AbstractAction("Return") {

         @Override
         public void actionPerformed(ActionEvent e) {
            //TODO
         }
      });
      add(returnButton);
      
      taskNumberLabel = new JLabel("Number of tasks: " + "3");
      add(taskNumberLabel);
      
      openButton = new JButton(new AbstractAction("Open") {

         @Override
         public void actionPerformed(ActionEvent e) {
            //TODO
         }
      });
      add(openButton);
      
      logoutButton = new JButton(new AbstractAction("Logout") {

         @Override
         public void actionPerformed(ActionEvent e) {
            System.exit(0);
         }
      });
      add(logoutButton);
      
   }
   
}
