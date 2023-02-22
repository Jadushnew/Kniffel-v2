
public class Player {
	
	public String playerName;
	public int[] points = new int[19];
	public String[] categories = {"1","2","3","4","5","6","gesamt oben","Bonus","gesamt oben mit Bonus","3er Pasch","4er Pasch","kleine Straße","große Straße","Full House","Kniffel","Chance","gesamt oben mit Bonus","gesamt unten","Gesamtergebnis"};

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
