# LuckySixes
Student project: a simple game made in Java

The following is a report detailing how the game “Lucky Sixes” was developed. It contains class descriptions, an extensibility assessment and an overview of the 'advanced' feature included. 

Class Descriptions

This covers each class structure and the function of each class. 

RunLuckySixesRun
This class is only to call the LuckySixes class and have the main method run it. 

Play
This class would best be described as either a play or player. 

The two main parts are:

Player name: a method wherein the players name is requested from the user.

Players bets: a method wherein the players bets are stored into a LinkedList and then sorted using collections. 

Lucky Sixes
This class is the game itself. It performs all the main parts of the game outside of player details.This includes, but is not limited to, the number of die, the number of sides to the die, the number of plays, the number of rolls per player, the prize values, the winning number and more.  

In terms of structure:

The constructor ties the number of winning number rolls to prize values and, as it is the first requirement, asks whether the player wishes to enter debug mode or not.

Next, and most critically, is the StartTheGame method. This is the main play loop and it is this that must be called in the RunLuckySixesRun class. This begins by first calling the SimpleMenu method (validation included) which gets the gamePlay variable. This variable decides what exactly the player wants to do: play, collate or exit. The first two options in this loop then call other methods to perform the functions of the game, while the last just exits. 

If the player wishes to play either the die rolling method is called or the manual number entry method, this depends on if the player is in debug mode or not. This allows whatever the designated number of rolls (in this case 6) is to be played/entered. It is at this stage that the method to limit the number of players per player is called and used. After each set (6) of rolls the player is then returned to the simpleMenu. Once a condition is met (10 max plays or the player wishes to collate) then the gamePlay variable changes and collation is performed. 

During collation two key methods are run. One to calculate the number of sixes the player has gotten and the related prize value, this being displayed play by play. And another to calculate the total profits and loss for Lucky Sixes overall. Once these are run the player is returned to the simpleMenu.
Extensibility
In terms of extensibility the program has several advantages.

Easily changed variables:

Given that all the variables that concern game play are controlled from the variable declaration at the beginning of the class it is easy to extend and change them. Options include: 
•	Number of die faces
•	Number of rolls per round
•	Number of rounds
•	Which values draw which prize amount
•	Prize amounts. 
•	Number of plays per player

Collections/Lists:
	
As the players bets and the play class instances themselves are stored in lists any changes to array size would not be needed to extend/change the program. 

Use of methods:

SimpleMenu: as it is in a method of its own and its results feed into the gameplay variable it is simple to extend the options presented to the user.

StartTheGame: with the flow of the game centralised to one method any additional options included in the simpleMenu only have to be added into here. 
Advanced Features

Limit number of goes per player: 
A method to limit the number of plays per player was created.

The method is run after each Play instance creation. It adds the instance to a list, and then checks later instances against this list. If the same variable name appears 3 times it won’t be allowed and the player is asked to pick a new name. 

If this new name is acceptable (not appearing 3 times on the list) then it can be used.
