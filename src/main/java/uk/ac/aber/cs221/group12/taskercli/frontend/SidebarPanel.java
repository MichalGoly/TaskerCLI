package uk.ac.aber.cs221.group12.taskercli.frontend;

import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author Michal Goly
 */
public class SidebarPanel extends JPanel {
   
   public static final int DEFAULT_WIDTH = 200;
   public static final int DEFAULT_HEIGHT = 600;
   
   public SidebarPanel() {
      setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
   }
   
}
