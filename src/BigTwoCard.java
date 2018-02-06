
/**
 * Subclass of card that inherits the properties of card.
 * Modified according to the rule of the BigTwo Card game.
 * Overrides compareTo method.
 * 
 * @author choijaewon
 */
public class BigTwoCard extends Card{
	
	
	/**
	 * Constructor setting the suit and rank of the card used in the BigTwo card game.
	 * @param suit
	 * 		integer value of suit to be modified according to corresponding card suit.
	 * @param rank
	 * 		integer value of rank to be modified according to corresponding card rank.
	 */
	public BigTwoCard(int suit, int rank) {
		super(suit, rank);
	}
	
	/**
	 * Overridden method from superclass Card.
	 * Compares the BigTwo card for order according to the BigTwo game.
	 * Ace and Two get bigger value than they do in normal cards.
	 * @param card
	 * 		card that is compared to the card using this method.
	 * @return a negative integer, zero, or a positive integer as this card is
	 *         less than, equal to, or greater than the specified card.
	 */
	public int compareTo(Card card) {
		
		int cardrank = this.getRank();
		int targetrank= card.getRank();
		
		// when rank of card is 'Ace' or 'Two', make them to have proper value according to the rule of the game.
		if (cardrank==0 || cardrank==1) {
			cardrank=cardrank+13;
		}
		if (targetrank==0 || targetrank==1) {
			targetrank=targetrank+13;
		}
		
		if (cardrank > targetrank) {
			return 1;
		} else if (cardrank < targetrank) {
			return -1;
		} else if (this.getSuit() > card.getSuit()) {
			return 1;
		} else if (this.getSuit() < card.getSuit()) {
			return -1;
		} else {
			return 0;
		}
	}
}
