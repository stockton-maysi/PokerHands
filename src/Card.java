/**
 * An object with a suit attribute (clubs, diamonds, spades, or hearts) and a value
 * attribute (integer from 1-13 inclusive; 1 represents ace, 11 for jack, 12 for queen,
 * and 13 for king).
 * 
 * @author Ian Mays
 */
public class Card {
	private String suit;
	private int value;
	
	/**
	 * Constructs a new Card.
	 * @param s The suit symbol, as a String
	 * @param v The integer value of the card
	 */
	public Card(String s, int v) {
		suit = s;
		value = v;
	}
	
	/**
	 * Gets the suit symbol.
	 * @return The suit symbol
	 */
	public String getSuit() {
		return suit;
	}
	
	/**
	 * Gets the card value.
	 * @return The integer value
	 */
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
		
		return suit + valueName;
	}
}