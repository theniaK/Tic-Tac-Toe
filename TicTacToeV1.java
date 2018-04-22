import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class TicTacToeV1 {
    
	private String letter, text;
	private int counter = 0,  //determines whose turn it is
	            pl1wins = 0,  //num. of wins for player1 (X)
	            pl2wins = 0;  // num. of wins for player2 (O)
	private JLabel winsStatus =  new JLabel(), //shows the score of the players
                   whoseTurn = new JLabel();	//shows whose turn it is
	private JPanel playingField = new JPanel(),  //the main area of the game
	               menuPanel = new JPanel(),     //the area that contains various options for the game
	               bottomPanel = new JPanel();   //the area that shows current info throughout the game (score and turn).
	private JFrame mywindow = new JFrame("Tic-Tac-Toe : Player Vs Player Version ");
    private JButton buttons[]= new JButton[9];
    private JMenuItem newGame = new JMenuItem("New Game"), //The options to be contained in the menu panel
                    menuInstr = new JMenuItem("Instructions"),
                    exit = new JMenuItem("Exit"); 
    private ButtonListener listener = new ButtonListener();
    
    
    private int winningCombos[][] = new int [][] {
			{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, //Horizontal wins
			{0, 3, 6}, {1, 4, 7}, {2, 5, 8}, //Vertical wins
			{0, 4, 8}, {2, 4, 6} };          //Diagonal wins 
    private boolean matchwon = false;
    
    
    public TicTacToeV1(){
    // This method creates the game.	
    	
    //Elements of the bottom panel of the game, showing the score and whose turn it is.
    //The bottom panel is updated after each turn of the game.(See below in actionPerformed method).	
    winsStatus.setText("[Player1 (X):"+pl1wins+"]  ^^  [Player2 (O):"+pl2wins+"]");	
    whoseTurn.setText("TURN: X");
    
    //The instructions of the game. They show up when the window opens for the first time.
    //Players can also see them anytime by clicking the "Instructions" button.	
    text = "Instructions:\n\n" +
	"-Your goal is to be the first player to get 3 X's or O's in a\n" +
	" row. (horizontally, diagonally, or vertically)\n" +
	"-Player1  : X\n" +
	"-Player2  : O\n"+
	"-When the game ends (either in a win or a tie) you can start\n" +
	" a new one by clicking the 'New Game' button on the menu bar.\n"+
	"-You may see this message again throughout the game by clicking\n"+
	" the 'Instructions' button.\n"+
	"-You can exit the game by clicking the 'Exit' button.";
		
    //Create window.
    mywindow.setSize(500,500);
    mywindow.setLayout(new BorderLayout());
    mywindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //Create playing field
    playingField.setSize(300, 300);
	playingField.setLayout(new GridLayout(3,3));
   
	
	for (int i=0; i<=8; i++){
    	buttons[i]= new JButton(" ");  //Create buttons
    	buttons[i].setBackground(Color.black);
     	buttons[i].addActionListener(listener);   //Adds the action listener to the buttons
     	playingField.add(buttons[i]);        //Adds buttons to the playing field panel
    }
    
    menuPanel.add(newGame);
    menuPanel.add(menuInstr);
    menuPanel.add(exit); //Menu panel is now complete
    
    bottomPanel.add(winsStatus);
    bottomPanel.add(whoseTurn); //Bottom panel is now complete
    
    
    mywindow.add(menuPanel, BorderLayout.NORTH); //Sets up the window
    mywindow.add(bottomPanel, BorderLayout.SOUTH);
    mywindow.add(playingField, BorderLayout.CENTER);
	
	
    
    newGame.addActionListener(listener);
    menuInstr.addActionListener(listener);
    exit.addActionListener(listener);
    
    /*Make The Window Visible*/
    mywindow.setVisible(true);
    
    JOptionPane.showConfirmDialog(null, text, "Instructions", JOptionPane.PLAIN_MESSAGE); //With this statement, the instructions appear 
                                               //on the screen for the first time. 
    }
    
   
    
    public class ButtonListener implements ActionListener {
    	
        public void actionPerformed(ActionEvent click) {
    
    	Object source = click.getSource();
    	
    	for (int i=0; i<9; i++){
    		if (source==buttons[i]&&counter<9&&(!matchwon)){
    			if (counter%2 == 0) letter= "X"; //player with "X" begins
            	else letter = "O";
            	
            	JButton pressedButton = (JButton)click.getSource();
            	pressedButton.setText(letter);
            	pressedButton.setEnabled(false);
            	playingField.requestFocusInWindow();
            	
            	// Check if a player has won
            	for( i=0; i<=7; i++){
                    if( buttons[winningCombos[i][0]].getText().equals(buttons[winningCombos[i][1]].getText()) && 
                        buttons[winningCombos[i][1]].getText().equals(buttons[winningCombos[i][2]].getText()) && 
                        buttons[winningCombos[i][0]].getText() != " ") {
                    	matchwon = true;
                    	if (letter == "X") pl1wins++;
                    	else pl2wins++;
                    }

    		}
            	counter++;
            	//Bottom panel update. It will now be showing whose turn it currently is
            	//or the new number of wins(if a player has won the game).
            	winsStatus.setText("[Player1 (X):"+pl1wins+"]  ^^  [Player2 (O):"+pl2wins+"]");
            	if (counter%2==0)whoseTurn.setText("TURN: X");
            	else whoseTurn.setText("TURN: O");
    	}
    	
    	}		
    		if (source==menuInstr){
    			
    			JOptionPane.showConfirmDialog(null, text, "Instructions", JOptionPane.PLAIN_MESSAGE);
    		}
    		if (source==newGame) {
    			
    			counter=0;
    			matchwon = false;
    			for (int i=0; i<=8; i++){
    		    	buttons[i].setText(" ");     //sets all buttons to 'empty'
    		    	buttons[i].setEnabled(true); //all buttons can now be pushed, allowing a new game to begin
    		     	   
    			}   			
    		}
    		
    		if(source == exit)	{
    			int option = JOptionPane.showConfirmDialog(null, "You are about to exit the game.\nAre you sure you want to exit?", "Exit warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    			if(option == JOptionPane.YES_OPTION)
    				System.exit(0);
    		}
    	
    	
    	    //Messages shown when the game is won and when the game is a tie.
    		//If none of the above happens, no message appears on the screen.
        	if (matchwon)JOptionPane.showMessageDialog(null, letter + " has won the game!");
        	if (!matchwon && counter ==9 )JOptionPane.showMessageDialog(null, "Tie game!");
        	
        	
        	 
        	
        	
    	}
    
        
    }
}
    
    
    

