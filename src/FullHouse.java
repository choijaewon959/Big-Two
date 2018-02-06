
/**
 * Subclass that inherits all the properties of the class Hand.
 * Overrides method for getting value of this card, checking if hand is satisfied to be FullHouse, getting the type of this hand.
 * 
 * @author choijaewon
 */
public class FullHouse extends Hand{
	
	/**
	 * Value of FullHouse representing the value among hands with five cards.
	 */
	private int value = 2;
	
	/**
	 * Constructor setting the player and list of cards representing this hand.
	 * @param player
	 * 		player of this hand.
	 * @param cards
	 * 		list of cards consisting this hand.
	 */
	public FullHouse(CardGamePlayer player, CardList cards) {
		super (player, cards);
	}
	
	/**
	 * Retrieves the value of FullHouse among hands of five cards.
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Checks if this hand is valid to be FullHouse.
	 * @return
	 * 		true if this hand is valid to be FullHouse.
	 * 		false if this hand is invalid to be FullHouse.
	 */
	public boolean isValid() {
		this.sort();	// sort for convenience in comparison among cards.
		boolean valid = true;
		Card flag = this.getCard(2);  // sets the card in the center as the card to be compared with the cards on left and right.
		
		if (this.size()==5) {	// checks if this hand is consisted of five cards.
			
			if(this.getCard(1).getRank() == flag.getRank()) {	// when the rank of center card is equal to that of the card on left.
				if (this.getCard(0).getRank() != this.getCard(1).getRank()) {
					valid = false;
				}
				if (this.getCard(3).getRank() != this.getCard(4).getRank()) {
					valid = false;
				}
			}
			
			else if (this.getCard(3).getRank() == flag.getRank()) {	// when the rank of center card is equal to that of the card on right.
				if (this.getCard(3).getRank() != this.getCard(4).getRank()){
					valid = false;
				}
				if (this.getCard(0).getRank()!=this.getCard(1).getRank()) {
					valid = false;
				}
			}
			else
				valid=false;
		}
		else
			valid = false;
		
		if(valid) {
			return true;
		}
		else
			return false;
	
		
	}
	
	/**
	 * Retrieves the type of this hand.
	 * @return "FullHouse"
	 * 		returns the type of this hand.
	 */
	public String getType() {
		return "FullHouse";
	}
}
