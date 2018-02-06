import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Class that implements the client server for each player.
 * Includes all the information of one player.
 * 
 * 
 * @author choijaewon
 *
 */
public class BigTwoClient implements CardGame, NetworkGame {

	/**
	 * Constructor that makes one user of the game.
	 * Makes list of hands on table.
	 * Makes list of four different players.
	 * Creates players and add them to the list of players.
	 * Makes table used for making GUI.
	 * Connects to the Game Server.
	 * 
	 */
	public BigTwoClient() {
		
				
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
				
				// table for GUI
				table = new BigTwoTable(this);
				table.repaint();
				
				// conncect to BigTwoServer.
				this.makeConnection();
				
				
				
	}
	
	private int numOfPlayers;
	private Deck deck;
	private ArrayList<CardGamePlayer> playerList;
	private ArrayList<Hand> handsOnTable;
	private int playerID;
	private String playerName;
	private String serverIP;
	private int serverPort;
	private Socket sock;
	private ObjectOutputStream oos;
	private int currentIdx;
	private BigTwoTable table;
	private NetworkGame[] test = new NetworkGame[10];
	
	public void abc () {
		test[0]= new BigTwoClient();
	
	}
	
	/**
	 * Returns total number of players in this game.
	 * @return this.numOfPlayers.
	 * 		Integer value indicating the total number of players in this game.
	 */
	public int getNumOfPlayers() {
		return this.numOfPlayers;

	}
	
	/**
	 * Returns deck used for game.
	 * @return this.deck
	 * 			deck used for game.
	 */
	public Deck getDeck() {
		return this.deck;
		
	}
	
	/**
	 * Returns list of players of this game.
	 * @return this.playerList
	 * 			list that stores all the players.
	 */
	public ArrayList<CardGamePlayer> getPlayerList(){
		return this.playerList;
	}
	
	/**
	 * Returns hand on table.
	 * @return this.handsOnTable
	 * 		hand on the top of the table played by previous player.
	 */
	public ArrayList<Hand> getHandsOnTable(){
		return this.handsOnTable;
	}
	
	/**
	 * Returns index of player who is currently playing.
	 * @return this.currentIdx
	 * 		integer value indicating index of current player.
	 */
	public int getCurrentIdx() {
		return this.currentIdx;
	}
	
	/**
	 * Clears the message of the text area.
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
		
		//clears the textarea.
		table.clearMsgArea();

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
		table.setActivePlayer(this.getPlayerID());
		for (int i=0; i<playerList.size(); i++) {
			if(playerList.get(i).getCardsInHand().contains(diathree)) {
				
				this.currentIdx=i;
			}
		}
		
		this.table.repaint();
		table.printMsg(this.getPlayerList().get(this.getCurrentIdx()).getName() + "'s turn");
		
	}
	
	/**
	 * Sends the object type MOVE which includes the index of selected cards of a player to the BigTwoServer.
	 * @param playerID
	 * 		Integer value indicating the index of this client.
	 * @param cardIdx
	 * 		Integer array that stores the integer value of index of selected cards.
	 */
	public void makeMove(int playerID, int[] cardIdx) {
		try {
			oos.writeObject(new CardGameMessage(6, -1, cardIdx)); // 6 = MOVE , -1=ignored, cardIdx = Object
		}catch(Exception ex) {ex.printStackTrace();}
		
	}
	
	/**
	 * Checks the move by the player with certain ID is valid or not.
	 * Rejects the move if the move by the player is invalid.
	 * Prints out corresponding message to GUI according to the move by player.
	 * Decides whether to pass the turn to next player.
	 * Checks if game must be ended or not.
	 * 
	 * @param playerID
	 * 		Integer value indicating index of this client.
	 * @param
	 * 		Integer array that stores the integer value of index of selected cards.
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
							table.printMsg("Not a legal move!!!\n");
							
						}
						else {
							
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
						table.printMsg("Not a legal move!!!\n");
						
				}
				else {	// when hand is empty.
					table.printMsg("Not a legal move!!!\n");
				
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
							
						
							table.printMsg("Not a legal move!!!\n");
							
						}
					}
					
					// cannot pass when current player is the last one who played hand on the table.
					else if(this.getHandsOnTable().get(this.getHandsOnTable().size()-1).getPlayer().getName() == playerList.get(currentIdx).getName() ) { 
						table.printMsg("Not a legal move!!!\n");
					
					}

					else {
						table.printMsg("{pass}\n");
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
			table.endbox();
			this.sendMessage(new GameMessage(4, -1, null));
			table.disable();
			
		}
		
		else if (legalmove==true){
			// giving a turn to next player.
			currentIdx++; 
			if (currentIdx==4) {
				currentIdx=0;
			}
			
			
		}
		else
			table.repaint();
		
		
		table.resetSelected();
		table.printMsg(this.getPlayerList().get(currentIdx).getName() + "'s turn");
		table.repaint();
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
	 * Returns the index of this client.
	 * @return this.playerID
	 * 		Integer value indicating index of this user. 		
	 */
	public int getPlayerID() {
		return this.playerID;
	}
	
