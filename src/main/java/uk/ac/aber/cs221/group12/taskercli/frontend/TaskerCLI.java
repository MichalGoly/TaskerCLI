package uk.ac.aber.cs221.group12.taskercli.frontend;

import java.awt.EventQueue;
import javax.swing.*;

import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;
import uk.ac.aber.cs221.group12.taskercli.logic.Syncer;

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

      // log in
      TeamMember teamMember = Syncer.logIn();
      
      EventQueue.invokeLater(new Runnable() {

         @Override
         public void run() {
            JFrame frame = new MainFrame(teamMember);
            JDialog taskframe = new TaskFrame();
            taskframe.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         }
      });
   }

}
