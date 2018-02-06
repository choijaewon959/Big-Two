import java.awt.*;
import java.awt.event.*;


import javax.swing.*;


/**
 * Class that builds GUI for user interface.
 * Handles all user actions for interact with users and shows corresponding output corresponding to that actions.
 * 
 * @author choijaewon
 *
 */
public class BigTwoTable implements CardGameTable{
	/**
	 * Constructor that makes frame of GUI.
	 * 
	 * Separates the frame into 2 parts (Left: Panel for drawing cards, players, table.
	 * 									 Right: Panel for drawing text messages and chat box.)
	 * 
	 * Separates the right panel into 2 parts (Top: Panel for text messages,
	 * 										   Bottom: Panel for chat boxes.)
	 * 
	 * Adds panel to each of the bottom of Right and Left panels to put play/pass button to the left, 
	 * 																	textfield/send button to the right.
	 * @param game
	 * 		BigTwo game played by players.
	 */
	public BigTwoTable(CardGame game) {	
		
		this.selected = new boolean[13];
		this.game = game;
		frame = new JFrame();
		frame.setTitle("Big Two");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1,2,2,2));
		this.setActivePlayer(((BigTwoClient)game).getPlayerID());
		
		//sets the background condition for panel used for GUI.
		this.bigTwoPanel= new BigTwoPanel();
		bigTwoPanel.setLayout(new BorderLayout());
		bigTwoPanel.setBackground(Color.GRAY);
		bigTwoPanel.setOpaque(true);
		bigTwoPanel.setVisible(true);
		bigTwoPanel.setLocation(0, 0);
		
		//panel for play & pass button.
		JPanel buttonpanel = new JPanel();

		//make play button.
		this.playButton = new JButton("Play");
		Font bigFont1 = new Font("Arial", Font.BOLD, 18);
		playButton.setFont(bigFont1);
		bigTwoPanel.add(playButton,BorderLayout.WEST);
		bigTwoPanel.revalidate();
		
		//make pass button.
		passButton = new JButton("Pass");
		Font bigFont2 = new Font("Arial", Font.BOLD, 18);
		passButton.setFont(bigFont2);
		bigTwoPanel.add(passButton,BorderLayout.EAST);
		bigTwoPanel.revalidate();
		
		buttonpanel.add(playButton);
		buttonpanel.add(passButton);
		
		bigTwoPanel.add(buttonpanel,BorderLayout.SOUTH);
		
		//Listener for LeftPanel
		playButton.addActionListener(new PlayButtonListener());
		passButton.addActionListener(new PassButtonListener());
		bigTwoPanel.addMouseListener(new BigTwoPanel());
		
		
		//panel used for chat and text area.
		JPanel rightpanel = new JPanel();
		rightpanel.setLayout(new GridLayout(2,1,2,2));
		
		//panel for top right.
		JPanel topright = new JPanel(new BorderLayout());
	
		//text area
		msgArea = new JTextArea();
		msgArea.setBackground(Color.white);
		Font MsgFont = new Font("Arial",Font.PLAIN,20);
		msgArea.setFont(MsgFont);
	    msgArea.setEditable(false);
	    JScrollPane textScroller = new JScrollPane(msgArea);
	    textScroller.setVerticalScrollBarPolicy(
	    ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    textScroller.setHorizontalScrollBarPolicy(
	    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    topright.add(textScroller,BorderLayout.CENTER); // add message area to right panel.
	    
	    //panel for bottom right
	    JPanel bottomright = new JPanel(new BorderLayout());
	    
	    //dialogbox area
	    dialogbox =new JTextArea();
	    JScrollPane chatscroller = new JScrollPane(dialogbox);
	    dialogbox.setEditable(false);
	    dialogbox.setFont(new Font("Arial",Font.PLAIN,20));
	    chatscroller.setVerticalScrollBarPolicy(
	    ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    chatscroller.setHorizontalScrollBarPolicy(
	    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    bottomright.add(chatscroller,BorderLayout.CENTER);
	    
	    //Makes panel for chat and send buttons.
	    JPanel bottombottomright = new JPanel();
	   
	    //Makes chat area
	    chat = new JTextField(40);	
	    bottombottomright.add(chat,BorderLayout.WEST);
	    
	    // Makes send button
	    JButton send = new JButton("send");
	    bottombottomright.add(send,BorderLayout.EAST);
	    
	    bottomright.add(bottombottomright,BorderLayout.SOUTH);
	    
	    rightpanel.add(topright);
	    rightpanel.add(bottomright);
	    
	   
	  //Listener for RightPanel
	  	chat.addKeyListener(new EnterListener());
	  	send.addActionListener(new SendButtonListener());
	  		
	    
	    
	    //makes the menu and items in it.
		JMenuBar bar = new JMenuBar();
		JMenuItem connect = new JMenuItem("Connect");
		JMenuItem quit = new JMenuItem("quit");
		
		connect.addActionListener(new RestartMenuItemListener());
		quit.addActionListener(new QuitMenuItemListener());
		
		
		
		frame.setJMenuBar(bar);
		bar.add(connect);
		bar.add(quit);
		

		frame.add(bigTwoPanel); //panel on left (where cards are updated)
		frame.add(rightpanel);  // panel on left(where chat and text are updated)
		frame.setMinimumSize(new Dimension(1000,1000));
		frame.pack();
		frame.setVisible(true);
		
		//Makes dialog box that gets player name.	
		String input=null;
		input=JOptionPane.showInputDialog("Enter User Name");
		((BigTwoClient)game).setPlayerName(input);
		((BigTwoClient)game).setServerIP("127.0.0.1");
		((BigTwoClient)game).setServerPort(2396);

	}
	
	/**
	 * Variable that stores all the methods and variables for playing card game.
	 */
	private CardGame game;
	

	/**
	 * Boolean array to indicate which cards are selected by the player.
	 * stores true if the corresponding index of card is selected.
	 * false otherwise.
	 */
	private boolean[] selected;
	
	/**
	 * Integer value storing the current index of player.
	 */
	private int activePlayer;
	
	/**
	 * Frame used for GUI.
	 */
	private JFrame frame;
	
	/**
	 * Panel that is mainly used for GUI.
	 * 
	 */
	private JPanel bigTwoPanel;
	
	/**
	 * Button component for players to take the move with selected cards.
	 */
	private JButton playButton;
	
	/**
	 * Button component for players to take the move for passing the turn.
	 */
	private JButton passButton;
	
	/**
	 * Text area part showing the corresponding messages to the players.
	 */
	private JTextArea msgArea;
	
	/**
	 * 2D array storing the images of faces of cards.
	 */
	private Image[][] cardImages;
	
	/** 
	 * Image of back of the card.
	 */
	private Image cardBackImage;
	
	/**
	 * Array of avatar images.
	 */
	private Image[] avatars;
	
	/**
	 * Users type messages in this box.
	 */
	private JTextField chat;
	
	/**
	 * Box that shows the chats of players.
	 */
	private JTextArea dialogbox;
	
	
	/**
	 * Shows the chat content among clients in box.
	 * @param dialog
	 * 		Contents that clients typed.
	 */
	public void printDialog(String dialog) {
		this.dialogbox.append(dialog);
	}

	

	
	/**
	 * Set current player's index.
	 * 
	 * @param activePlayer
	 * 		Integer indicating the index of current player among the list of players.
	 */
	public void setActivePlayer(int activePlayer) {
		this.activePlayer = activePlayer;
	}
	
	/**
	 * Integer array storing the index of list of selected cards from the player.
	 * @return cardIdx
	 * 		Integer array of selected cards.
	 */
	public int[] getSelected() {
		int[] cardIdx = null;
		int count = 0;
		for (int i = 0; i < this.selected.length; i++) {
			if (this.selected[i]) {
				count++;
			}
		}

		if (count != 0) {
			cardIdx = new int[count];
			count = 0;
			for (int j = 0; j < this.selected.length; j++) {
				if (this.selected[j]) {
					cardIdx[count] = j;
					count++;
				}
			}
		}
		return cardIdx;
	}
	
	/**
	 * Resetting all the members of the array by making all the elements of the selected array false.
	 */
	public void resetSelected() {
		for (int i=0; i < this.selected.length; i++) {
			selected[i]=false;
		}
	}
	
	/**
	 * Repaints the panel whenever needed.
	 */
	public void repaint() {
	
			bigTwoPanel.repaint();
			bigTwoPanel.setOpaque(true);
			bigTwoPanel.setVisible(true);
	
	}
	
	/**
	 * Prints out the results after the game ends.
	 */
	public void endbox() {
			JOptionPane.showMessageDialog(null, "Game ends\n"
					+ game.getPlayerList().get(game.getCurrentIdx()).getName() + "wins the game.\n"
							+ game.getPlayerList().get(0).getName() + " has " + game.getPlayerList().get(0).getCardsInHand().size() + "cards.\n"
									+ game.getPlayerList().get(1).getName() + " has " + game.getPlayerList().get(1).getCardsInHand().size() + "cards.\n"
											+ game.getPlayerList().get(2).getName() + " has " + game.getPlayerList().get(2).getCardsInHand().size() + "cards.\n"
													+ game.getPlayerList().get(3).getName() + " has " + game.getPlayerList().get(3).getCardsInHand().size() + "cards.\n",
													"GameOver!",JOptionPane.PLAIN_MESSAGE);
	

	}
	
	/**
	 * Prints the message to the text area.
	 * 
	 * @param msg
	 * 		String that is to be shown on the text area.
	 */
	public void printMsg(String msg) {
		msgArea.append(msg+"\n");
	}
	
	/**
	 * Clears all the message printed on the text area.
	 */
	public void clearMsgArea() {
		this.msgArea.setText("");
	}
	
	/**
	 * Resetting the game by resetting selected cards, clearing the message, and enabling all the components.
	 */
	public void reset() {
		this.resetSelected();
		this.clearMsgArea();
		this.enable();
	}
	
	/**
	 * Enables all the components to interact with the users.
	 */
	public void enable() {
		playButton.addActionListener(new PlayButtonListener());
		passButton.addActionListener(new PassButtonListener());
		bigTwoPanel.addMouseListener(new BigTwoPanel());
	}
	
	/**
	 * Disables all the components.
	 */
	public void disable() {
		this.playButton.setEnabled(false);
		this.passButton.setEnabled(false);
		this.bigTwoPanel.setEnabled(false);
		
	}
	
	/**
	 * Designed panel on purpose to make more detailed panel.
	 * Listener class that enables the interaction with users by mouse.
	 * Extends JPanel.
	 * Implements MouseListener.
	 * 
	 * @author choijaewon
	 *
	 */
	class BigTwoPanel extends JPanel implements MouseListener{
		
		/**
		 * Method that mainly used for designing the panel.
		 * 
		 * @param g
		 * 		Graphics used for drawing.
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			bigTwoPanel.setOpaque(true);
			bigTwoPanel.revalidate();
			
			//storing images to arrays.
			avatars = new Image[4];
			avatars[0]=new ImageIcon("003-Character/png/72/batman_72.png").getImage();
			avatars[1]=new ImageIcon("003-Character/png/72/flash_72.png").getImage();
			avatars[2]=new ImageIcon("003-Character/png/72/superman_72.png").getImage();
			avatars[3]=new ImageIcon("003-Character/png/72/wonder_woman_72.png").getImage();
			
			cardBackImage=new ImageIcon("cards/b.gif").getImage();
			
			String fileloc="";
			String suit = "a";
			String rank = "a";
			cardImages = new Image[4][13];
			for (int i=0;i<4;i++) {
				for (int j=0; j<13; j++) {
					if (i==0) {	
						suit ="d";
					}
					else if (i==1) {
						suit="c";
					}
					else if (i==2) {
						suit = "h";
					}
					else if (i==3) {
						suit = "s";
					}

					if (j==0) {
						rank = "a";
						fileloc = "cards/a"+suit+".gif";
						cardImages[i][j]=new ImageIcon(fileloc).getImage();
					}
					else if (j==9) {
						fileloc="cards/t"+suit+".gif";
						cardImages[i][j]=new ImageIcon(fileloc).getImage();
					}
					else if (j==10) {
						rank = "j";
						fileloc = "cards/j"+suit+".gif";
						cardImages[i][j]=new ImageIcon(fileloc).getImage();
					}
					else if (j==11) {
						rank = "q";
						fileloc = "cards/q"+suit+".gif";
						cardImages[i][j]=new ImageIcon(fileloc).getImage();
					}
					else if (j==12) {
						rank = "k";
						fileloc = "cards/k"+suit+".gif";
						cardImages[i][j]=new ImageIcon(fileloc).getImage();
					}
					else {
						fileloc = "cards/"+(j+1)+suit+".gif";
						cardImages[i][j]=new ImageIcon(fileloc).getImage();
					}
					
					
				}
			}

		
			// draw lines and avatars along the panel.
			int linegap=0;
			for (int l=0; l<4; l++) {
				g.drawLine(0, (this.getHeight()-playButton.getHeight())/5+linegap, this.getWidth(), (this.getHeight()-playButton.getHeight())/5+linegap);
				linegap+=(this.getHeight()-playButton.getHeight())/5;
			}
			g.drawLine(0, this.getHeight()/5, this.getWidth(), this.getHeight()/5);
			
			//draw strings for players and table.
			int strgap=0;
			for (int s=0; s< 5; s++) {
				String str = "Player " +s;
				
				if (s==4) {
					String tablestr="";
					if (game.getHandsOnTable().size()==0) {//no cards on table.
						tablestr = "Empty";
					}
					
					else if(game.getHandsOnTable().size()!=0){
						Hand lastHandOnTable = (game.getHandsOnTable().isEmpty()) ? null : game.getHandsOnTable()
								.get(game.getHandsOnTable().size() - 1);
						tablestr = "Played by " + lastHandOnTable.getPlayer().getName();
					}
					
					g.drawString(tablestr, this.getWidth()/70, this.getHeight()/40+strgap);
				}
				else {
					g.drawString(str, this.getWidth()/50, this.getHeight()/50+strgap);
					g.drawImage(avatars[s], this.getWidth()/70, this.getHeight()/40+strgap, this);
					strgap+=this.getHeight()/5;
				}
				
			}
			
			// draws cards.
			for (int playerloop=0; playerloop<4; playerloop++) {	
				int xgap=0;
				int ygap=this.getHeight()/5;
				
			
				if (playerloop == activePlayer) {
					for (int i=0; i<game.getPlayerList().get(activePlayer).getCardsInHand().size(); i++) {
							Card card= game.getPlayerList().get(activePlayer).getCardsInHand().getCard(i);
							
							if (selected[i]==true) {
								g.drawImage(cardImages[card.getSuit()][card.getRank()], this.getWidth()/6+xgap, 10+ygap*playerloop-10,  this);
								xgap+=this.getWidth()/50;
							}
							else {
								g.drawImage(cardImages[card.getSuit()][card.getRank()], this.getWidth()/6+xgap, 10+ygap*playerloop,  this);
								xgap+=this.getWidth()/50;
							}
								
							
					}
				}
				else {
					for (int i=0; i< game.getPlayerList().get(playerloop).getCardsInHand().size();i++) {
						g.drawImage(cardBackImage, this.getWidth()/6+xgap, 10+ygap*playerloop, this);
						xgap+=this.getWidth()/50;
					}
				}
			}
	
			if (game.getHandsOnTable().size()!=0) {
				int xgap =0; 
				Hand handontable = game.getHandsOnTable().get(game.getHandsOnTable().size()-1);		
				
				
				for (int i=0; i<handontable.size();i++) {
					Card cardinhand = handontable.getCard(i);
					g.drawImage(cardImages[cardinhand.getSuit()][cardinhand.getRank()], this.getWidth()/6+xgap, 10+4*(this.getHeight()/5),this);
					xgap+=this.getWidth()/50;
				}
			}
		
			if (game.endOfGame()) {
				//disables all the components.
				playButton.setEnabled(false);
				passButton.setEnabled(false);
				bigTwoPanel.setEnabled(false);
			}
			else if (!game.endOfGame()){
				playButton.setEnabled(true);
				passButton.setEnabled(true);
				bigTwoPanel.setEnabled(true);
			}
			
		}

		/**
		 * Method for handling the event after the mouse is clicked.
		 * 
		 * @param e
		 * 		Mouse event indicating the mouse is clicked.
		 */
		public void mouseClicked(MouseEvent e) {
			int mouseclickx = e.getX();
			int mouseclicky = e.getY();
			if (game.getCurrentIdx()==((BigTwoClient)game).getPlayerID()) {
			if (mouseclicky > (bigTwoPanel.getHeight()/5)*activePlayer+10 && mouseclicky < (bigTwoPanel.getHeight()/5)*activePlayer+107) {// click the opened cards		
			
				for (int i=0; i <game.getPlayerList().get(activePlayer).getNumOfCards(); i++) {
					
						if (mouseclickx > bigTwoPanel.getWidth()/6 + bigTwoPanel.getWidth()/50*i 
								&& mouseclickx <  bigTwoPanel.getWidth()/6 +bigTwoPanel.getWidth()/50 * (i +1)) {
								selected[i]=true;
							
							}
						
						//when the card on right is raised and clicked the previously overlapped part.
						else if (i!=game.getPlayerList().get(activePlayer).getNumOfCards()-1) { // when it is not the last card on the right.
							if (selected[i+1]==true) {
								if (mouseclicky < (bigTwoPanel.getHeight()/5)*activePlayer+107 &&  // adjust condition for y coordinate
											mouseclicky > (bigTwoPanel.getHeight()/5)*activePlayer+107-10) {
									if (mouseclickx > bigTwoPanel.getWidth()/6 + bigTwoPanel.getWidth()/50*i &&  // adjust condition for x coordinate
											mouseclickx <  bigTwoPanel.getWidth()/6 +bigTwoPanel.getWidth()/50 *(i)+73) {
										selected[i]=true;
									
									}
								}
							}
						}
						
						
						//lastcard
						 if (mouseclickx > bigTwoPanel.getWidth()/6+bigTwoPanel.getWidth()/50*game.getPlayerList().get(activePlayer).getNumOfCards()&&
									mouseclickx <  bigTwoPanel.getWidth()/6 +bigTwoPanel.getWidth()/50 *(game.getPlayerList().get(activePlayer).getNumOfCards()-1)+73	) {
								selected[game.getPlayerList().get(activePlayer).getNumOfCards()-1]=true;
							
							}
					
					
				
				}
				
			
			
			}
			
				bigTwoPanel.repaint();
			}
		}

		/**
		 * Overriden method for mouse action.
		 */
		public void mouseEntered(MouseEvent arg0) {
		}

		/**
		 * Overriden method for mouse action
		 */
		public void mouseExited(MouseEvent arg0) {	
		}

		/**
		 * Overriden method for mouse action
		 */
		public void mousePressed(MouseEvent arg0) {
		}

		/**
		 * Overriden method for mouse action
		 */
		public void mouseReleased(MouseEvent arg0) {
		}
		
	}
	
	/**
	 * Action listener class for play button.
	 * 
	 * @author choijaewon
	 *
	 */
	class PlayButtonListener implements ActionListener{
		/**
		 * Method for handling the event after the play button is clicked.
		 * 
		 * @param event
		 * 		event that indicates mouse clicking.
		 */
		public void actionPerformed(ActionEvent event ) {
			if (game.getCurrentIdx()==((BigTwoClient)game).getPlayerID()) { // Only the current player can choose the cards.
				if(getSelected()!=null) { // Play button only acts when player selects the cards.
					game.makeMove(game.getCurrentIdx(), getSelected());
				}
				
			}
			
		}
	}
	
	/**
	 * Action listener class for pass button.
	 * 
	 * @author choijaewon
	 *
	 */
	class PassButtonListener implements ActionListener{
		/**
		 * Method for handling the event after the pass button is clicked.
		 * 
		 * @param event
		 * 		event that indicates mouse clicking.
		 */
		public void actionPerformed(ActionEvent event) {
			if (game.getCurrentIdx()==((BigTwoClient)game).getPlayerID()) {
				game.makeMove(activePlayer, null);
			}
			
		}
	}
	
	/**
	 * Action listener class for restart menu item.
	 * 
	 * @author choijaewon
	 *
	 */
	class RestartMenuItemListener implements ActionListener{
		/**
		 * Method for handling the event after the restart menu is clicked.
		 * Makes a new deck and starts a new game.
		 * Repaints the panel.
		 * Clears the text area.
		 * 
		 * @param event
		 * 		event that indicates mouse clicking.
		 */
		public void actionPerformed(ActionEvent event) {
			((BigTwoClient)game).makeConnection();
		}
	}
	
	/**
	 * Action listener class for quit menu item.
	 * 
	 * @author choijaewon
	 *
	 */
	class QuitMenuItemListener implements ActionListener{
		/**
		 * Method for handling the event after the quit menu is clicked.
		 * Exits the program.
		 * 
		 * @param event
		 * 		event that indicates mouse clicking.
		 */
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	}
		

	/**
	 * Key listener class for handling the case that enter key is pressed.
	 * 
	 * @author choijaewon
	 *
	 */
	class EnterListener implements KeyListener{
			 public void keyPressed (KeyEvent e) {
				 if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					 ((BigTwoClient)game).sendMessage(new CardGameMessage(7,-1,chat.getText())); // sends MSG type CardGameMessage to game server.
					 chat.setText("");
				 }
				
			 }
			 public void keyTyped(KeyEvent e) {
			 }
			 
			 public void keyReleased(KeyEvent e) {
			 }
	 	}
	 
	/**
	 * Action listener class for send button of chat.
	 * 
	 * @author choijaewon
	 *
	 */
	class SendButtonListener implements ActionListener{
		 public void actionPerformed(ActionEvent event) {
			 ((BigTwoClient)game).sendMessage(new CardGameMessage(7,-1,chat.getText())); // sends MSG type CardGameMessage to game server.
			 chat.setText(""); // clears the text in text field.
		 }
	 }
	
	
	
	
	
}
