import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class OutputPanel extends JPanel {
	
	private JLabel playerName = new JLabel("default", SwingConstants.CENTER);
	private JLabel output;
	private Border border;
	
	public OutputPanel(String playerName) {
		this.playerName.setText("Player: " + playerName);
		border = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
		this.add(Box.createRigidArea(new Dimension(20,0)));
		output = new JLabel("", SwingConstants.CENTER);
		
		this.setBorder(border);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(this.playerName);
		this.add(output);
	}
	
	public void setOutput(String output) {
		this.output.setText(output);
	}
}
