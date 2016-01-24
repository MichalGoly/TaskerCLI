package uk.ac.aber.cs221.group12.taskercli.frontend;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * ProgressBar class is responsible for creating a pop up dialog which notifies
 * the user about a possibly lengthy process of either syncing or logging into the
 * system. It provides 2 static methods which can be used to either show the 
 * dialog with provided message, or hide it. 
 * 
 * Created by jam on 1/23/16.
 */
public class ProgressBar extends JPanel {

   private JProgressBar progressBar;
   private JLabel textArea;
   private String status;
   private static JFrame frame;
   
   /**
    * Constructs the ProgressBar dialog which can be used to visually notify the user
    * about some lengthy process taking place, e.g syncing. 
    *
    * @param newStatus 
    */
   public ProgressBar(String newStatus) {
      progressBar = new JProgressBar();
      progressBar.setIndeterminate(true);

      status = newStatus;

      textArea = new JLabel(status);

      this.add(progressBar, BorderLayout.CENTER);
      this.add(textArea, BorderLayout.SOUTH);
   }

   public static void showGui(String status) {
      //Create and set up the window.
      frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

      //Create and set up the content pane.
      JComponent newContentPane = new ProgressBar(status);
      newContentPane.setOpaque(true);
      frame.setContentPane(newContentPane);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   public static void hideGui() {
      if (frame == null) {
         return;
      }
      
      frame.setVisible(false);
   }

}
