import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*	this class contains the button used for rolling the dice
 * 	as well as a label that tells the player how many attempts they
 * 	have left.
 */



public class RollCheckPanel extends JPanel {

	private JButton roll;
	private JLabel roundCheck;

	public RollCheckPanel(int attempts) {
		setPanel();
		roundCheck.setText(attempts + " more attempts");
	}

	// sets up the basic layout of the RCP
	public void setPanel() {

		roll = new JButton("roll");
		roundCheck = new JLabel();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(roll);
		this.add(Box.createRigidArea(new Dimension(150,5)));
		this.add(roundCheck);

		forceSize(roll);
		roll.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		roundCheck.setAlignmentX(JComponent.CENTER_ALIGNMENT);
	}

	// updates the panel 
	public void updateRCP(int attempt) {

		if (attempt != 0) {
			roundCheck.setText(attempt + " more attempts");
			roundCheck.repaint();
		} else {
			roll.setEnabled(false);
			roundCheck.setText("<html>no attempts left<br>book your points or cancel a row</html>");
		}
	}

	public JLabel getLabel() {
		return roundCheck;
	}

	public void setLabel(String text) {
		getLabel().setText(text);
	}

	public JButton getButton() {
		return this.roll;
	}

	// makes sure that button has the right size
	private void forceSize(JButton button) {
		button.setMaximumSize(new Dimension(150, 60));
		button.setMinimumSize(new Dimension(150, 60));
		button.setSize(new Dimension(150, 60));
		button.setPreferredSize(new Dimension(150, 60));
	}
}
