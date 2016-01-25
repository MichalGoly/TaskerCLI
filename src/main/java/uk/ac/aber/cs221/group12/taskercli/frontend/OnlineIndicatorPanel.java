package uk.ac.aber.cs221.group12.taskercli.frontend;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OnlineIndicatorPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	
	private static JLabel label = new JLabel();
	
	public OnlineIndicatorPanel() {
		initialise();
	}
	
	
	public void initialise(){
		    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		    label = new JLabel();
		    add(label);
	}
	
	public static void setOnline(){
		label.setText("Online");
		label.setFont(label.getFont().deriveFont(15f));
		label.setForeground(Color.green);
	    
	}
	
	public static void setOffline(){
		label.setText("Offline");
		label.setFont(label.getFont().deriveFont(15f));
		label.setForeground(Color.red);
	}
}
