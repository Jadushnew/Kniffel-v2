import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SaveGameHandler{
	
	private String[][] desiredTableValues = new String[20][2];
	private String desiredPlayerName;
	private int[] desiredValues = new int[5];
	private int desiredAttempts;

	public void saveGame(String playerName, int[] currentValues, String[][] currentTableValues, int currentAttempts) {
		try {
			BufferedWriter writer;
			
			writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "\\saves\\" + java.time.LocalDate.now() + "_save_.txt"));
			for (int i = 1; i < 20; i++) {
				writer.write(currentTableValues[i][1]+"\n");
			}
			writer.write(playerName+"\n");
			writer.write(Integer.toString(currentAttempts)+"\n");
			for (int i = 0; i < currentValues.length; i++) {
				writer.write(currentValues[i]+"\n");
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("saving failed!");
			e.printStackTrace();
		}
	}
	
	public void loadGame(File toLoad) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(toLoad));
			for(int i=1; i<20;i++) {
				desiredTableValues[i][1] = reader.readLine();
			}
			desiredPlayerName = reader.readLine();
			desiredAttempts = Integer.parseInt(reader.readLine());
			for(int i=0;i<5;i++) {
				desiredValues[i] = Integer.parseInt(reader.readLine());
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("loading failed!");
			e.printStackTrace();
		}
	}

	public String[][] getDesiredTableValues() {
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