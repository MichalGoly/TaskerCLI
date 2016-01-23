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

    public ProgressBar(){
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        status = new String();

        textArea = new JLabel(status);

        JPanel panel = new JPanel();
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(textArea, BorderLayout.SOUTH);
    }


}
