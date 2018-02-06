
/**
 * Subclass that inherits all the properties of the class Hand.
 * Overrides method for checking if hand is satisfied to be Triple and getting the type of this hand.
 * 
 * @author choijaewon
 */
public class Triple extends Hand{
	
	/**
	 * Constructor setting the player and list of cards representing this hand.
	 * @param player
	 * 		player of this hand.
	 * @param cards
	 * 		list of cards consisting this hand.
	 */
	public Triple(CardGamePlayer player, CardList cards) {
		super (player, cards);
	}
	
	/**
	 * Checks if this hand is valid to be Triple.
	 * @return
	 * 		true if this hand is valid to be Triple.
	 * 		false if this hand is invalid to be Triple.
	 */
	public boolean isValid() {
		boolean valid = true;
		if (this.size()==3) {
			for (int i=0; i<2; i++) {
				if(this.getCard(i).getRank() != this.getCard(i+1).getRank()) {
					valid =false;
				}
			}
		}
		else
			valid = false;
		
		if (valid ==true) {
			return true;
		}
		
		else 
			return false;
	}
	
	/** 
	 * Retrieves the type of this hand.
	 * @return "Triple"
	 * 		returns the type of this hand.
	 */
	public String getType() {
		return "Triple";
	}
}
