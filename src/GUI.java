import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class GUI implements ActionListener, MouseListener {

	private JFrame window;
	// game panel is divided into table (left) and playField (right)
	private JPanel table, playField;
	// playField is divided into upperField (for dice) and lowerField (for buttons)
	private JPanel upperField, lowerField;
	// on top of the upperField sits the numberRow, on the lowerField sits the buttonRow
	private JPanel numberRow, buttonRow;
	private JButton[] buttons = new JButton[5];
	private NumberPanel[] nps = new NumberPanel[5];
	// RollCheckPanel holds the attempts and the rollButton
	private RollCheckPanel rcp;

	private JButton cancelRowButton;
	private JButton saveButton;
	private JButton mainMenuButton;

	private JPanel highscorePanel;
	private JLabel highscoreLabel;
	// OutputPanel holds the playerName and gives commands to the player
	private OutputPanel opp;
	private int attemptsLeft = 3;
	private int roundPoints = 0;
	private Player player1;

	private JPanel roundBackground;
	private JLabel roundLabel;

	private String[][] tableArray = new String[20][2];
	private String[] headerArray = new String[] { "combinations", "points" };
	private JTable playTable;

	private Die[] dice = new Die[5];
	private int[] diceValues = new int[5];

	private boolean cancelModeOn = false;

	// constructor called when a new game is created
	public GUI(String playerName) {

		player1 = new Player(playerName);

		createBackground();

		createTable();

		table.add(roundBackground);
		table.add(playTable);
		opp = new OutputPanel(playerName);
		table.add(opp);

		createPlayfield();

		for (int i = 0; i < buttons.length; i++) {
			nps[i] = new NumberPanel("default");
			numberRow.add(nps[i]);
		}

		createKeepButtons();

		rcp = new RollCheckPanel(attemptsLeft);

		createOtherStuff();

		createDice();

	}

	// constructor called when an old game is loaded, sets values
	public GUI(String playerName, int[] currentValues, String[] currentTableValues, int currentAttempts) {

		player1 = new Player(playerName);

		createBackground();

		createTable(currentTableValues);

		table.add(roundBackground);
		table.add(playTable);
		opp = new OutputPanel(playerName);
		table.add(opp);
		
		if(attemptsLeft > 0) {
			opp.setOutput("please continue rolling or book your points");
		} else {
			opp.setOutput("please book your points");
		}

		createPlayfield();

		for (int i = 0; i < buttons.length; i++) {
			nps[i] = new NumberPanel(Integer.toString(currentValues[i]));
			numberRow.add(nps[i]);
		}

		for(int i=1; i<20; i++) {
			playTable.setValueAt(currentTableValues[i-1], i, 1);
		}

		createKeepButtons();
		for(int i=0;i<buttons.length;i++) {
			buttons[i].setEnabled(true);
		}

		rcp = new RollCheckPanel(currentAttempts);

		createOtherStuff();

		attemptsLeft = currentAttempts;
		createDice(currentValues);

	}

	// creates the frame and the table part of the game
	public void createBackground() {
		window = new JFrame("Yahtzee");
		window.setSize(1000, 520);
		window.setResizable(false);
		window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.X_AXIS));

		table = new JPanel();
		table.setLayout(new BoxLayout(table, BoxLayout.Y_AXIS));
		table.setPreferredSize(new Dimension(300, 520));
		table.setBackground(Color.gray);
	}

	// creates the play field on the right side of the game and the panels for the dice and the buttons
	public void createPlayfield() {
		playField = new JPanel();
		playField.setLayout(new BoxLayout(playField, BoxLayout.Y_AXIS));
		playField.setPreferredSize(new Dimension(700, 500));
		playField.setBackground(Color.lightGray);

		numberRow = new JPanel();
		numberRow.setLayout(new GridLayout(0, 5));
		numberRow.setPreferredSize(new Dimension(690, 240));
		buttonRow = new JPanel();
		buttonRow.setLayout(new GridLayout(0, 5));
		buttonRow.setPreferredSize(new Dimension(690, 240));
	}

	// gets called when table is created from a save file
	public void createTable(String[] currentTableValues) {

		for (int i = 1; i < 19; i++) {
			tableArray[i][0] = player1.getCategory(i - 1);
		}
		tableArray[19][0] = player1.getCategory(18);

		TableModel model = new DefaultTableModel(tableArray, headerArray) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;// This causes all cells to be not editable
			}
		};

		playTable = new JTable(model);
		playTable.setPreferredSize(new Dimension(290, 520));

		playTable.setValueAt(headerArray[0], 0, 0);
		playTable.setValueAt(headerArray[1], 0, 1);

		for (int i = 1; i < 20; i++) {
			playTable.setValueAt(currentTableValues[i-1], i, 1);
		}

		playTable.addMouseListener(this);

		roundLabel = new JLabel("Round: " + getRound());
		roundBackground = new JPanel();
		roundBackground.setSize(300, 50);
		roundBackground.add(roundLabel);
	}

	// creates a new table
	public void createTable() {

		for (int i = 1; i < 19; i++) {
			tableArray[i][0] = player1.getCategory(i - 1);
		}
		tableArray[19][0] = player1.getCategory(18);

		TableModel model = new DefaultTableModel(tableArray, headerArray) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;// This causes all cells to be not editable
			}
		};

		playTable = new JTable(model);
		playTable.setPreferredSize(new Dimension(290, 520));

		playTable.setValueAt(headerArray[0], 0, 0);
		playTable.setValueAt(headerArray[1], 0, 1);

		for (int i = 1; i < 20; i++) {
			playTable.setValueAt("0", i, 1);
		}

		playTable.addMouseListener(this);

		roundLabel = new JLabel("Round: " + getRound());
		roundBackground = new JPanel();
		roundBackground.setSize(300, 50);
		roundBackground.add(roundLabel);
	}

	// gets the number of rounds by counting how many rows have a value (used above table)
	private String getRound() {
		boolean[] isFull = new boolean[playTable.getRowCount()-7];
		for(int i=1; i<7; i++) {
			if(!playTable.getValueAt(i, 1).equals("0")) {
				isFull[i-1] = true;
			} else {
				isFull[i-1] = false;
			}
		}
		for(int i=10; i<17; i++) {
			if(!playTable.getValueAt(i, 1).equals("0")) {
				isFull[i-5] = true;
			} else {
				isFull[i-5] = false;
			}
		}
		int roundNumber = 1;
		for(int i=0; i<isFull.length; i++) {
			if(isFull[i] == true) {
				roundNumber++;
			}
		}
		return Integer.toString(roundNumber);
	}

	// creates the buttons which will be added to the button row
	public void createKeepButtons() {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton();
			buttons[i].setText("not kept!");
			buttons[i].setHorizontalTextPosition(AbstractButton.LEFT);
			buttons[i].setPreferredSize(new Dimension(100, 80));
			buttons[i].addActionListener(this);
			buttons[i].setFont(new Font("Dialog", Font.BOLD, 14));
			buttons[i].setEnabled(false);
			buttonRow.add(buttons[i]);
		}
	}

	// method created for a cleaner constructor (hides creation of other buttons and panels)
	public void createOtherStuff() {
		rcp.getButton().addActionListener(this);
		buttonRow.add(rcp);

		cancelRowButton = new JButton("cancel row");
		cancelRowButton.addActionListener(this);

		buttonRow.add(cancelRowButton);

		saveButton = new JButton("save game");
		saveButton.addActionListener(this);
		buttonRow.add(saveButton);

		upperField = new JPanel();
		upperField.setPreferredSize(new Dimension(700, 250));
		upperField.setBackground(Color.lightGray);
		lowerField = new JPanel();
		lowerField.setPreferredSize(new Dimension(700, 250));
		lowerField.setBackground(Color.lightGray);

		mainMenuButton = new JButton("<html>go back to <br/> main menu </html>");
		mainMenuButton.addActionListener(this);
		buttonRow.add(mainMenuButton);

		highscoreLabel = new JLabel("highscore: " + Highscore.getHighscore(), SwingConstants.CENTER);
		highscorePanel = new JPanel(new BorderLayout());
		highscorePanel.add(highscoreLabel, BorderLayout.CENTER);
		buttonRow.add(highscorePanel);

		window.add(table);
		upperField.add(numberRow);
		lowerField.add(buttonRow);
		playField.add(upperField);
		playField.add(lowerField);

		window.add(playField);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.toFront();
		window.setLocationRelativeTo(null);
	}

	// decreases attempts and updates the roll check panel accordingly
	public void decreaseAttempt() {
		this.attemptsLeft--;
		rcp.updateRCP(attemptsLeft);
	}

	public int getAttempt() {
		return attemptsLeft;
	}

	// creates 5 new default dice
	public void createDice() {
		for (int i = 0; i < buttons.length; i++) {
			dice[i] = new Die(6);
		}
	}

	// method for creating dice from a loaded game
	public void createDice(int[] desiredValues) {
		for (int i = 0; i < buttons.length; i++) {
			dice[i] = new Die(6);
			dice[i].rollDie(desiredValues[i]);
		}
	}

	// calls the rollDie()-method of a die if it is not set to kept
	public void rollDice() {
		for (int i = 0; i < dice.length; i++) {
			if (dice[i].getKept() == false) {
				dice[i].rollDie();
			}
			diceValues[i] = dice[i].getValue();
		}
	}

	public void displayNumber() {
		for (int i = 0; i < dice.length; i++) {
			nps[i].setNumber(dice[i].getValue());
		}
	}

	public void attempt() {
		for (int i = 0; i < 5; i++) {
			if (buttons[i].isEnabled() == false) {
				buttons[i].setEnabled(true);
			}
		}
		opp.setOutput("please continue rolling or book your points");
		decreaseAttempt();
		rollDice();
		displayNumber();
	}

	public void changeButtonText(Die die, JButton button) {
		if (die.getKept()) {
			button.setText("kept!");
			button.setBackground(Color.green);
		} else {
			button.setText("not kept!");
			button.setBackground(null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[0]) {
			dice[0].changeKept();
			changeButtonText(dice[0], buttons[0]);
		}
		if (e.getSource() == buttons[1]) {
			dice[1].changeKept();
			changeButtonText(dice[1], buttons[1]);
		}
		if (e.getSource() == buttons[2]) {
			dice[2].changeKept();
			changeButtonText(dice[2], buttons[2]);
		}
		if (e.getSource() == buttons[3]) {
			dice[3].changeKept();
			changeButtonText(dice[3], buttons[3]);
		}
		if (e.getSource() == buttons[4]) {
			dice[4].changeKept();
			changeButtonText(dice[4], buttons[4]);
		}
		if (e.getSource() == rcp.getButton()) {
			if (attemptsLeft > 0) {
				attempt();
			}
		}
		if (e.getSource() == cancelRowButton) {
			cancelRow();
		}
		if (e.getSource() == saveButton) {
			SaveGameHandler saver = new SaveGameHandler();
			String[] tableValues = new String[19];
			for(int i=0; i < tableValues.length; i++) {
				tableValues[i] = playTable.getValueAt(i+1, 1).toString();
			}
			saver.saveGame(player1.getName(), diceValues, tableValues, attemptsLeft);
		}
		if(e.getSource() == mainMenuButton) {
			new MainMenu();
			window.dispose();
		}
	}

	// handles the tables cell when clicked on, used for scoring the player's points
	@Override
	public void mouseClicked(MouseEvent e) {
		int row = playTable.rowAtPoint(e.getPoint());
		int col = playTable.columnAtPoint(e.getPoint());

		if (col == 1 && attemptsLeft < 3) {
			handleEndOfRound(row, col);
		} else {
			if(col != 1) {
				opp.setNotValid();
			}
			if(attemptsLeft >= 3) {
				opp.setNotYetRolled();
			}
		}
	}

	// handles the end of a round, checks if game should end are executed every round
	public void handleEndOfRound(int row, int col) {
		
		if (row != 0 && row != 7 && row != 8 && row != 9 && row < 17 && playTable.getValueAt(row, col).toString() == "0") {

			if(cancelModeOn) {
				// if cancelModeOn rowValue will be set to "cancelled" if the rows is still 0
				// sets the cancel status back to false
				if (playTable.getValueAt(row, 1).toString().equals("0")) {
					playTable.setValueAt("cancelled", row, 1);
					cancelRow();
					checkStatus();
					roundRestart();
				}
			} else {
				// otherwise points will be booked accordingly 
				if (checkIfWriteable(row)) {
					bookPoints(row);
					checkStatus();
					roundRestart();
				} else {
					opp.setNotValid();
				}
			}
		} else {
			opp.setNotValid();
		}
	}

	// checks if sections are full
	private void checkStatus() {
		// writes the score and bonus if upper part is full
		if (checkIfPartIsFull(1,6)) {
			writeSum(7);
			giveBonus();
		}

		// writes the score if lower part is full
		if (checkIfPartIsFull(10,7)) {
			writeSum(18);
		}

		// writes the total score if both parts are full and ends the game
		if (checkIfPartIsFull(1,6) && checkIfPartIsFull(10,7)) {
			writeSum(19);
			opp.setOutput("Game ended. Final score: " + playTable.getValueAt(19, 1));
			if(Highscore.isHigher(Integer.parseInt(playTable.getValueAt(19, 1).toString()))) {
				Highscore.setHighscore(Integer.parseInt(playTable.getValueAt(19, 1).toString()));
				highscoreLabel.setText(Highscore.getHighscore());
				highscorePanel.revalidate();
			}
			deactivateInputs();
		}
	}

	// checks if the row is valid for writing by checking if your combination would generate points by the rule of the role
	public boolean checkIfWriteable(int row) {
		//a row is only writable if it's current value is still 0 -> still in default state
		if (Integer.parseInt( (String) playTable.getValueAt(row, 1)) == 0) {

			// check number rows
			if (row > 0 && row < 7) {
				for (int i = 0; i < dice.length; i++) {
					if (dice[i].getValue() == row) {
						roundPoints += dice[i].getValue();
					}
				}
				if (roundPoints != 0) {
					return true;
				}
			}
			// check three of a kind
			if (row == 10) {
				int[] occurance = new int[6];
				for (int j = 0; j < occurance.length; j++) {
					for (int i = 0; i < dice.length; i++) {
						if (dice[i].getValue() == j + 1) {
							occurance[j]++;
						}
					}
					if (occurance[j] >= 3) {
						roundPoints = dice[0].getValue() + dice[1].getValue() + dice[2].getValue() + dice[3].getValue()
								+ dice[4].getValue();
						return true;
					}
				}
			}
			// check four of a kind
			if (row == 11) {
				int[] occurance = new int[6];
				for (int j = 0; j < occurance.length; j++) {
					for (int i = 0; i < dice.length; i++) {
						if (dice[i].getValue() == j + 1) {
							occurance[j]++;
						}
					}
					if (occurance[j] >= 4) {
						roundPoints = dice[0].getValue() + dice[1].getValue() + dice[2].getValue() + dice[3].getValue()
								+ dice[4].getValue();
						return true;
					}
				}
			}
			// check for little street
			if (row == 12) {
				int[] occurance = new int[5];
				for (int i = 0; i < dice.length; i++) {
					occurance[i] = dice[i].getValue();
				}
				Arrays.sort(occurance);
				int neighbours = 0;
				for (int i = 0; i < occurance.length - 1; i++) {
					if (occurance[i] == (occurance[i + 1] - 1)) {
						neighbours++;
					}
				}
				if (neighbours > 2) {
					roundPoints = 30;
					return true;
				}
			}
			// check for great street
			if (row == 13) {
				int[] occurance = new int[5];
				for (int i = 0; i < dice.length; i++) {
					occurance[i] = dice[i].getValue();
				}
				Arrays.sort(occurance);
				int neighbours = 0;
				for (int i = 0; i < occurance.length - 1; i++) {
					if (occurance[i] == (occurance[i + 1] - 1)) {
						neighbours++;
					}
				}
				if (neighbours > 3) {
					roundPoints = 40;
					return true;
				}
			}
			// check for Full House
			if (row == 14) {
				int[] occurance = new int[6];
				for (int j = 0; j < occurance.length; j++) {
					for (int i = 0; i < dice.length; i++) {
						if (dice[i].getValue() == j + 1) {
							occurance[j]++;
						}
					}
				}
				for (int k = 0; k < occurance.length; k++) {
					if (occurance[k] == 3 || occurance[k] == 2) {
						for (int l = 0; l < occurance.length; l++) {
							if (l != k) {
								if (occurance[l] == 3) {
									roundPoints = 25;
									return true;
								}
							}
						}
					}
				}
			}
			// check for Yahtzee
			if (row == 15) {
				int result = 0;
				for (int i = 0; i < dice.length;) {
					result += dice[i].getValue();
				}
				if (result % 5 == 0) {
					roundPoints = 50;
					return true;
				}
			}
			// check for chance (chance is always possible if not filled out yet)
			if (row == 16) {
				for (int i = 0; i < dice.length;) {
					roundPoints += dice[i].getValue();
					return true;
				}
				
			}

		}
		return false;
	}

	// writes the points into the table, resets the output panel
	public void bookPoints(int row) {
		playTable.setValueAt(Integer.toString(roundPoints), row, 1);
		opp.setOutput("please continue");
	}

	// writes the sum of the table part in the respective row
	public void writeSum(int row) {
		switch (row) {
		case 7: {
			playTable.setValueAt(Integer.toString(sumPoints(1,0)), row, 1);
			break;
		}
		case 18: {
			playTable.setValueAt(Integer.toString(sumPoints(10,1)), row, 1);
			break;
		}
		case 19: {
			int finalSum = sumPoints(1,0) + sumPoints(10,1);
			playTable.setValueAt(Integer.toString(finalSum), row, 1);
			break;
		}
		}
	}

	//sums the points for the score. Addition is needed because lower section is larger by one row
	public int sumPoints(int startingRow, int addition) {
		int sum = 0;
		for (int i = startingRow; i < startingRow+6+addition; i++) {
			if (!playTable.getValueAt(i, 1).toString().equals("cancelled")) {
				sum += Integer.parseInt(playTable.getValueAt(i, 1).toString());
			}
		}
		return sum;
	}

	//checking method for the writing of the score. Uses starting row and section length to differentiate between upper and lower section of the table
	public boolean checkIfPartIsFull(int startingRow, int sectionLength) {
		boolean[] isFull = new boolean[sectionLength];
		for(int i = startingRow; i < startingRow + sectionLength; i++) {
			if ((playTable.getValueAt(i, 1)).toString().equals("cancelled") || !(playTable.getValueAt(i, 1)).toString().equals("0")) {
				isFull[i-startingRow] = true;
			} else {
				isFull[i-startingRow] = false;
			}
		}
		if (isFull[0] && isFull[1] && isFull[2] && isFull[3] && isFull[4] && isFull[5]) {
			return true;
		} else {
			return false;
		}
	}

	// gives the bonus if the necessary score is reached
	public void giveBonus() {
		if (Integer.parseInt(playTable.getValueAt(7, 1).toString()) >= 63) {
			playTable.setValueAt(Integer.toString(35), 8, 1);
			Integer newSum = (Integer.parseInt(playTable.getValueAt(7, 1).toString()) + 35);
			playTable.setValueAt(newSum.toString(), 9, 1);
		} else {
			playTable.setValueAt((playTable.getValueAt(7, 1).toString()), 9, 1);
		}
		playTable.setValueAt(playTable.getValueAt(9, 1).toString(), 17, 1);
	}

	// resets attempts, roundPoints, dice, and buttons
	public void roundRestart() {
		attemptsLeft = 3;
		roundPoints = 0;
		for (int i = 0; i < buttons.length; i++) {
			nps[i].setNumber("<html>not yet<br/>rolled<html/>");
			dice[i].setUnKept();
			buttons[i].setBackground(null);
			buttons[i].setEnabled(false);
			buttons[i].setText("not kept!");
		}
		opp.setOutput("round ended!");
		cancelModeOn = false;
		rcp.getButton().setEnabled(true);
		rcp.updateRCP(attemptsLeft);
		roundLabel.setText("Round: " + getRound());
	}

	public void cancelRow() {
		if (cancelModeOn) {
			cancelModeOn = false;
			cancelRowButton.setBackground(null);
		} else {
			cancelModeOn = true;
			cancelRowButton.setBackground(Color.red);
		}
	}

	// gets called when the game ends
	// deactivates all buttons which could continue the game
	public void deactivateInputs() {
		for(JButton button : buttons) {
			button.setEnabled(false);
		}
		rcp.getButton().setEnabled(false);
		cancelRowButton.setEnabled(false);
	}

	public String getTableValues(int row) {
		return tableArray[row][1];
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
