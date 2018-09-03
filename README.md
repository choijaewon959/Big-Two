# Big-Two
Big Two Card Game written by Java. 
Only runs on the local server.

Mainly Focused on Multi-thread handling on local server, Simple GUI for users to use, Updates of game status on each move of the players on each client UI, Updates of the connection status among players to local server, Real-time chatting functionality among players. 

# How to play
1.  Run BigTwoServer.java class.
2.  Run BigTwoClient.java class so that the total number of players in the game is 4. Game only starts when all the 4 players are ready to play the game.
3.  On each client console, it will notice the player of first turn.

# The Flow and Rules of the Game
* Standard 52 card pack is used.
* The order of ranks of the cards go from High to Low: 2, A, K, Q, J, 10, 9, 8, 7, 6, 5, 4, 3.
* The order of suits of the cards go from High to Low: Spades > Hearts > Clubs > Diamonds (♠>♥>♣>♦)
* Each four player is holding 13 randomly assigned card at the beginning.
* The player holding the Three of Diamonds will begin the game.
* Player in turn can play by playing a hand of legal combination of cards that beats the last hand of cards played on the table or passing his turn to the next player.
* A hand of legal combination of cards can only be beaten by another better hand of legal combination of cards with the SAME number of cards.
* A player CANNOT pass his turn to the next player if he is the one who played the last hand of cards on the table. In this case, he can play a hand of any legal combination of cards regardless of the last hand he played on the table.
* The game ends when any of the players has no more cards in his hand.

# Legal combinations of the cards
* Single: This hand consists of only one single card. The only card in a single is referred to as the top card of this single. A single with a higher rank beats a single with a lower rank. For sinlges with the same rank, the one with a higher suit beats the one with a lower suit.
* Pair: This hand consists of two cards with the same rank. The card with a higher suit in a pair is referred to as the top card of this pair. A pair with a higher rank beats a pair with a lower rank. For pairs with the same rank, the one containing the highest suit beats the other.
* Triple: This hand consists of three cards with the same rank. The card with the highest suit in a triple is referred to as the top card of this triple. A triple with a higher rank beats a triple with a lower rank.
* Straight: The hand consists of five cards with consecutive ranks. For the sake of simplicity, 2 and A can only form a striaght with K but not with 3. The card with the highest rank in a straight is referred to as the top card of this straight. A straight having a top card with a higher rank beats a straight having a top card with a lower rank. For straights having top cards with the same rank, the one having a top card with a higher suit beats the one having a top card with a lower suit.
* Flush:  This hand consists of five cards with the same suit. The card with the highest rank in a flush is referred to as the top card of this flush. A flush always beats any straights. A flush with a higher suit beats a flush with a lower suit. For flushes with the same suit, the one having a top card with a higher rank beats the one having a top card with a lower rank.
* Full House: This hand consists of five cards, with two having the same rank and three having another same rank. The card in the triplet with the highest suit in a full house is referred to as the top card of this full house. A full house always beats any straights and flushes. A full house having a top card with a higher rank beats a full house having a top card with a lower rank.
* Quad: This hand consists of five cards, with four having the same rank. The card in the quadruplet with the highest suit in a quad is referred to as the top card of this quad. A quad always beats any straights, flushes, and full houses. A quad having a top card with a higher rank beats a quad having a top card with a lower rank.
* Straight Flush: This hand consists of five cards with consecutive ranks and the smae suits. For the sake of the simplicity, 2 and A can only form a straight flush with K but not with 3. The card with the highest rank in a straight flush is referred to as the top card of this straight flush. A straight flush having a top card with a higher rank beats a straight flush having a top card with a lower rank. For straight flushes having top cards with the same rank, the one having a top card with a higher suit beats one having a top card with a lower suit.
