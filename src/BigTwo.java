
import java.util.ArrayList;

/**
 * Class where the logic of game is implemented.
 * Actual game begins in main part in this class.
 * Includes card deck, list of players consisting of 4, hands of cards on table, console used for BigTwo game as variables.
 * Implements methods used for retrieving the deck, list of players, current index of player, making hand and start method where the logic of game is implemented. 
 * 
 * @author choijaewon
 */
public class BigTwo implements CardGame{
	
	/**
	 * card deck used for the game.
	 */
	private Deck deck = new Deck();
	
	/**
	 * List of players playing the BigTwo card game.
	 */
	private ArrayList<CardGamePlayer> playerList;
	
	/**
	 * List of hands on table played by each players.
	 */
	private ArrayList<Hand> handsOnTable;
	
	/**
	 * Index indicating current player in the game.
	 */
	private int currentIdx;
	
	
	
	/**
	 * Table used for GUI.
	 */
	private BigTwoTable table;

	/**
	 *  Constructor used for creating four players,list of players,and adding them to list of player.
	 *  Declares list of hands on the table used for storing hands played by the players.
	 *  Creates a GUI for providing user interface.
	 */
	public BigTwo() {

		handsOnTable = new ArrayList<Hand>(); // makes list of hands on table.
		playerList= new ArrayList<CardGamePlayer>(); // makes list of four different players.
		
		// creates four players playing the BigTwo game.
		CardGamePlayer a = new CardGamePlayer();
		CardGamePlayer b= new CardGamePlayer();
		CardGamePlayer c= new CardGamePlayer();
		CardGamePlayer d= new CardGamePlayer();
	   
		// add players to the player list.
		this.playerList.add(a);
		this.playerList.add(b);
		this.playerList.add(c);
		this.playerList.add(d);
		
		// make Big Two table for GUI which handles user actions.
		//table = new BigTwoTable(this);
	}
	
	/**
	 * Retrieves the number of players playing the game.
	 * @return	playerList.size()
	 * 		Number of player participating the game.
	 */
	public int getNumOfPlayers() {
		return this.playerList.size();
	}
	
	/**
	 * Retrieves the deck used for the BigTwo game.
	 * @return deck
	 * 		deck used for the game.
	 */
	public Deck getDeck() {
		return this.deck;
	}
	
	/**
	 * Retrieves the list of players.
	 * @return playerList
	 * 		List of four different players. 
	 */
	public ArrayList<CardGamePlayer> getPlayerList(){
		return this.playerList;
	}
	
	/**
	 * Retrieves the list of hands on the table played by the players.
	 * @return handsOnTable
	 * 		List of hands on the table.
	 */
	public ArrayList<Hand> getHandsOnTable(){
		return this.handsOnTable;
	}
	
	/**
	 * Retrieves the current index of players used for identifying the current player.
	 * @return currentIdx
	 * 		Current index used in list of players to identify current player.
	 */
	public int getCurrentIdx() {
		return this.currentIdx;
	}
	
	/**
	 * Checks if the game must be ended or not.
	 * 
	 * @return 
	 * 		true if a current player has no cards in hand.
	 * 		false otherwise.
	 */
	public boolean endOfGame() {
		if(playerList.get(currentIdx).getCardsInHand().size()==0) {
			return true;
		}
		else
			return false;
	}
	
	/** 
	 * Player with certain ID makes a move using a selected card.
	 * Calls checkMove method to check whether the move is valid or not.
	 * 
	 * @param playerID
	 * 		Integer value indicating certain player.
	 * @param cardIdx
	 * 		List of indices of cards selected by the player.
	 */
	public void makeMove(int playerID, int[] cardIdx) {
		checkMove(playerID, cardIdx);
	}
	
