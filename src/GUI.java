import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import java.util.Arrays;

public class GUI implements ActionListener, MouseListener {

	private JFrame window;
	private JPanel table, playField;
	private JPanel upperField, lowerField;
	private JPanel numberRow, buttonRow;
	private JButton[] buttons = new JButton[5];
	private NumberPanel[] nps = new NumberPanel[5];
	private RollCheckPanel rcp;
	private JButton cancelRowButton;
	private JButton saveButton;
	private OutputPanel opp;
	private int attempt = 3;
	private int roundPoints = 0;
	private Player player1; 

	private String[][] tableArray = new String[20][2];
	private String[] headerArray = new String[]{"combinations", "points"};
	private JTable playTable;

	private Die[] dice = new Die[5];
	private int[] diceValues = new int[5];
	
	
	private boolean cancelModeOn = false;

	public boolean isFirstPartFull() {
		return firstPartFull;
	}

	public void setFirstPartFull(boolean firstPartFull) {
		this.firstPartFull = firstPartFull;
	}

	public boolean isSecondPartFull() {
		return secondPartFull;
	}

	public void setSecondPartFull(boolean secondPartFull) {
		this.secondPartFull = secondPartFull;
	}

	//booleans for the check if everything is full
	private boolean firstPartFull = false;
	private boolean secondPartFull = false;

