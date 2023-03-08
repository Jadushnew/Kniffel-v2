import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;

public class Highscore extends JLabel{
	
	private static String highscore;
	
	public static String getHighscore() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("highscore.txt"));
			highscore = reader.readLine();
			reader.close();
		} catch (IOException e1) {
			System.out.println("loading highscore failed!");
		}
		return highscore;
	}
	
	public static boolean isHigher(int totalScore) {
		if(totalScore > Integer.parseInt(highscore)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void setHighscore(int newHighscore) {
		try {
			BufferedWriter writer;
			writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "highscore.txt"));
			writer.write(newHighscore);
			writer.close();
			highscore = Integer.toString(newHighscore);
		} catch (IOException e2) {
			System.out.println("setting highscore failed!");
		}
	}
}
