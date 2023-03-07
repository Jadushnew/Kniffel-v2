import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
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
	
	private int[] keepingNumbers = new int[5];
	private int[] currentDiceValues = new int[5];
	private GUI gameGUI;
	private JLabel welcomeText;
	private JPanel buttonPanel;
	private JButton newGameButton;
	private JButton loadSaveButton;
	private JButton howToPlayButton;
	private JTextField name;
	private String playerName;
	private SaveGameHandler handler;

	public static void main(String[] args) {
		
		KniffelGame game = new KniffelGame();
		
	}
	
	public KniffelGame() {
		setTitle("Kniffel");
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
		
		howToPlayButton = new JButton("how to play?");
		howToPlayButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		howToPlayButton.addActionListener(this);
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		buttonPanel.add(Box.createRigidArea(new Dimension(124, 30)));
		buttonPanel.add(newGameButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(124, 10)));
		buttonPanel.add(loadSaveButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(124, 10)));
		buttonPanel.add(howToPlayButton);
		
		forceSize(newGameButton);
		forceSize(loadSaveButton);
		forceSize(howToPlayButton);
		
		newGameButton.setAlignmentX(0);
		loadSaveButton.setAlignmentX(0);
		howToPlayButton.setAlignmentX(0);
		
		this.add(welcomeText, BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.CENTER);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	public void startNewGame() {
		name = new JTextField(20);
		name.addActionListener(this);
		forceSize(name);
		buttonPanel.add(new JLabel("Please enter your name:"));
		newGameButton.setEnabled(false);
		buttonPanel.add(name);
		name.setAlignmentX(0);
		this.revalidate();
	}
	
	// starts a new game with no values
	public void startGame(String playerName) {
		gameGUI = new GUI(playerName);
		this.setVisible(false);
		}
	
	// starts a game with given values (loading)
	public void startGame() {
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
			try {
				handler.loadGame(selectedFile);
				startGame();
			} catch (NullPointerException e1) {
			}
		}
		if(e.getSource() == name) {
			startGame(name.getText());
		}
		if(e.getSource() == howToPlayButton) {
			new Rules();
		}
		
	}
	
	private void forceSize(JButton button) {
		  button.setMaximumSize(new Dimension(124, 30));
		  button.setMinimumSize(new Dimension(124, 30));
		  button.setSize(new Dimension(124, 30));
		  button.setPreferredSize(new Dimension(124, 30));
		}
	
	private void forceSize(JTextField field) {
		  field.setMaximumSize(new Dimension(124, 30));
		  field.setMinimumSize(new Dimension(124, 30));
		  field.setSize(new Dimension(124, 30));
		  field.setPreferredSize(new Dimension(124, 30));
		}
	
	
}
