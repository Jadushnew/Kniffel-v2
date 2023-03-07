
public class RulesText {
	
	private static String rulesText = 
			"<html>In Yahtzee you try to score as many points a possible.<br/>"
			+"To do that you try to have different dice combinations "
			+"which are listed in a table. <br/>Per round you may roll the dice three times, "
			+"always deciding which ones to keep and which ones to roll again.<br/>"
			+"As soon as you are happy with your result or after three rolls at the latest, <br/>"
			+"you have to score your points in a suiting row in the table by clicking in it. <br/>"
			+"<br/>"
			+"If you are not happy with your result or you don't want to score, <br/>"
			+"you can cancel a row by clicking on it while the cancel button is active.<br/>"
			+"You cannot score in this row again. <br/>"
			+"<br/>"
			+"Upper section: all the dice of the respective number count <br/>"
			+"three / four of a kind: all dice count <br/>"
			+"small street: 30 points <br/>"
			+"large street: 40 points <br/>"
			+"Yahtzee: 50 points <br/>"
			+"Chance: all dice count, no restrictions <br/>"
			+"<br/>"
			+"You will get a bonus of 35 points, if your score in the upper section of the table <br/>"
			+"is 63 points or higher.</html>";
	
	public static String getText() {
		return rulesText;
	}
}
