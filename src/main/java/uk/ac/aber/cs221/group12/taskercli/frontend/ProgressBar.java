package uk.ac.aber.cs221.group12.taskercli.frontend;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.*;

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
   private static JDialog frame;
   
   /**
    * Constructs the ProgressBar dialog which can be used to visually notify the user
    * about some lengthy process taking place, e.g syncing. 
    *
    * @param newStatus the text to display in the dialog box
    */
   public ProgressBar(String newStatus) {
      progressBar = new JProgressBar();
      progressBar.setIndeterminate(true);

      status = newStatus;

      textArea = new JLabel(status);

      this.add(progressBar, BorderLayout.CENTER);
      this.add(textArea, BorderLayout.SOUTH);
   }
   
/**
 * Displays the Progress bar dialog box, either creating a new one or changing info
 * in the one already created
 * @param status The text to display in the dialog box
 */
   public static void showGui(final String status) {
	   //Create and set up the window.
	   //eventQueue.invoke later allows the progress bar to load properly before being shown.
	   EventQueue.invokeLater(new Runnable() {

	         @Override
	         public void run() {
	        	 if(frame == null){
	      		   System.out.println("CREATE NEW FRAME");
	      		   frame = new JDialog();
	      		   frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	      		   Container contents = new ProgressBar(status);
	      		   frame.setContentPane(contents);
	      		   frame.pack();
	      		   frame.setLocationRelativeTo(null);
	      	   }else{
	      		   ((ProgressBar) frame.getContentPane()).setTextArea(status);
	      	   }
	      	   frame.setVisible(true);
	         }
	      });
	   
   }
   /**
    * Hides the GUI from view by making it invisible
    */
   public static void hideGui() {
	   EventQueue.invokeLater(new Runnable() {

	         @Override
	         public void run() {
	        	 	System.out.println("REMOVING DIALOG BOX");
	        	 	if (frame == null) {
	        	 			return;
	        	 	}
	        	 	frame.setVisible(false);
	         }
	   });
   }
   
   public void setTextArea(String status){
	   this.textArea.setText(status);
   }
}
