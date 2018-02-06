
/**
 * Subclass that inherits all the properties of the class Hand.
 * Overrides method for checking if hand is satisfied to be Single and getting the type of this hand.
 * 
 * @author choijaewon
 */
public class Single extends Hand {
	
	/** 
	 * Constructor setting the player and list of cards representing this hand.
	 * @param player
	 * 		player of this hand.
	 * @param cards
	 * 		list of cards consisting this hand.
	 */
	public Single(CardGamePlayer player, CardList cards) {
		super (player, cards);
	}
	
	/**
	 * Checks if this hand is valid to be Single.
	 * @return
	 * 		true if this hand is valid to be Single.
	 * 		false if this hand is invalid to be Single.
	 */
	public boolean isValid() {
		if (this.size()==1) {
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Retrieves the type of this hand.
	 * @return "Single"
	 * 		returns the type of this hand.
	 */
	public String getType() {
		return "Single";
	}
}