	public GUI(String playerName) {

		player1 = new Player(playerName);

		window = new JFrame("Kniffel");
		window.setSize(1000, 520);
		window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.X_AXIS));

		table = new JPanel();
		table.setLayout(new BoxLayout(table, BoxLayout.Y_AXIS));
		table.setPreferredSize(new Dimension(300, 520));
		table.setBackground(Color.gray);
		
		for(int i=1; i<20; i++) {
			tableArray[i][1] = "0";
		}
		
		playTable = new JTable(tableArray, headerArray);
		playTable.setPreferredSize(new Dimension(290, 520));

		playTable.setValueAt(headerArray[0], 0, 0);
		playTable.setValueAt(headerArray[1], 0, 1);

		for (int i = 1; i < 19; i++) {
			tableArray[i][0] = player1.getCategory(i-1);
		}
		tableArray[19][0] = player1.getCategory(18);
		playTable.addMouseListener(this);
		
		table.add(playTable);
		opp = new OutputPanel(playerName);
		table.add(opp);


		playField = new JPanel();
		playField.setLayout(new BoxLayout(playField, BoxLayout.Y_AXIS));
		playField.setPreferredSize(new Dimension(700, 500));
		playField.setBackground(Color.lightGray);

		numberRow = new JPanel();
		numberRow.setLayout(new GridLayout(0,5));
		numberRow.setPreferredSize(new Dimension(690,240));
		buttonRow = new JPanel();
		buttonRow.setLayout(new GridLayout(0,5));
		buttonRow.setPreferredSize(new Dimension(690,240));

		for (int i = 0; i < buttons.length; i++) {
			nps[i] = new NumberPanel("default");
			numberRow.add(nps[i]);
		}

		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton();
			buttons[i].setText("not kept!");
			buttons[i].setHorizontalTextPosition(AbstractButton.LEFT);
			buttons[i].setPreferredSize(new Dimension(100,80));
			buttons[i].addActionListener(this);
			buttons[i].setFont(new Font("Dialog", Font.BOLD, 14));
			buttons[i].setEnabled(false);
			buttonRow.add(buttons[i]);
		}

		rcp = new RollCheckPanel(attempt);
		rcp.getButton().addActionListener(this);
		buttonRow.add(rcp);

		cancelRowButton = new JButton("cancel row");
		cancelRowButton.addActionListener(this);
		buttonRow.add(cancelRowButton);

		saveButton = new JButton("save game");
		saveButton.addActionListener(this);
		buttonRow.add(saveButton);
		
		upperField = new JPanel();
		upperField.setPreferredSize(new Dimension(700,250));
		upperField.setBackground(Color.lightGray);
		lowerField = new JPanel();
		lowerField.setPreferredSize(new Dimension(700,250));
		lowerField.setBackground(Color.lightGray);

		window.add(table);
		upperField.add(numberRow);
		lowerField.add(buttonRow);
		playField.add(upperField); playField.add(lowerField);

		window.add(playField);	
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.toFront();
		createDice();

	}

	public GUI(String playerName, int[] currentValues, String[][] currentTableValues, int currentAttempts) {

		player1 = new Player(playerName);

		window = new JFrame("Kniffel");
		window.setSize(1000, 520);
		window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.X_AXIS));

		table = new JPanel();
		table.setLayout(new BoxLayout(table, BoxLayout.Y_AXIS));
		table.setPreferredSize(new Dimension(300, 520));
		table.setBackground(Color.gray);
		
		for(int i=1; i<20; i++) {
			tableArray[i][1] = currentTableValues[i][1];
		}
		
		playTable = new JTable(tableArray, headerArray);
		playTable.setPreferredSize(new Dimension(290, 520));

		playTable.setValueAt(headerArray[0], 0, 0);
		playTable.setValueAt(headerArray[1], 0, 1);

		for (int i = 1; i < 19; i++) {
			tableArray[i][0] = player1.getCategory(i-1);
		}
		tableArray[19][0] = player1.getCategory(18);
		playTable.addMouseListener(this);
		
		table.add(playTable);
		opp = new OutputPanel(playerName);
		table.add(opp);


		playField = new JPanel();
		playField.setLayout(new BoxLayout(playField, BoxLayout.Y_AXIS));
		playField.setPreferredSize(new Dimension(700, 500));
		playField.setBackground(Color.lightGray);

		numberRow = new JPanel();
		numberRow.setLayout(new GridLayout(0,5));
		numberRow.setPreferredSize(new Dimension(690,240));
		buttonRow = new JPanel();
		buttonRow.setLayout(new GridLayout(0,5));
		buttonRow.setPreferredSize(new Dimension(690,240));

		for (int i = 0; i < buttons.length; i++) {
			nps[i] = new NumberPanel(Integer.toString(currentValues[i]));
			numberRow.add(nps[i]);
		}

		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton();
			buttons[i].setText("not kept!");
			buttons[i].setHorizontalTextPosition(AbstractButton.LEFT);
			buttons[i].setPreferredSize(new Dimension(100,80));
			buttons[i].addActionListener(this);
			buttons[i].setFont(new Font("Dialog", Font.BOLD, 14));
			buttons[i].setEnabled(true);
			buttonRow.add(buttons[i]);
		}

		rcp = new RollCheckPanel(currentAttempts);
		rcp.getButton().addActionListener(this);
		buttonRow.add(rcp);

		cancelRowButton = new JButton("cancel row");
		cancelRowButton.addActionListener(this);
		
		buttonRow.add(cancelRowButton);

		saveButton = new JButton("save game");
		saveButton.addActionListener(this);
		buttonRow.add(saveButton);
		
		
		upperField = new JPanel();
		upperField.setPreferredSize(new Dimension(700,250));
		upperField.setBackground(Color.lightGray);
		lowerField = new JPanel();
		lowerField.setPreferredSize(new Dimension(700,250));
		lowerField.setBackground(Color.lightGray);

		window.add(table);
		upperField.add(numberRow);
		lowerField.add(buttonRow);
		playField.add(upperField); playField.add(lowerField);

		window.add(playField);	
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.toFront();
		attempt = currentAttempts;
		createDice(currentValues);

	}
	
	public void decreaseAttempt() {
		this.attempt--;;
		rcp.decreaseAttempt(attempt);
	}

	public int getAttempt() {
		return attempt;
	}

	public void createDice() {
		for (int i = 0; i < buttons.length; i++) {
			dice[i] = new Die(6);
		}
	}
	
	//for creating dice from a loaded game
	public void createDice(int[] desiredValues) {
		for (int i = 0; i < buttons.length; i++) {
			dice[i] = new Die(6);
			dice[i].rollDie(desiredValues[i]);
		}
	}

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
		for(int i=0; i<5; i++) {
			if(buttons[i].isEnabled() == false) {
				buttons[i].setEnabled(true);
			}
		}
		decreaseAttempt();
		rollDice();
		displayNumber();
	}

	public void changeButtonText(Die die, JButton button) {
		if(die.getKept()) {
			button.setText("kept!");
			button.setBackground(Color.green);
		} else {
			button.setText("not kept!");
			button.setBackground(null);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == buttons[0]) {
			dice[0].changeKept();
			changeButtonText(dice[0], buttons[0]);
		}
		if(e.getSource() == buttons[1]) {
			dice[1].changeKept();
			changeButtonText(dice[1], buttons[1]);
		}
		if(e.getSource() == buttons[2]) {
			dice[2].changeKept();
			changeButtonText(dice[2], buttons[2]);
		}
		if(e.getSource() == buttons[3]) {
			dice[3].changeKept();
			changeButtonText(dice[3], buttons[3]);
		}
		if(e.getSource() == buttons[4]) {
			dice[4].changeKept();
			changeButtonText(dice[4], buttons[4]);
		}
		if(e.getSource() == rcp.getButton()) {
			if(attempt > 0) {
				attempt();
			}
		}
		if(e.getSource() == cancelRowButton) {
			cancelRow();
		}
		if(e.getSource() == saveButton) {
			SaveGameHandler saver = new SaveGameHandler();
			saver.saveGame(player1.getName(), diceValues, tableArray, attempt);
		}

	}
	
	public void bookPoints(int row) {
		
		playTable.setValueAt(Integer.toString(roundPoints), row, 1);
		opp.setOutput("");
		checkSum(row);
		
		if(firstPartFull && secondPartFull) {
			System.out.println("all rows filled");
		} else {
			roundRestart();
		}
	}

	public void cancelRow() {
		if(cancelModeOn) {
			cancelModeOn = false;
			cancelRowButton.setBackground(null);
		}
		else {
			cancelModeOn = true;
			cancelRowButton.setBackground(Color.red);
		}
	}
	
	public void checkSum(int row) {
		
		//check upper part of table
		if (row > 0 && row <7) {
			boolean[] isFull = new boolean[6];
			for (int i = 1; i < 7; i++) {
				if (tableArray[i][1] != "cancelled") {
					if (Integer.parseInt(tableArray[i][1]) != 0) {
						isFull[i - 1] = true;
					} else {
						isFull[i - 1] = false;
					} 
				} else {
					isFull[i - 1] = true;
				}
			}
			if(isFull[0] && isFull[1] && isFull[2] && isFull[3] && isFull[4] && isFull[5]) {
				writeSum(7);
				firstPartFull = true;
				giveBonus();
			}
		}

		//check lower part of table
		if (row > 9 && row <17) {
			boolean[] isFull = new boolean[7];
			for (int i = 10; i < 17; i++) {
				if (tableArray[i][1] != "cancelled") {
					if (Integer.parseInt(tableArray[i][1]) != 0) {
						isFull[i - 10] = true;
					} else {
						isFull[i - 10] = false;
					} 
				} else {
					isFull[i - 10] = true;
				}
			}
			if(isFull[0] && isFull[1] && isFull[2] && isFull[3] && isFull[4] && isFull[5] && isFull[6]) {
				writeSum(17);
				secondPartFull = true;
			}
		}
	}
	
	public void writeSum(int row) {
		switch(row) {
		case 7: {
			playTable.setValueAt(Integer.toString(sumUpperPoints()), row, 1);
		}
		case 18: playTable.setValueAt(Integer.toString(sumLowerPoints()), row, 1);
		}
	}
	
	public int sumUpperPoints() {

		int upperSum =0;
		for(int i=1; i<7; i++) {
			if (tableArray[i][1] != "cancelled") {
				upperSum += Integer.parseInt(tableArray[i][1]);
			}
		}
		System.out.println("uppersum = " + upperSum);
		return upperSum;
	}
	
	public int sumLowerPoints() {
		int LowerSum =0;
		for(int i=10; i<17; i++) {
			if (tableArray[i][1] != "cancelled") {
				LowerSum += Integer.parseInt(tableArray[i][1]);
			}
		}
		return LowerSum;
	}
	
	public void giveBonus() {
		if(Integer.parseInt(tableArray[7][1]) >= 63) {
			playTable.setValueAt(Integer.toString(35), 8, 1);
			playTable.setValueAt(Integer.toString(Integer.parseInt((tableArray[7][1])) + 35), 9, 1);
		} else {
			playTable.setValueAt((tableArray[7][1]), 9, 1);
		}
		playTable.setValueAt((tableArray[9][1]), 17, 1);
	}

	public void checkAndWrite(int row) {
		
		if(Integer.parseInt(tableArray[row][1]) == 0 && tableArray[row][1] != "cancelled") {
			
			boolean validFound = false;
			//check number rows
			if (row > 0 && row < 7) {
				for (int i = 0; i < dice.length; i++) {
					if (dice[i].getValue() == row) {
						roundPoints += dice[i].getValue();
						validFound = true;
					}
				}
			}
			//check three of a kind
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
						validFound = true;

					}
				}
			}
			//check four of a kind
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
						validFound = true;
					}
				}
			}
			//check for little street
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
					validFound = true;
				}
			}
			//check for great street
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
					validFound = true;
				}
			}
			//check for Full House
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
									validFound = true;
								}
							}
						}
					}

				}
			}
			//check for Kniffel
			if (row == 15) {
				int result = 0;
				for (int i = 0; i < dice.length; i++) {
					result += dice[i].getValue();
				}
				if (result % 5 == 0) {
					roundPoints = 50;
					validFound = true;
				}
			}
			//check for Chance
			if (row == 16) {
				for (int i = 0; i < dice.length; i++) {
					roundPoints += dice[i].getValue();
					validFound = true;
				}
			}
			if (validFound) {
				bookPoints(row);
			} else {
				opp.setOutput("not a valid row! Please choose another!");
			} 
		} else {
			opp.setOutput("not a valid row! Please choose another!");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = playTable.rowAtPoint(e.getPoint());
		int col = playTable.columnAtPoint(e.getPoint());

		if (col == 1 && attempt < 3) {
			if (row != 0 && row != 7 && row != 8 && row != 9 && row < 17) {
				if (cancelModeOn) {
					playTable.setValueAt("cancelled", row, 1);
					cancelRow();
					roundRestart();
				} else {
					checkAndWrite(row);
				}
			}
		}

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
	
	public void roundRestart() {
		attempt = 3;
		roundPoints = 0;
		for(int i=0;i<buttons.length;i++) {
			dice[i].setUnKept();
			buttons[i].setBackground(null);
			buttons[i].setEnabled(false);
			buttons[i].setText("not kept!");
		}
		rcp.setLabel("<html>round over<br>roll dice to start next round</html>");
	}
	
	public String getTableValues(int row) {
		return tableArray[row][1];
	}
}
