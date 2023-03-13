import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainMenu extends JFrame implements ActionListener{
	
	private JLabel welcomeText;
	private JPanel buttonPanel;
	
	private JButton newGameButton;
	private Component nameBox;
	private JLabel nameLengthWarningLabel;
	private Component nameBox2;
	private JButton loadSaveButton;
	private JButton howToPlayButton;
	private JButton closeGameButton;
	
	private Rules rules;
	
	private JTextField name;
	private SaveGameHandler handler;
	
	private final String ENTER_NAME_LABEL = "your name";
	private final String NAME_LENGTH_WARNING = "maximum length: 20";

	public static void main(String[] args) {
		new MainMenu();
	}
	
	public MainMenu() {
		setTitle("Yahtzee main menu");
		setSize(400,400);
		setResizable(false);
		setLayout(new BorderLayout());
		
		handler = new SaveGameHandler();
		
		welcomeText = new JLabel("Welcome to Yahtzee!", SwingConstants.CENTER);
		welcomeText.setFont(new Font("Dialog", Font.BOLD, 30));
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		newGameButton = new JButton("start new game");
		newGameButton.addActionListener(this);
		
		name = new JTextField();
		name.addActionListener(this);
		name.setText(ENTER_NAME_LABEL);
		name.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				name.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				name.setText(ENTER_NAME_LABEL);
			}
		});
		
		name.setVisible(false);
		forceSize(name);
		
		nameLengthWarningLabel = new JLabel(NAME_LENGTH_WARNING);
		nameLengthWarningLabel.setVisible(false);
		
		loadSaveButton = new JButton("load save");
		loadSaveButton.addActionListener(this);
		
		howToPlayButton = new JButton("how to play?");
		howToPlayButton.addActionListener(this);
		
		closeGameButton = new JButton("exit game");
		closeGameButton.addActionListener(this);
		
		buttonPanel.add(Box.createRigidArea(new Dimension(124, 30)));
		buttonPanel.add(newGameButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(124, 10)));
		buttonPanel.add(name);
		
		nameBox2 = Box.createRigidArea(new Dimension(124, 10));
		buttonPanel.add(nameBox2);
		nameBox2.setVisible(false);
		
		buttonPanel.add(nameLengthWarningLabel);
		
		nameBox = Box.createRigidArea(new Dimension(124, 10));
		buttonPanel.add(nameBox);
		nameBox.setVisible(false);
		nameLengthWarningLabel.setVisible(false);
		
		buttonPanel.add(loadSaveButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(124, 10)));
		buttonPanel.add(howToPlayButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(124, 10)));
		buttonPanel.add(closeGameButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(124, 10)));

		forceSize(newGameButton);
		forceSize(loadSaveButton);
		forceSize(howToPlayButton);
		forceSize(closeGameButton);
		
		newGameButton.setAlignmentX(0);
		loadSaveButton.setAlignmentX(0);
		howToPlayButton.setAlignmentX(0);
		closeGameButton.setAlignmentX(0);
		
		this.add(welcomeText, BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.CENTER);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newGameButton) {
			openNameField();
		}
		if(e.getSource() == loadSaveButton) {
			JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir") + "\\saves\\");
			fileChooser.showOpenDialog(buttonPanel);
			File selectedFile = fileChooser.getSelectedFile();
			try {
				handler.loadGame(selectedFile);
				startGame();
			} catch (NullPointerException e1) {
				System.out.println("loading cancelled.");
			}
		}
		if(e.getSource() == name) {
			if(name.getText().length() <= 20) {
				startGame(name.getText());
			} else {
				nameLengthWarningLabel.setVisible(true);
			}
	
		}
		if(e.getSource() == howToPlayButton) {
			rules = new Rules(this);
			howToPlayButton.setEnabled(false);
		}
		if(e.getSource() == closeGameButton) {
			this.dispose();
			if(rules != null) {
				rules.dispose();
			}
		}
	}
	
	

	// opens the name field to enter your name
	public void openNameField() {
		newGameButton.setEnabled(false);
		nameBox.setVisible(true);
		name.setVisible(true);
		nameBox2.setVisible(true);
		name.setAlignmentX(0);
		this.revalidate();
	}

	// starts a new game with no values 
	public void startGame(String playerName) {
		new GUI(playerName);
		dispose();
		}
	
	// starts a game with given values (loading)
	public void startGame() {
		new GUI(handler.getDesiredPlayerName(), handler.getDesiredValues(), handler.getDesiredTableValues(), handler.getDesiredAttempts());
		this.setVisible(false);
	}
	
	// makes sure that buttons have the right size
	private void forceSize(JButton button) {
		  button.setMaximumSize(new Dimension(124, 30));
		  button.setMinimumSize(new Dimension(124, 30));
		  button.setSize(new Dimension(124, 30));
		  button.setPreferredSize(new Dimension(124, 30));
		}
	
	// makes sure that the textfield has the right size
	private void forceSize(JTextField field) {
		  field.setMaximumSize(new Dimension(124, 30));
		  field.setMinimumSize(new Dimension(124, 30));
		  field.setSize(new Dimension(124, 30));
		  field.setPreferredSize(new Dimension(124, 30));
		}
	
	// used in Rules class to enable the button again
	public JButton getHowToPlayButton() {
		return howToPlayButton;
	}
	
	
}
