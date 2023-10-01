import java.util.ArrayList;
import java.util.Random;

/**
 * Class that generates the deck, draws a hand from it, and evaluates that hand based
 * on various criteria.
 * 
 * @author Ian Mays
 */
public class HandEvaluator {
	private ArrayList<Card> hand = new ArrayList<>();
	private ArrayList<Card> deck = new ArrayList<>();
	private String[] suits = {"♣", "♦", "♥", "♠"};
	private Random random = new Random();
	
	/**
	 * Constructs a new HandEvaluator.
	 */
	public HandEvaluator() {
		for (String suit : suits) {
			for (int i = 1; i <= 13; i++) {
				deck.add(new Card(suit, i));
			}
		}
	}
	
	/**
	 * Draws a single Card from the deck and adds it to the hand.
	 */
	public void drawCard() {
		hand.add(deck.remove(random.nextInt(deck.size())));
	}
	
	/**
	 * Draws a number of cards from the deck and adds them all to the hand.
	 * @param handSize The number of cards to draw
	 */
	public void drawHand(int handSize) {
		for (int i = 0; i < handSize; i++) {
			drawCard();
		}
	}
	
	/**
	 * Gets the ArrayList representing the current hand.
	 * @return The current hand of cards
	 */
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	/**
	 * Counts the number of each card value in the hand
	 * @return An ArrayList representing the card values (indexed starting at 1)
	 */
	public int[] getValueCounts() {
		int[] valueCounts = new int[14];
		
		for (int i = 1; i < 13; i++) {
			valueCounts[i] = 0;
		}
		
		for (Card card : hand) {
			valueCounts[card.getValue()]++;
		}
		
		return valueCounts;
	}
	
	/**
	 * Determines whether a hand has n-of-a-kind of a single card value.
	 * @param n The count of a single card value required
	 * @return true if there is at least one card value which appears at least n
	 * times, false otherwise
	 */
	public boolean hasValueCount(int n) {
		int[] valueCounts = getValueCounts();
		
		for (int i = 1; i < valueCounts.length; i++) {
			if (valueCounts[i] >= n) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Determines whether the hand contains 2-of-a-kind.
	 * @return true if there is at least one card value which appears at least twice,
	 * false otherwise
	 */
	public boolean hasPair() {
		return hasValueCount(2);
	}
	
	/**
	 * Determines whether the hand contains 3-of-a-kind.
	 * @return true if there is at least one card value which appears at least three
	 * times, false otherwise
	 */
	public boolean hasThreeOfAKind() {
		return hasValueCount(3);
	}
	
	/**
	 * Determines whether the hand contains 4-of-a-kind.
	 * @return true if there is at least one card value which appears at least four
	 * times, false otherwise
	 */
	public boolean hasFourOfAKind() {
		return hasValueCount(4);
	}
	
	/**
	 * Determines whether the hand contains a straight, i.e. five cards with
	 * sequential values, e.g. A 2 3 4 5 or 8 9 10 J Q.<p>
	 * For simplicity, and since there seem to be conflicting house rules for what
	 * actually counts as a straight, an ace is assumed to always rank low, so 10 J Q
	 * K A does not count.
	 * @return true if there is a straight in the hand, false otherwise
	 */
	public boolean hasStraight() {
		int[] valueCounts = getValueCounts();
		
		int streak = 0;
		int maxStreak = 0;
		
		for (int i = 1; i < valueCounts.length; i++) {
			if (valueCounts[i] >= 1) {
				streak++;
				
				if (streak > maxStreak) {
					maxStreak = streak;
				}
			} else {
				streak = 0;
			}
		}
		
		return maxStreak >= 5;
	}
	
	/**
	 * Determines whether there are at least five cards in the hand of the same suit.
	 * @return true if there are five cards of one suit, false otherwise
	 */
	public boolean hasFlush() {
		int[] suitCounts = new int[suits.length];
		
		for (int i = 1; i < suitCounts.length; i++) {
			suitCounts[i] = 0;
		}
		
		for (Card card : hand) {
			for (int i = 0; i < suits.length; i++) {
				if (suits[i] == card.getSuit()) {
					suitCounts[i]++;
				}
			}
		}
		
		for (int i = 1; i < suitCounts.length; i++) {
			if (suitCounts[i] >= 5) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Determines whether the hand contains a full house, i.e. at least three cards of
	 * one value and at least two cards of a second value.
	 * @return true if there is a full house, false otherwise
	 */
	public boolean hasFullHouse() {
		int[] valueCounts = getValueCounts();
		
		boolean firstThreeFound = false;
		for (int i = 1; i < valueCounts.length && !firstThreeFound; i++) {
			if (valueCounts[i] >= 3) {
				firstThreeFound = true;
				valueCounts[i] = 0;
			}
		}
		
		if (!firstThreeFound) {
			return false;
		}
		
		for (int i = 1; i < valueCounts.length; i++) {
			if (valueCounts[i] >= 2) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Determines whether the hand contains a straight flush, i.e. a single five-card
	 * subset which is both a straight (values are all sequential) and a flush (same
	 * suit).
	 * @return true if there is a straight flush, false otherwise
	 */
	public boolean hasStraightFlush() {
		for (String suit : suits) {
			for (int i = 1; i <= 9; i++) {
				boolean straightBroken = false;
				
				for (int j = i; j <= i+4 && !straightBroken; j++) {
					boolean cardFound = false;
					
					for (Card card : hand) {
						if (card.getSuit() == suit && card.getValue() == j) {
							cardFound = true;
						}
					}
					
					if (!cardFound) {
						straightBroken = true;
					}
				}
				
				if (!straightBroken) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Determines whether the hand contains a royal flush, i.e. a single five-card
	 * subset 10 J Q K A, all of the same suit.<p>
	 * Unlike with hasStraight(), this method assumes that an ace is allowed to rank
	 * high, since otherwise this wouldn't be a straight.
	 * @return true if there is a royal flush, false otherwise
	 */
	public boolean hasRoyalFlush() {
		int[] royal = {1, 10, 11, 12, 13};
		
		for (String suit : suits) {
			boolean straightBroken = false;
			
			for (int j = 0; j < royal.length && !straightBroken; j++) {
				boolean cardFound = false;
				
				for (Card card : hand) {
					if (card.getSuit() == suit && card.getValue() == royal[j]) {
						cardFound = true;
					}
				}
				
				if (!cardFound) {
					straightBroken = true;
				}
			}
			
			if (!straightBroken) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Determines whether the hand is just a boring high-card hand, and doesn't meet
	 * any of the other criteria for n-of-a-kinds, straights, flushes, etc.
	 * @return true if all of the other methods return false, false otherwise
	 */
	public boolean isHighCard() {
		return !hasPair() && !hasThreeOfAKind() && !hasFourOfAKind() && !hasStraight() && !hasFlush() && !hasFullHouse() && !hasStraightFlush() && !hasRoyalFlush();
	}
}