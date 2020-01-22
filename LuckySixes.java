import java.util.TreeMap;
import java.util.Random; 
import javax.swing.JOptionPane; 
import java.util.ArrayList;
import java.util.Collections;

public class LuckySixes {

	/*
	 * This class should contain:
	 * A constructor: debug mode, prize sizes 								
	 * A method in which the game starts (calls methods and coordinates the game) 							
	 * A method for a profit or loss calculator										
	 * A method for a die 													
	 * A method to sum each players bets/play								
	 * A method to function as debug mode and allow manual number entry 	
	 * A simpleMenu method													
	 * A method to limit the number of bets per player 						
	 */

	//Variables
	
	//General gameplay
	private int debugMode;
	private Random randomDice = new Random();
	final private int WINNING_NUMBER = 6; //decides what the winning roll is, can be modified here if a larger or smaller die is used
	final private int NUMBER_OF_ROLLS = 6; //decides how many times the dice is rolled
	final private int NUMBER_OF_PLAYS = 10; //decides how many of each round there is before collation
	final private int NUMBER_OF_PLAYS_PER_PLAYER = 3; //decides how many times each player can go
	private int gamePlay; //controls stage of game (play, collate, exit)
	private int winCount = 0; //counter number of times a player has won
	//private int accWinCount = 0; //not used - was a hypothetical part of advanced option
	private int totalWinnings = 0; //to accumulate all cash winnings for all players
	private int profitAndLoss = 0; //used in final display in P&L method
	private ArrayList <String> playerNameList = new ArrayList <String>(); //stores players names to check number of bets against
	private int numberOfNames = 0; //just initialising for the above array
	
	//Stores the prize values
	private TreeMap <Integer, Integer> prizeMap = new TreeMap <Integer, Integer> ();
	
	//creates arraylist to store each instance of Play into (this is what you have to use this. with) 
	private ArrayList <Play> players = new ArrayList <Play>();
	
	//sets the prizes
	final private int ONE_SIX = 1;
	final private int TWO_SIX = 40;
	final private int THREE_SIX = 250;
	final private int FOUR_SIX = 1500;
	final private int FIVE_SIX = 10000;
	final private int SIX_SIX = 50000;
	
	//set the number of faces of the die (may wish to extend to a die with more than 6 sides)
	final private int HIGHEST_DIE_NUMBER = 6;
	final private int LOWEST_DIE_NUMBER = 1;
	private int roll;
	
	//constructor
	public LuckySixes () {
		
		//debug menu
		String output;
		
		output = "Would you like to enter debug mode?";
				
		this.debugMode = JOptionPane.showConfirmDialog(null, output, "Lucky Sixes - Debug Mode", JOptionPane.YES_NO_OPTION);
		
		//setting of number of sixes to prize value
		this.prizeMap.put(0, 0);
		this.prizeMap.put(1, ONE_SIX);
		this.prizeMap.put(2, TWO_SIX);
		this.prizeMap.put(3, THREE_SIX);
		this.prizeMap.put(4, FOUR_SIX);
		this.prizeMap.put(5, FIVE_SIX);
		this.prizeMap.put(6, SIX_SIX);
		
	}//end Lucky Sixes constructor
	
	//method to start game, with menu 
	public void startTheGame() {
		
		//loop for each menu option
		while(true) {
			
			gamePlay = simpleMenu();
			
			//if player wants to play game
			if(gamePlay == 1) {
				
					//checks if debug mode or not
					if(debugMode == 0) {
						debugManualEntry();
					} else if(debugMode == 1) {
						theDie();
					}
				
				//put upper limit of x plays for everyone
				if(this.players.size() >= NUMBER_OF_PLAYS) {
					gamePlay = 2;
				}
				
			}//end gamePlay option 1 (play game)
			
			//if player wants to collate results
			if(gamePlay == 2) {
				
				//checks who's won what
				betsAndWinnings();
				
				//works out profit and losses
				profitAndLoss();
				
				//resets the array so that you're not getting non-stop running totals. (unfortunately not useful for advanced features)
				this.players.removeAll(players);
				
			} //end gamePlay option 2 (collate)
			
			//if player wants to exit
			if(gamePlay == 3) {
				System.exit(0);
			} //end gamePlay option 3 (exit)
			
		} //end while loop for main playing
		
	} //end game start method
	
	//method to calculate LuckySixes P&Ls. 
	private void profitAndLoss() {
		String output;
		
		//can use players.size because it's a pound per bet/instance (variable bet sizes would've been more complicated)
		this.profitAndLoss = (this.players.size() - this.totalWinnings); 
		
		//builds a standard output that can be easily added on to depending on P&L status
		output = "With " + this.players.size() + " bets and " + this.totalWinnings + " pounds paid out in prize money\n";
		output = output + "Lucky Sixes has made a ";
		
		//if LuckySixes broke even 
		if(this.profitAndLoss == 0) {
			output = output + "break even of " + this.profitAndLoss;
		}
		
		//if LuckySixes made a profit
		if(this.profitAndLoss > 0) {
			output = output + "profit of " + this.profitAndLoss;
		}
		
		//if LuckySixes made a loss
		if(this.profitAndLoss < 0) {
			output = output + "loss of " + (this.profitAndLoss * -1); //*-1 to remove minus sign but retain numeric value
		}
		
		//add on da dollar bills
		output = output + " pounds";
		
		//display P&Ls to user
		JOptionPane.showMessageDialog(null, output, "Totals", JOptionPane.INFORMATION_MESSAGE);
		
		//zero these in case player wants to continue playing after collation
		this.profitAndLoss = 0;
		this.totalWinnings = 0;
		
	}
	
