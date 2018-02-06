
/**
 * Subclass that inherits all the properties of the class Hand.
 * Overrides method for getting value of this card, checking if hand is satisfied to be Flush, getting the type of this hand.
 * 
 * @author choijaewon
 */
public class Flush extends Hand{
	
	/**
	 * Value of Flush representing the value among hands with five cards.
	 */
	private int value =1;
	
	/**
	 * Constructor setting the player and list of cards representing this hand.
	 * @param player
	 * 		player of this hand.
	 * @param cards
	 * 		list of cards consisting this hand.
	 */
	public Flush(CardGamePlayer player, CardList cards) {
		super (player, cards);
	}
	
	/**
	 * Retrieves the value of Flush among hands of five cards.
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Checks if this hand is valid to be Flush.
	 * @return
	 * 		true if this hand is valid to be Flush.
	 * 		false if this hand is invalid to be Flush.
	 */
	public boolean isValid() {
		this.sort();
		boolean valid =true;
		
		if (this.size()==5) {	// checks if this hand is consisted of five cards.
			for (int i=0; i<4; i++){
				if (this.getCard(i).getSuit() != this.getCard(i+1).getSuit()) {
					valid = false;
				}
			}
		}
		else
			valid = false;
		
		if (valid) {
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Retrieves the type of this hand.
	 * @return "Flush"
	 * 		returns the type of this hand.
	 */
	public String getType() {
		return "Flush";
	}

}
