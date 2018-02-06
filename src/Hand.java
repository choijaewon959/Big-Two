
/**
 * Abstract super class that is used for representing a combination of cards.
 * Contains player of this hand as a variable.
 * Contains methods for getting player of this hand, getting top card of this hand, checking whether this hand beats a certain hand.
 * Contains abstract methods to be overridden for checking if the hand is valid and getting the type of the hand.
 * 
 * @author choijaewon
 */
public abstract class Hand extends CardList{
	
	
	/**
	 * Constructor setting player of this hand and list of cards representing hand.
	 * @param player
	 * 		player of this hand.
	 * @param cards
	 * 		list of cards to compose this hand.
	 */
	public Hand(CardGamePlayer player, CardList cards) {
		    this.removeAllCards();
		    this.player=player;
			for (int i=0; i<cards.size(); i++) {
				this.addCard(cards.getCard(i));
		}
	}
	
	private CardGamePlayer player;
	

	/**
	 * Methods to be overridden in every subclass of this class.
	 * Used to getting value of each hand consisted of 5 cards.
	 * Hand that beats a certain hand gets the higher value.
	 * Straight - 0, Flush - 1, FullHouse - 2, Quad - 3, StraightFlush -4.
	 * 
	 * @return 0
	 * 		return meaningless value since it is going to be overridden.
	 */
	public int getValue() {
		return 0;
	}
	
	/**
	 * Retrieves player of this hand.
	 * @return player
	 * 		player of this hand.
	 */
	public CardGamePlayer getPlayer() {
		return this.player;
	}
	
	/**
	 * Retrieves the card that has highest value in hand according to the BigTwo card game.
	 * @return card
	 * 		card that has the highest value in hand.
	 */
	public Card getTopCard() {
		this.sort();
		
		// Getting top card of FullHouse.
		if( this.getType() == "FullHouse") {
			if(this.getCard(2).getRank()==this.getCard(1).getRank()) {
				return this.getCard(2);
			}
			else
				return this.getCard(4);
		}
		
		// Getting top card of Quad.
		else if (this.getType()=="Quad") {
			if (this.getCard(0)==this.getCard(1)) {
				return this.getCard(3);
			}
			else
				return this.getCard(4);
		}
		
		// Getting top card of the hands that can be obtained by sorting and getting the card in the last index.(Single,Pair,Triple,Straight,Flush,StraightFlush)
		return getCard(size()-1);
	}
	
	/**
	 * Checks if hand beats a certain hand.
	 * @param hand
	 * 		hand that is to be compared with the hand using this method.
	 * @return
	 * 		true when this hand wins.
	 * 		false when this hand loses.
	 */
	public boolean beats(Hand hand) {
		if (this.size()==hand.size()) { // compares only when number of cards in two hands are equal.
			if (this.getValue()>hand.getValue()) {    // compares different type of card with same number of cards and wins.
				return true;
			}
			else if (this.getValue()==hand.getValue()) {  //when comparing same type of card.
				int comp=this.getTopCard().compareTo(hand.getTopCard());
				if (comp == 1) { 		// when this top card wins.
					return true;
				}
				else if (comp == -1) {  // when this top card loses.
					return false; 
				}
			}
			else		// compares different type of card with same number of cards and loses.
				return false;
				
		}
		return false; // returns false when number of cards in hands are not equal.

	}
	
	/**
	 * Abstract method to be overridden in subclasses.
	 * Checks if this hand is valid or not.
	 * @return
	 * 		true when hand is valid.
	 * 		false when hand in invalid.
	 */
	public abstract boolean isValid();
	
	/**
	 * Abstract method to be overridden in subclasses.
	 * Retrieves the name of hand in string type.
	 * @return
	 * 		type of the hand which corresponds to the name of the hand in game.
	 */
	public abstract String getType();
	
}