	//method to work out how many sixes the player got and how much prize money that's worth
	private void betsAndWinnings() {
		String output ="";
		
		//for loop to iterate through players array that has Play objects (players) in it
		for(Play tempPlay : this.players) {
			
			//iterates through each players bet array and pulls the 6 die rolls
			for(int tempInt : tempPlay.getPlayerBet()) {
				
				//check if the int value is a winning number
				if(tempInt == WINNING_NUMBER) {
					winCount++; //make this specific to the player instance to get totals by player for adv feature?
					
				} //end if function that checks winning condition
				
			} //end play instance iteration
			
			//displays current instance of players name and die rolls 
			output = tempPlay.toString();
			
			//make sure and put this BEFORE you zero the winCount and NOT after. Idiot. 
			this.totalWinnings = this.totalWinnings + this.prizeMap.get(winCount);
			
			//build output & display
			output = output + "\nYou have " + winCount + " matches, and win " + this.prizeMap.get(winCount) + " pounds";
		
			JOptionPane.showMessageDialog(null, output, "Winnings", JOptionPane.INFORMATION_MESSAGE);
			
			//zero it or end up with running total for everyone 
			winCount = 0;

			
		}//end all players array list iteration
		
	} //end bets and winnings 
	
	//method for a die
	private void theDie() {
		
		//variables
		Play newPlay = new Play();
		
		//gets the player name for this round
		newPlay.playerName();
		
		//check for 3 bets per player
		checkNumberOfBets(newPlay);
		
		//loops the number of times the die needs to be thrown
		for(int counter = 0 ; counter < NUMBER_OF_ROLLS ; counter++) {
			
			//rolls dice 
			roll = randomDice.nextInt(HIGHEST_DIE_NUMBER) + LOWEST_DIE_NUMBER;
		
			//adds this roll into the player
			newPlay.addTheRolls(roll);
		} //end for loop
		
		//adds this players name and bets into the arraylist
		this.players.add(newPlay);
		
		//print results of this turn for this player
		newPlay.printDetails();
		
	} //end theDie method. 
	
	//simpleMenu
	private int simpleMenu() {
			String output;
			String userNum;
			int userNumber;
			
			//menu options
			output = "Select one of the menu options\n\n";
			output = output + "1 - Play the game\n";
			output = output + "2 - Collate the results\n";
			output = output + "3 - Exit";
			
			//ask for user input & convert
			userNum = JOptionPane.showInputDialog(output);
			userNumber = Integer.parseInt(userNum);
			
			//validation for menu entry
			while(userNumber != 1 && userNumber != 2 && userNumber != 3) {
				
				//ask for user input & convert (again)
				userNum = JOptionPane.showInputDialog(output);
				userNumber = Integer.parseInt(userNum);
			}
			//this will be used to set gamePlay variable (controls stage of play)
			return userNumber;
	}
	
	//method for debug mode manual entry
	private void debugManualEntry() {
		
		//variables (creates new instance of Play each time, important!)
		Play playerNew = new Play();
		int manualRoll;
		String userNum;
		String output1;
		String output2;
		
		//get player name
		playerNew.playerName();
		
		//check for max 3 plays per player
		checkNumberOfBets(playerNew);
		
		
		//for loop making player enter data the correct number of times 
		for(int counter = 0; counter < NUMBER_OF_ROLLS ; counter++) {
		
			//constructs output for in and out of range
			output1 = "Please enter a number between " + LOWEST_DIE_NUMBER + " and " + HIGHEST_DIE_NUMBER;
			output2 = "That number was not in range. " + output1;
		
			//First manual data entry & convert
			userNum = JOptionPane.showInputDialog(output1);
			manualRoll = Integer.parseInt(userNum);
		
			//validation loop for manual entry. 
			while(manualRoll < LOWEST_DIE_NUMBER || manualRoll > HIGHEST_DIE_NUMBER) {
				userNum = JOptionPane.showInputDialog(output2);		
			
				manualRoll = Integer.parseInt(userNum);
			}
			
			//adds rolls into player
			playerNew.addTheRolls(manualRoll);
		
		} //end for loop controlling number of rolls of die.
		
		//adds the new player into the arraylist of players
		this.players.add(playerNew);
		
		//print this rounds bets for the player to see
		playerNew.printDetails();

	} //end debug Manual Entry
	
	//method to prevent each player playing more than 3 times (takes arg in form of new Play instance that was created)
	private void checkNumberOfBets(Play name) {
		String output;
		//check how many instances of that name is in the name list in this class
		numberOfNames = Collections.frequency(playerNameList, name.getPlayersName());
		
		//if the list has 3 or more of that exact name enter this loop, stays in loop if new name has 3 or more entries too
		while(numberOfNames >= NUMBER_OF_PLAYS_PER_PLAYER) {	
			
			//output warning to user
			output = "You have already played " + NUMBER_OF_PLAYS_PER_PLAYER + " times. You cannot play again.";
		
			JOptionPane.showMessageDialog(null, output, NUMBER_OF_PLAYS_PER_PLAYER + " number of plays reached", JOptionPane.INFORMATION_MESSAGE);
			
			//call request for new name
			name.playerName();
			
			//check again
			numberOfNames = Collections.frequency(playerNameList, name.getPlayersName());
			
		}//end while loop
		
		//add the name to the list
		playerNameList.add(name.getPlayersName());
		
		//purges the list of names once it reaches the max number of plays. If removed you'll have to close the game to use the same names again
		if(playerNameList.size() >= NUMBER_OF_PLAYS) {
			playerNameList.removeAll(playerNameList);
		}
		
	} //end of method that stops player doing more than 3 bets
	
} //end LuckySixes class
