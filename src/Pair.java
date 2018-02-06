
/**
 * Subclass that inherits all the properties of the class Hand.
 * Overrides method for checking if hand is satisfied to be Pair and getting the type of this hand.
 * 
 * @author choijaewon
 */
public class Pair extends Hand{
	
	/**
	 * Constructor setting the player and list of cards representing this hand.
	 * @param player
	 * 		player of this hand.
	 * @param cards
	 * 		list of cards consisting this hand.
	 */
	public Pair(CardGamePlayer player, CardList cards) {
		super (player, cards);
	}
	
	/**
	 * Checks if this hand is valid to be Pair.
	 * @return
	 * 		true if this hand is valid to be Pair.
	 * 		false if this hand is invalid to be Pair.
	 */
	public boolean isValid() {
		if (this.size()==2) {
			if(this.getCard(0).getRank()==this.getCard(1).getRank()) {
				return true;
			}
			else 
				return false;
		}
		else 
			return false;
	}
	
	/**
	 * Retrieves the type of this hand.
	 * @return "Pair"
	 * 		returns the type of this hand.
	 */
	public String getType() {
		return "Pair";
	}
}
