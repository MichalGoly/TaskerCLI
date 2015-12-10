package uk.ac.aber.cs221.group12.taskercli.frontend;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import uk.ac.aber.cs221.group12.taskercli.business.Task;

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
      
      dialog.setLocationRelativeTo(parent);
      dialog.setTitle("Tasker - " + task.getTitle());
      dialog.setVisible(true);
   }
   
   private void initFrame() {
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
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
}
