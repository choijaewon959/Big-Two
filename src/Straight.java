
/**
 * Subclass that inherits all the properties of the class Hand.
 * Overrides method for getting value of this card, checking if hand is satisfied to be Straight, getting the type of this hand.
 * 
 * @author choijaewon
 */
public class Straight extends Hand{
	
	/**
	 * Value of Straight representing the value among hands with five cards.
	 */
	private int value=0; 
	
	/**
	 * Constructor setting the player and list of cards representing this hand.
	 * @param player
	 * 		player of this hand.
	 * @param cards
	 * 		list of cards consisting this hand.
	 */
	public Straight(CardGamePlayer player, CardList cards) {
		super (player, cards);
	}
	
	/**
	 * Retrieves the value of Straight among hands of five cards.
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Checks if this hand is valid to be Straight.
	 * @return
	 * 		true if this hand is valid to be Straight.
	 * 		false if this hand is invalid to be Straight.
	 */
	public boolean isValid() {
		this.sort();
	    boolean valid = true;
	    
	    if (this.size()==5) {
		    	for (int i=0; i<4; i++) {
					int current = this.getCard(i).getRank();
					int next = this.getCard(i+1).getRank();
					
					// when the card is 'Ace' or 'Two', give it higher value according to the rule of the BigTwo game.
					if (current == 1 || current ==0) {
						current+=13;
					}
					if(next == 1|| next == 0) {
						next+=13;
					}
		    		if (current!=next-1) {
		    			valid =false;
		    		}
		    	}
		    	
	    }
	    else
	    	valid=false;
	    
	    if (valid) {
	    	return true;
	    }
	    else
	    	return false;
	}
	
	/**
	 * Retrieves the type of this hand.
	 * @return "Straight"
	 * 		returns the type of this hand.
	 */
	public String getType() {
		return "Straight";
	}
}