	/**
	 * Sets the index of this client.
	 * @param playerID
	 * 		Integer value indicating the index of this client.
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	
	/**
	 * Returns the string value of name of this client.
	 * @return this.playerName
	 * 		Name of this client.
	 * 		
	 */
	public String getPlayerName() {
		return this.playerName;
	}
	
	/**
	 * Sets the name of this client.
	 * @param playerName
	 * 		String value indicating the name of the player.
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	/**
	 * Returns the string value of IP address of game server.
	 * @return	this.serverIP
	 * 		Address of game server IP.
	 */
	public String getServerIP() {
		return this.serverIP;
	}
	
	/**
	 * Sets the IP of game server.
	 * @param serverIP
	 * 		String value of IP address of game server.
	 */
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	
	/**
	 * Returns the TCP port of game server.
	 * @return	this.serverPort
	 * 		Server port of game server.
	 */
	public int getServerPort() {
		return this.serverPort;
	}
	
	/**
	 * Sets the TCP port of game server.
	 * @param serverPort
	 * 		Integer value indicating the TCP port of game server.
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
	/**
	 * Makes socket connection with the game server.
	 * 
	 * Creates objects needed for sending and receiving messages from game server.
	 * Sends JOIN to game server.
	 * Sends READY to game server.
	 */
	public void makeConnection() {
		try {
			this.sock = new Socket("127.0.0.1" , 2396);
			this.oos = new ObjectOutputStream(sock.getOutputStream());
			Thread receiving = new Thread(new ServerHandler());
			receiving.start();
			oos.writeObject(new CardGameMessage(1 , -1 , this.getPlayerName())); // send JOIN to game server.
			oos.writeObject(new CardGameMessage(4 , -1 , null)); // send READY to game server.
			oos.flush();

						
		} catch (Exception ex) {ex.printStackTrace();}
			
	}
	
	/**
	 * Parses the messages from the server according to the types of the messages.
	 * @param message
	 * 		GameMessage type object that contains (type, PlayerID, object type data) as parameters.
	 */
	public void parseMessage(GameMessage message) {
		if (message.getType()==0) { // PLAYER_LIST
			this.setPlayerID(message.getPlayerID());   //update Local ID
			
			//update names in the player list.
			String[] namesofp;
			namesofp=(String[])message.getData();
			for(int i=0; i<namesofp.length; i++) {
				this.playerList.get(i).setName(namesofp[i]);
			}
		}
		
		else if (message.getType()==1) { // JOIN
			
			this.playerList.get(message.getPlayerID()).setName((String)message.getData()); // Update name of local player.
			this.numOfPlayers++; // Increase the total number of player.
		}
		
		else if (message.getType()==2) { //FULL
			this.table.printMsg("Server is full. Cannot Join");
		}
		
		else if (message.getType()==3) { //QUIT
			this.playerList.get(message.getPlayerID()).setName(""); // Remove player from the list by updating his or her name as empty string.
			this.sendMessage(new CardGameMessage(4,-1,null)); 
		}
		
		else if (message.getType()==4) { //READY
			this.table.printMsg(this.getPlayerList().get(message.getPlayerID()).getName() + "(Player" +  this.getPlayerID()+") is ready");
		}
		
		else if (message.getType()==5) { //START
			this.start((BigTwoDeck)message.getData()); //Start the game.
		}
		
		else if (message.getType()==6) { //MOVE
			this.checkMove(message.getPlayerID(), (int[])message.getData()); // Checks if hand of player currently playing is valid or not.
		}
		
		else if(message.getType()==7) { //MSG
			this.table.printDialog((String)message.getData()+"\n"); // Updates the chat box among players.
		}
	}
	
	/**
	 * Sends the specified messages to the game server.
	 * @param message
	 * 		GameMessage that includes (type, playerID, data object) as parameters.
	 */
	public void sendMessage(GameMessage message) {
		try {
			
			oos.writeObject(new CardGameMessage(message.getType(), message.getPlayerID(), message.getData())); // sends message to the game server.
			oos.flush();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Inner class implementing Runnable interface.
	 * @author choijaewon
	 *
	 */
	class ServerHandler implements Runnable {
		ObjectInputStream recipient;
		
		public ServerHandler() { // constructor
			try {
				 recipient = new ObjectInputStream(sock.getInputStream());
			}catch(Exception ex) {
				
				ex.printStackTrace();}
		}


		/**
		 * Receives the message from the server and parses it accordingly.
		 */
		public void run() {
			CardGameMessage messa ;
			try {
				while (true) {
					messa= (CardGameMessage)recipient.readObject();
					parseMessage(messa);
				
				}
				
				
			}catch(Exception ex) {
				
				ex.printStackTrace();}
			
		}
		
		
		

		
		
	}
	
	/**
	 * Main that creates a new user object.
	 * @param args
	 * 		Not being used.
	 */
	public static void main(String[] args) {
		BigTwoClient user = new BigTwoClient();
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
