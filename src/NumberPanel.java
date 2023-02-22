import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class NumberPanel extends JPanel {
	
	private JLabel number = new JLabel("default", SwingConstants.CENTER);
	private Border border;

	public NumberPanel(String number) {
		setPanel();
		this.number.setText(number);
	}
	
	public void setPanel() {
		border = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
		number.setFont(new Font("Dialog", Font.BOLD, 30));
		this.setPreferredSize(new Dimension(110,80));
		this.setLayout(new BorderLayout());
		this.setBorder(border);
		this.add(number, BorderLayout.CENTER);
	}
	
	public JLabel getNumber() {
		return this.number;
	}
	
	public void setNumber(int number) {
		this.number.setText(Integer.toString(number));
	}
}
