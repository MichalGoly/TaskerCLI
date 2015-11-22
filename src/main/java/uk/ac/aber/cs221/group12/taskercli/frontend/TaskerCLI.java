package uk.ac.aber.cs221.group12.taskercli.frontend;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class TaskerCLI {

   public static void main(String[] args) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception e) {
         try {
            UIManager.setLookAndFeel(UIManager
                    .getCrossPlatformLookAndFeelClassName());
         } catch (Exception ex) {
            System.exit(-1);
         }
      }

      EventQueue.invokeLater(new Runnable() {

         @Override
         public void run() {
            JFrame frame = new MainFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         }
      });
   }

}
