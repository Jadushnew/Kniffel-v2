import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 *  class that handles saving and loading of save games. ATTENTION: CURRENTLY NOT WORKING GLOBALLY
 */
public class SaveGameHandler{
	
	private String[] desiredTableValues = new String[19];
	private String desiredPlayerName;
	private int[] desiredValues = new int[5];
	private int desiredAttempts;

	// writes all relevant Data into a .txt file
	// writes into saves folder in project folder
	public void saveGame(String playerName, int[] currentValues, String[] currentTableValues, int currentAttempts) {
		try {
			BufferedWriter writer;
			
			writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "\\saves\\" + java.time.LocalDate.now() + "_" + playerName +"_save_.txt"));
			for (int i = 0; i < 19; i++) {
				writer.write(currentTableValues[i]+"\n");
			}
			writer.write(playerName+"\n");
			writer.write(Integer.toString(currentAttempts)+"\n");
			for (int i = 0; i < currentValues.length; i++) {
				writer.write(currentValues[i]+"\n");
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("saving failed!");
		}
	}
	
	// reads from given .txt file
	public void loadGame(File toLoad) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(toLoad));
			for(int i=0; i<19; i++) {
				desiredTableValues[i] = reader.readLine();
			}
			desiredPlayerName = reader.readLine();
			desiredAttempts = Integer.parseInt(reader.readLine());
			for(int i=0;i<5;i++) {
				desiredValues[i] = Integer.parseInt(reader.readLine());
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("loading failed!");
		}
	}

	public String[] getDesiredTableValues() {
		return desiredTableValues;
	}

	public String getDesiredPlayerName() {
		return desiredPlayerName;
	}

	public int[] getDesiredValues() {
		return desiredValues;
	}

	public int getDesiredAttempts() {
		return desiredAttempts;
	}

}
