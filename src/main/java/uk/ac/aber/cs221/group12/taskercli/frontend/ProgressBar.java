package uk.ac.aber.cs221.group12.taskercli.frontend;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jam on 1/23/16.
 */


public class ProgressBar extends JPanel {
    private JProgressBar progressBar;
    private JLabel textArea;
    private String status;
    private static JFrame frame;

    public ProgressBar(String newStatus){
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        status = newStatus;

        textArea = new JLabel(status);

        JPanel panel = new JPanel();
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(textArea, BorderLayout.SOUTH);

    }

    public static void showGui(String status) {
        //Create and set up the window.
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new ProgressBar(status);
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }

    public static void hideGui(){
        frame.setVisible(false);
    }

}
