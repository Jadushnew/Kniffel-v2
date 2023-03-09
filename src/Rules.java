import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 *  class that provides a new frame with the rules displayed. Used in main menu
 */
public class Rules extends JFrame implements ActionListener{

	private JPanel textPanel;
	private JLabel textLabel;
	private JButton closeButton;
	private MainMenu menu;

	public Rules(MainMenu menu) {
		this.menu = menu;
		setTitle("Rules");
		setLayout(new BorderLayout());
		createText();
		add(textPanel);
		closeButton = new JButton("close");
		closeButton.addActionListener(this);
		textPanel.add(Box.createRigidArea(new Dimension(1, 20)));
		textPanel.add(closeButton);
		textPanel.add(Box.createRigidArea(new Dimension(1, 20)));
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
	}

	// creates text out of the class RulesText
	private void createText() {
		textPanel = new JPanel();
		textLabel = new JLabel();

		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

		textLabel.setText(RulesText.getText());

		textPanel.add(textLabel);


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == closeButton) {
			dispose();
			menu.getHowToPlayButton().setEnabled(true);
		}

	}
}
