package uk.ac.aber.cs221.group12.taskercli.frontend;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;

public class OnlineIndicatorPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	
	private TeamMember teamMember;
	private JLabel label;
	
	public OnlineIndicatorPanel() {
		initialise();
	}
	
	
	public void initialise(){
		    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		    label = new JLabel();
		    add(label);
	}
	
	public void setOnline(){
		label.setText("Online");
		label.setForeground(Color.green);
	    
	}
	
	public void setOffline(){
		label.setText("Offline");
		label.setForeground(Color.red);
	}

}
