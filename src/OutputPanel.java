import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class OutputPanel extends JPanel {
	
	private JLabel playerName = new JLabel("default", SwingConstants.CENTER);
	private JLabel output;
	private Border border;
	
	private final String NOT_VALID = "not a valid row. Please choose another!";
	private final String NOT_YET_ROLLED = "please roll the dice to score";
	
	public OutputPanel(String playerName) {
		this.playerName.setText("Player: " + playerName);
		border = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
		output = new JLabel("please begin your game", SwingConstants.CENTER);
		
		this.setBorder(border);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(this.playerName);
		this.add(output);
		this.setPreferredSize(new Dimension(300, 50));
	}
	
	public void setNotValid() {
		this.output.setText(NOT_VALID);
	}
	
	public void setNotYetRolled() {
		this.output.setText(NOT_YET_ROLLED);
	}
	
	public void setOutput(String text) {
		this.output.setText(text);
	}
}
