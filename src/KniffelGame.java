import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class KniffelGame extends JFrame implements ActionListener{
	
	public int[] keepingNumbers = new int[5];
	public int[] currentDiceValues = new int[5];
	public GUI gameGUI;
	public JLabel welcomeText;
	public JPanel buttonPanel;
	public JButton newGameButton;
	public JButton loadSaveButton;
	public JTextField name;
	public String playerName;
	public SaveGameHandler handler;

	public static void main(String[] args) {
		
		KniffelGame game = new KniffelGame();
		
	}
	
	public KniffelGame() {
		setTitle("Kniffel");
		setLocationRelativeTo(null);
		setSize(400,400);
		setLayout(new BorderLayout());
		
		handler = new SaveGameHandler();
		
		welcomeText = new JLabel("Welcome to Kniffel!", SwingConstants.CENTER);
		welcomeText.setFont(new Font("Dialog", Font.BOLD, 30));
		
		buttonPanel = new JPanel();
		newGameButton = new JButton("start new game");
		newGameButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		newGameButton.addActionListener(this);
		loadSaveButton = new JButton("load save");
		loadSaveButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		loadSaveButton.addActionListener(this);
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(newGameButton);
		buttonPanel.add(loadSaveButton);
		
		name = new JTextField(20);
		name.addActionListener(this);
		name.setVisible(false);
		
		this.add(welcomeText, BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.CENTER);
		this.add(name, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void startNewGame() {
		name.setVisible(true);
		buttonPanel.add(new JLabel("Please enter your name:"));
		newGameButton.setEnabled(false);
		this.revalidate();
	}
	
	public void startGame(String playerName) {
		gameGUI = new GUI(playerName);
		this.setVisible(false);
		}
	
	public void loadOldGame() {
		new GUI(handler.getDesiredPlayerName(), handler.getDesiredValues(), handler.getDesiredTableValues(), handler.getDesiredAttempts());
		this.setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newGameButton) {
			startNewGame();
		}
		if(e.getSource() == loadSaveButton) {
			JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir") + "\\saves\\");
			fileChooser.showOpenDialog(buttonPanel);
			File selectedFile = fileChooser.getSelectedFile();
			handler.loadGame(selectedFile);
			loadOldGame();
		}
		if(e.getSource() == name) {
			startGame(name.getText());
		}
		
	}

}