	/**
	 * Checks the move by the player with certain ID is valid or not.
	 * Rejects the move if the move by the player is invalid.
	 * Prints out corresponding message to GUI according to the move by player.
	 * Decides whether to pass the turn to next player.
	 * Checks if game must be ended or not.
	 * 
	 * @param playerID
	 * 		Interger value indicating certain player.
	 * @param
	 * 		List of indices of cards selected by the player.
	 */
	public void checkMove(int playerID, int[] cardIdx) {
			
			
			boolean legalmove = false; 	// check if the move is a valid move or not.
			BigTwoCard diathree = new BigTwoCard(0,2);

			if (this.handsOnTable.isEmpty()) {  // when there is no hand on table(first turn)

					CardList selected=playerList.get(currentIdx).play(cardIdx);
					
					if(selected!=null) {  // when hand is not empty.
						Hand validhand = composeHand(playerList.get(currentIdx) , selected);
						
						if (validhand!=null) { // when hand is a valid hand.
							
							// check if first player plays a hand containing the Three of Diamond.
							if (!validhand.contains(diathree)) {
								table.printMsg("Not a legal move!!!");
								
							}
							else {
								//System.out.println("test");
								this.handsOnTable.add(validhand);
								table.printMsg("<"+playerList.get(currentIdx).getName()+">"+" "+"{"+validhand.getType()+"}"+" ");
								table.printMsg(validhand.toString());
								
								for (int i=0; i<validhand.size(); i++) {
									playerList.get(currentIdx).getCardsInHand().removeCard(validhand.getCard(i));
								}
								legalmove=true;
							
							}
							
						}
						else	// when hand is not a valid hand.
							table.printMsg("Not a legal move!!!");
							
					}
					else {	// when hand is empty.
						table.printMsg("Not a legal move!!!");
					
					}
		
			}
			
			else if (legalmove==false) { // not a first turn.
					
						CardList selected=playerList.get(currentIdx).play(cardIdx);
						
					
						if(selected!=null) {
							Hand validhand = composeHand(playerList.get(currentIdx) , selected);
							
							// when every other player passed after the last move of current player.(can play hand no matter the hand on the table).
							if (validhand!=null && 	   
								this.playerList.get(currentIdx).getName() == this.getHandsOnTable().get(this.getHandsOnTable().size()-1).getPlayer().getName()) {  
								this.handsOnTable.add(validhand);
								table.printMsg("<"+playerList.get(currentIdx).getName()+">"+" "+validhand.getType()+" ");
								table.printMsg(validhand.toString());
								
			
								for (int i=0; i<validhand.size(); i++) {
									playerList.get(currentIdx).getCardsInHand().removeCard(validhand.getCard(i));
								}
								legalmove=true;
							}
							
							// normal condition that hands of player should beat hand on table.
							else if (validhand!=null && validhand.beats(handsOnTable.get(handsOnTable.size()-1))) {  
								this.handsOnTable.add(validhand);
								table.printMsg("<"+playerList.get(currentIdx).getName()+">"+" "+validhand.getType()+" ");
								table.printMsg(validhand.toString());
								
								
			
								for (int i=0; i<validhand.size(); i++) {
									playerList.get(currentIdx).getCardsInHand().removeCard(validhand.getCard(i));
								}
								legalmove=true;
							}
							
							// when hand of player cannot beat the last hand on table.
							else {
								
							
								table.printMsg("Not a legal move!!!");
								
							}
						}
						
						// cannot pass when current player is the last one who played hand on the table.
						else if(this.getHandsOnTable().get(this.getHandsOnTable().size()-1).getPlayer().getName() == playerList.get(currentIdx).getName() ) { 
							table.printMsg("Not a legal move!!!");
						
						}

						else {
							table.printMsg("{pass}");
							legalmove=true;
							
						}
	
					
				}
			
			
			
			//checks if game ended
			if (this.endOfGame()) {
				// game ends and prints out the winner and the cards in hand of other players.
				table.printMsg("Game ends");
				for (int end=0; end<this.playerList.size(); end++) {
				
					if (end==this.getCurrentIdx()) {
						table.printMsg(this.playerList.get(currentIdx).getName()+ " wins the game.");
					}
					else
						table.printMsg(this.playerList.get(end).getName()+ " has "+ this.playerList.get(end).getNumOfCards() + " cards in hand.");
				}
				table.disable();
				
			}
			
			else if (legalmove==true){
				// giving a turn to next player.
				currentIdx++; 
				if (currentIdx==4) {
					currentIdx=0;
				}
				
				table.setActivePlayer(getCurrentIdx()); // player gets a turn.
			}
			else
				table.repaint();
			
			
			table.resetSelected();
			table.repaint();
			
	}

	
	/**
	 * Sets the conditions for the game.
	 * Starts the game with a deck.
	 * Removes all cards from players and table.
	 * Distributes cards to each player.
	 * Identifies who has diamond three thus making the player as the first player to play.
	 * Updates the value of current player and active player by identifying who is the first player.
	 * 
	 * @param deck
	 * 		BigTwo card deck used for game.
	 */
	public void start(Deck deck) {

		//removing all cards from players.
		for (int i=0; i<playerList.size(); i++) {
				playerList.get(i).removeAllCards();
		}
		
		//removes all cards from the table.
		handsOnTable.clear();
		
		//distributing cards to player.
		for (int a=0; a<playerList.size(); a++) {
			for (int i=0; i<13; i++) {
				playerList.get(a).addCard(deck.getCard(0));
				
				deck.removeCard(0);
			}
			playerList.get(a).sortCardsInHand();
		}
		

		
		//making a player with diamond three as a first player.
		BigTwoCard diathree = new BigTwoCard(0,2);
		for (int i=0; i<playerList.size(); i++) {
			if(playerList.get(i).getCardsInHand().contains(diathree)) {
				table.setActivePlayer(i);
				currentIdx=i;
			}
		}
		
	}


