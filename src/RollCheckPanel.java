import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RollCheckPanel extends JPanel {

	private JButton roll = new JButton("roll");
	private JLabel roundCheck = new JLabel();

	public RollCheckPanel(int attempts) {
		setPanel();
		roundCheck.setText(attempts + " more attempts");
	}
	
	public void setPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(roll);
		this.add(roundCheck);
	}

	public JButton getButton() {
		return this.roll;
	}
	
	public void decreaseAttempt(int attempt) {
		
		if (attempt != 0) {
			roundCheck.setText(attempt + " more attempts");
			roundCheck.repaint();
		} else {
			roundCheck.setText("<html>no attempts left<br>book your points</html>");
		}
	}
	
	public JLabel getLabel() {
		return roundCheck;
	}
	
	public void setLabel(String text) {
		getLabel().setText(text);
	}
}
