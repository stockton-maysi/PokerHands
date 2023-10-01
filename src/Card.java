public class Card {
	private String suite;
	private int value;
	
	public Card(String s, int v) {
		suite = s;
		value = v;
	}
	
	public String getSuite() {
		return suite;
	}
	
	public int getValue() {
		return value;
	}
	
	public String toString() {
		String valueName;
		
		if (value == 1) {
			valueName = "A";
		} else if (value == 11) {
			valueName = "J";
		} else if (value == 12) {
			valueName = "Q";
		} else if (value == 13) {
			valueName = "K";
		} else {
			valueName = "" + value;
		}
		
		return suite + valueName;
	}
}