	/**
	 * Main where the game starts.
	 * Creates the BigTwo game.
	 * Creates the BigTwo deck, shuffles it and passes to the game.
	 * @param args
	 * 		not being used.
	 */
	public static void main(String[] args) {
		
			BigTwo game = new BigTwo(); // creates game.
			
			// sets the shuffled deck for game.
			BigTwoDeck deckforgame = new BigTwoDeck();
			deckforgame.initialize();
			deckforgame.shuffle();
			
			// starts the game with the BigTwo deck.
			game.start(deckforgame);
			 
	}
	
	/**
	 * Composes valid hand according to the condition of each hand of the BigTwo card game.
	 * 
	 * @param player
	 * 		player who selected this hand.
	 * @param cards
	 * 		selected cards by the player.
	 * @return Hand
	 * 		returns one of Single, Pair, Triple, Straight, Flush, FullHouse, Quad, and StraightFlush if the hand is valid.
	 * 	 	returns null if it is not a valid hand.
	 */
	public static Hand composeHand(CardGamePlayer player, CardList cards) {
		Hand candidate;
		
		// check if the hand is one of valid hands and returns the corresponding valid hand.
		candidate = new StraightFlush(player,cards);
		if(candidate.isValid()) {
			return candidate;
		}
	
		candidate = new Single(player, cards);
		if (candidate.isValid()) {
			return candidate;
		}
		
		candidate = new Pair(player,cards);
		if (candidate.isValid()) {
			return candidate;
		}
		
		candidate = new Triple(player,cards);
		if (candidate.isValid()) {
			return candidate;
		}
		
		candidate = new Straight(player,cards);
		if (candidate.isValid()) {
			return candidate;
		}
		
		candidate = new Flush(player,cards);
		if (candidate.isValid()) {
			return candidate;
		}
		
		candidate = new Quad(player,cards);
		if (candidate.isValid()) {
			return candidate;
		}
		
		candidate = new FullHouse(player,cards);
		if(candidate.isValid()) {
			return candidate;
		}
		
		// returns null if it is not a valid hand.
		else
			return null;
		
	}
}
