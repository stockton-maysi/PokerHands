import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TestHandEvaluator {
	public static void main(String[] args) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(new File("out.csv"));
		int n = 10000;
		int[][] stats = new int[2][9];
		
		for (int i = 0; i < n; i++) {
			HandEvaluator handEvaluator5 = new HandEvaluator();
			handEvaluator5.drawHand(5);
			
			HandEvaluator handEvaluator7 = new HandEvaluator();
			handEvaluator7.drawHand(7);
			
			HandEvaluator[] handEvaluators = {handEvaluator5, handEvaluator7};
			
			for (int j = 0; j < handEvaluators.length; j++) {
				if (handEvaluators[j].hasPair()) {
					stats[j][0]++;
				}
				
				if (handEvaluators[j].hasThreeOfAKind()) {
					stats[j][1]++;
				}
				
				if (handEvaluators[j].hasFourOfAKind()) {
					stats[j][2]++;
				}
				
				if (handEvaluators[j].hasStraight()) {
					stats[j][3]++;
				}
				
				if (handEvaluators[j].hasFlush()) {
					stats[j][4]++;
				}
				
				if (handEvaluators[j].hasFullHouse()) {
					stats[j][5]++;
				}
				
				if (handEvaluators[j].hasStraightFlush()) {
					stats[j][6]++;
				}
				
				if (handEvaluators[j].hasRoyalFlush()) {
					stats[j][7]++;
				}
				
				if (handEvaluators[j].isHighCard()) {
					stats[j][8]++;
				}
			}
		}
		
		String[] handTypes = {"Pair", "Three of a kind", "Four of a kind", "Straight", "Flush", "Full house", "Straight flush", "Royal flush", "High card (none of the above)"};
		
		out.println("Hand type,5 cards,7 cards");
		
		for (int i = 0; i < stats[0].length; i++) {
			out.printf("%s,%f,%f\n", handTypes[i], (double) stats[0][i]/n, (double) stats[1][i]/n);
		}
		
		out.close();
	}
}