import java.util.ArrayList;
import java.util.Random;

public class HandEvaluator {
	private ArrayList<Card> hand = new ArrayList<>();
	private ArrayList<Card> deck = new ArrayList<>();
	private String[] suites = {"♣", "♦", "♥", "♠"};
	private Random random = new Random();
	
	public HandEvaluator() {
		for (String suite : suites) {
			for (int i = 1; i <= 13; i++) {
				deck.add(new Card(suite, i));
			}
		}
	}
	
	public void drawCard() {
		hand.add(deck.remove(random.nextInt(deck.size())));
	}
	
	public void drawHand(int handSize) {
		for (int i = 0; i < handSize; i++) {
			drawCard();
		}
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
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
	
	public boolean hasValueCount(int n) {
		int[] valueCounts = getValueCounts();
		
		for (int i = 1; i < valueCounts.length; i++) {
			if (valueCounts[i] >= n) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasPair() {
		return hasValueCount(2);
	}
	
	public boolean hasThreeOfAKind() {
		return hasValueCount(3);
	}
	
	public boolean hasFourOfAKind() {
		return hasValueCount(4);
	}
	
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
	
	public boolean hasFlush() {
		int[] suiteCounts = new int[suites.length];
		
		for (int i = 1; i < suiteCounts.length; i++) {
			suiteCounts[i] = 0;
		}
		
		for (Card card : hand) {
			for (int i = 0; i < suites.length; i++) {
				if (suites[i] == card.getSuite()) {
					suiteCounts[i]++;
				}
			}
		}
		
		for (int i = 1; i < suiteCounts.length; i++) {
			if (suiteCounts[i] >= 5) {
				return true;
			}
		}
		
		return false;
	}
	
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
	
	public boolean hasStraightFlush() {
		for (String suite : suites) {
			for (int i = 1; i <= 9; i++) {
				boolean straightBroken = false;
				
				for (int j = i; j <= i+4 && !straightBroken; j++) {
					boolean cardFound = false;
					
					for (Card card : hand) {
						if (card.getSuite() == suite && card.getValue() == j) {
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
	
	public boolean hasRoyalFlush() {
		int[] royal = {1, 10, 11, 12, 13};
		
		for (String suite : suites) {
			boolean straightBroken = false;
			
			for (int j = 0; j < royal.length && !straightBroken; j++) {
				boolean cardFound = false;
				
				for (Card card : hand) {
					if (card.getSuite() == suite && card.getValue() == royal[j]) {
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
	
	public boolean isHighCard() {
		return !hasPair() && !hasThreeOfAKind() && !hasFourOfAKind() && !hasStraight() && !hasFlush() && !hasFullHouse() && !hasStraightFlush() && !hasRoyalFlush();
	}
}