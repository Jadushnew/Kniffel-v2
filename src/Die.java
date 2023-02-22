import java.util.Random;

public class Die {

	private int numSides;
	private int value;
	private boolean kept = false;
	
	public Die(int numSides)
	{
		this.numSides = numSides;
	}
	
	public int rollDie()
	{
		Random random = new Random();
		this.value = random.nextInt(this.numSides) + 1;
		return this.value;
	}
	
	public int rollDie(int toRoll) {
		this.value = toRoll;
		return this.value;
		//only for debugging
	}
	
	public void setSides(int numSides)
	{
		this.numSides = numSides;
	}
	
	public int getSides()
	{
		return this.numSides;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	public boolean getKept() {
		return kept;
	}
	
	public void changeKept() {
		if(kept) {
			this.kept = false;
		} else {
			this.kept = true;
		}
	}
	
	public void setUnKept() {
		this.kept = false;
	}
}
