import java.util.Collections;
import java.util.LinkedList;


import javax.swing.JOptionPane;
public class Play {
/*
 * This class should contain
 * Method/Constructor getting the players name
 * Method for the bets tied to them
 */
	
	//variables
	private LinkedList <Integer> playerBets = new LinkedList <Integer>(); //stored in linklist to allow more or less than 6 die rolls
	private String playerName;
	
	//constructor
	public Play() {
	}
	
	//toString
	public String toString() {
		String output = "";
		
		for(int tempInt : this.playerBets) {
			output = output + tempInt + " ";
		}
		
		output = this.playerName + " you rolled " + output;
		
		return output;
	} //end toString
	
	//set player name
	public void playerName() {
		this.playerName = JOptionPane.showInputDialog("Please enter your name");
	
	}
	
	//method for input die rolls into 
	public void addTheRolls(int input) {
		
		//used in LuckySixes class to input die rolls
		this.playerBets.add(input);
		
		//sorts the rolls in numerical order
		Collections.sort(this.playerBets);
	}
  	
	//printDetails
	public void printDetails() {
		String output = "";
		
		output = toString();
		
		JOptionPane.showMessageDialog(null, output, "The bets", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	//getters and setters
	public void setPlayerName(String name) {
		this.playerName = name;
	}
	
	public String getPlayersName() {
		return this.playerName;
	}
	
	public LinkedList <Integer> getPlayerBet(){
		return this.playerBets;
	}

	
} //end Play class
