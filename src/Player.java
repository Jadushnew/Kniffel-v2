
public class Player {
	
	public String playerName;
	public int[] points = new int[19];
	public String[] categories = {"1","2","3","4","5","6","total score","bonus","total score with bonus","3 of a kind","4 of a kind","small street","long street","full house","yahtzee","chance","total of upper section","total of lower section","grand total"};

	public Player(String name) {
		this.playerName = name;
	}
	
	public String getCategory(int position) {
		return categories[position];
	}
	
	public String getName() {
		return this.playerName;
	}


}
