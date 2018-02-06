
/**
 * Subclass that inherits all the properties of the class Hand.
 * Overrides method for getting value of this card, checking if hand is satisfied to be Quad, getting the type of this hand.
 * 
 * @author choijaewon
 */
public class Quad extends Hand{
	
	/**
	 * Value of Quad representing the value among hands with five cards.
	 */
	private int value = 3;
	
	/**
	 * Constructor setting the player and list of cards representing this hand.
	 * @param player
	 * 		player of this hand.
	 * @param cards
	 * 		list of cards consisting this hand.
	 */
	public Quad(CardGamePlayer player, CardList cards) {
		super (player, cards);
	}
	
	/**
	 * Retrieves the value of Quad among hands of five cards.
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Checks if this hand is valid to be Quad.
	 * @return
	 * 		true if this hand is valid to be Quad.
	 * 		false if this hand is invalid to be Quad.
	 */
	public boolean isValid() {
		
		this.sort();
		boolean valid=true;
		
		if (this.size()==5) {
			
			// divides two cases: 1. card with different rank sorted to right. 2. card with different rank sorted to left.
			if(this.getCard(0).getRank()==this.getCard(1).getRank()) { // first case
				for (int i=0; i<3; i++) {
					if(this.getCard(i).getRank() != this.getCard(i+1).getRank()) {
						valid=false;
					}
				}
			}
			
			else if (this.getCard(4).getRank()==this.getCard(3).getRank()) { // second case
				for (int i=1; i<4; i++) {
					if( this.getCard(i).getRank() != this.getCard(i+1).getRank()) {
						valid =false;
					}
				}
			}
			else
				valid = false;
		}
		else
			valid= false;
		
		if (valid)
			return true;
		else
			return false;
		
		
	}
	
	/**
	 * Retrieves the type of this hand.
	 * @return "Quad"
	 * 		returns the type of this hand.
	 */
	public String getType() {
		return "Quad";
	}
}
