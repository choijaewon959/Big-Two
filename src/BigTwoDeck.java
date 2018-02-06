
/**
 * Subclass of deck that inherits all the properties of superclass deck.
 * Makes BigTwo cards and add them to the deck of the BigTwo cards.
 * 
 * @author choijaewon
 */
public class BigTwoDeck extends Deck {
	
	/**
	 * Constructor that initializes the BigTwo cards to the deck.
	 */
	public BigTwoDeck() {
		this.initialize();
	}
	
	/**
	 * Overridden method from the superclass Deck.
	 * Initializes BigTwo cards and makes a BigTwo deck with them.
	 */
	public void initialize() {
		this.removeAllCards();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				BigTwoCard card = new BigTwoCard(i, j);
				this.addCard(card);
			}
		}
	}
}
