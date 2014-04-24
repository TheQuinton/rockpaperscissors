package rockpaperscissors;
/* Group: Foxtrot
 * Group Members: Quinton Schafer, Homare Takase
 * Instructions: save files will be saved as 'playername'.txt, to load existing players place save files in the containing folder
 * 
 */
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
	//main variables
	static int numberOfGames,counter;
	static List<String> rps = new ArrayList<String>();
	@SuppressWarnings("serial")
	static List<String> firstPlayerValid = new ArrayList<String>() {{add("s");add("d");add("f");}};
	@SuppressWarnings("serial")
	static List<String> secondPlayerValid = new ArrayList<String>() {{add("j");add("k");add("l");}};
	static Hashtable<String, String> inputConnector = new Hashtable<String, String>();
	static List<Player> player = new ArrayList<Player>();
	
	public static void main(String[] args) {
		System.out.println("Welcome to the game of Rock Paper Scissors!");
		createPlayers();	//creates the players
		while (true) {	//set the number of games to play
			try {
				@SuppressWarnings("resource")
				Scanner input = new Scanner(System.in);
				System.out.println("Enter the number of games for this match, it must be odd: ");
				numberOfGames = Integer.parseInt(input.nextLine());
				if (numberOfGames%2 != 0) {	//makes sure the number of games is odd
					break;
				} else {	//only happens if input is not odd
					System.out.println("Invalid Input: Number must be odd");
				}
			}
			catch(Exception e){ //only happens if input is not an integer
				System.out.println("Invalid Input: Must be an integer and odd");
			}
		}
		//this is the main loop of the match
		while (true) {	//each time through the loop is one game
			player.get(0).setGamesPlayed();	//adds to the number of games that the player has played
			player.get(1).setGamesPlayed();
			inputConnector.clear();	//clears the hashtable to allow for new randomized inputs
			System.out.print(player.get(0).getName() + " > ");
			randomizeInputs(firstPlayerValid);	//randomizes first player's inputs
			System.out.print(player.get(1).getName() + " > ");
			randomizeInputs(secondPlayerValid);	//randomizes second player's inputs
			playRPS(getPlayersInput());	//gets the input and then figures out who won the game 
			counter++;	//keeps track of the games per match
			if (counter == numberOfGames) {	//stops the game loop when the number of games has been reached
				break;
			}
		}
		player.get(0).setMatchesPlayed();	//adds to the number of matches that the player has played
		player.get(1).setMatchesPlayed();
		endMatch();	//displays the winner of the match, as well as stats
	}
	
	public static void createPlayers(){	//creates the player objects and names them
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		for (int i = 0; i < 2; i++) {
			System.out.println("Enter the name of player "+ (i+1) + ": ");	// i+1 so that it displays 1 and 2 instead of 0 and 1
			String name = input.nextLine();
			player.add(new Player(name,i));
			player.get(i).load();	//loads player save data from file
		}
	}
	public static void randomizeInputs(List<String> valid){	//randomizes the inputs, making different buttons count as R P S
		rps.add("rock");
		rps.add("paper");
		rps.add("scissors");
		for (int i = 0; i < valid.size(); i++) {
			Random r = new Random();
			int random = r.nextInt(((rps.size()-1) - 0) + 1) + 1;	//finds random integer from 0 and the size of rps, or the number of objects it contains
			String output = String.format("%1$-" + 15 + "s", valid.get(i) + " - "+ rps.get(random-1));
			System.out.print(output);
			String x = rps.get(random-1);
			inputConnector.put(valid.get(i), x);	//holds the string s,d,f,j,k,l and connects them to the randomly set string from rps
			rps.remove(x);
		}
		System.out.println();
	}
	public static String getPlayersInput(){	//gets the input from the players for their choices also, checks for valid input
		String choices;
		System.out.println("ROCK PAPER SCISSORS! SHOOT!... tap your keys! and then press ENTER");
		while (true) {
			@SuppressWarnings("resource")
			Scanner input = new Scanner(System.in);
			choices = input.nextLine().toLowerCase();	//gets the input from players, makes it lower case
			if (choices.length() == 2) {
				break;
			} else{
				System.out.println("Invalid Input: Only one input per person allowed, so only two letters");
				System.out.println("Try Again > ");
				continue;
			}
		}
		return choices;
	}
	public static void playRPS(String choices){	//compares the inputs of the players
		String first = choices.substring(0,1);	//first character of input
		String second = choices.substring(1);	//second character of input
		String firstPlayerInput,secondPlayerInput;
		if (firstPlayerValid.contains(first)) {	//checks if first character is in first player's choice of keys
			firstPlayerInput = inputConnector.get(first);	// if first character is in first player's choice of keys, sets first player's input as first character
			if (secondPlayerValid.contains(second)) {	//checks if second character is in second player's choice of keys
				secondPlayerInput = inputConnector.get(second);	//if second character is in second player's choice of keys, sets second player's input as second character
			} else {
				secondPlayerInput = "forfeit";	
			}
		} else if (secondPlayerValid.contains(first)) {	//checks if first character is in second player's choice of keys
			secondPlayerInput = inputConnector.get(first);	//if first character is in second player's choice of keys, sets second player's input as first character
			if (firstPlayerValid.contains(second)) {	//checks if second character is in first player's choice of keys
				firstPlayerInput = inputConnector.get(second);	//if second character is in first player's choice of keys, sets first player's input as second character
			} else {
				firstPlayerInput = "forfeit";
			}
		} else {	// invalid first character input
			if (firstPlayerValid.contains(second)) {	//checks if second character is in first player's choice of keys, while first character is invalid
				firstPlayerInput = inputConnector.get(second);	//if second character is in first player's choice of keys, sets first player's input as the second character
				secondPlayerInput = "forfeit";	// and thus second player's input is invalid
			} else if (secondPlayerValid.contains(second)) {	//checks if second character is in second player's choice of keys, while first character is invalid
				secondPlayerInput = inputConnector.get(second);	//if second character is in second player's choice of keys, sets second player's input as the second character
				firstPlayerInput = "forfeit";	//and thus first player's input is invalid
			} else {
				firstPlayerInput = "forfeit";
				secondPlayerInput = "forfeit";
			}
		}
		//now we compare the inputs to find out who won the game
		if (firstPlayerInput.equals(secondPlayerInput)) {	//tie game scenario
			System.out.println("TIE GAME");
			System.out.println("You both picked: "+ firstPlayerInput);
			if (firstPlayerInput.equals("forfeit")) {	//saves forfeits if both forfeited 
				player.get(0).setForfeits();
				player.get(1).setForfeits();
			}
			return;	//exits method if tie game
		}
		if (firstPlayerInput.equals("forfeit")) {	//player 1 forfeits
			System.out.println(player.get(1).getName() + " won this game! "+ player.get(0).getName() + " forfeited the game!");
			player.get(1).setGamesWon();
			player.get(1).setWinsThisMatch();
			player.get(0).setForfeits();
			return;	//exits method if player 1 forfeited
		} else if (secondPlayerInput.equals("forfeit")) {	//player 2 forfeits
			System.out.println(player.get(0).getName() + " won this game! "+ player.get(1).getName() + " forfeited the game!");
			player.get(0).setGamesWon();
			player.get(0).setWinsThisMatch();
			player.get(1).setForfeits();
			return;	//exits method if player 2 forfeited
		} 
		if (firstPlayerInput.equals("rock")) {	//if first player picked rock
			if (secondPlayerInput.equals("paper")) {
				System.out.println(player.get(1).getName() + " WINS! PAPER COVERS ROCK!");
				player.get(1).setGamesWon();
				player.get(1).setWinsThisMatch();
				return;
			} else if (secondPlayerInput.equals("scissors")) {
				System.out.println(player.get(0).getName() + " WINS! ROCK CRUSHES SCISSORS!");
				player.get(0).setGamesWon();
				player.get(0).setWinsThisMatch();
				return;
			}
		} else if (firstPlayerInput.equals("paper")) {	//if first player picked paper
			if (secondPlayerInput.equals("scissors")) {
				System.out.println(player.get(1).getName() + " WINS! SCISSORS CUTS PAPER!");
				player.get(1).setGamesWon();
				player.get(1).setWinsThisMatch();
				return;
			} else if (secondPlayerInput.equals("rock")) {
				System.out.println(player.get(0).getName() + " WINS! PAPER COVERS ROCK!");
				player.get(0).setGamesWon();
				player.get(0).setWinsThisMatch();
				return;
			}
		} else if (firstPlayerInput.equals("scissors")) {	//if first player picked scissors
			if (secondPlayerInput.equals("paper")) {
				System.out.println(player.get(0).getName() + " WINS! SCISSORS CUTS PAPER!");
				player.get(0).setGamesWon();
				player.get(0).setWinsThisMatch();
				return;
			} else if (secondPlayerInput.equals("rock")) {
				System.out.println(player.get(1).getName() + " WINS! ROCK CRUSHES SCISSORS!");
				player.get(1).setGamesWon();
				player.get(1).setWinsThisMatch();
				return;
			}
		}
	}
	public static void endMatch(){	//displays the results of the match, and adds to player's matches won
		boolean tie = false;	//if true -> tie game
		boolean victory = false;	// if false -> player 2 won the match
		if (player.get(0).getWinsThisMatch() == player.get(1).getWinsThisMatch()) {
			tie = true;
		} else if (player.get(0).getWinsThisMatch() > player.get(1).getWinsThisMatch()) {
			victory = true;
		}
		if (tie == false) {
			if (victory) {
				System.out.println(player.get(0).getName() + " WINS THE MATCH!");
				player.get(0).setMatchesWon();
			} else if (victory == false) {
				System.out.println(player.get(1).getName() + " WINS THE MATCH!");
				player.get(1).setMatchesWon();
			}
		}
		for (int i = 0; i < 2; i++) {	//display the stats of the players
			System.out.println(player.get(i).getName() + " won: " + player.get(i).getWinsThisMatch() + " games this match");
			player.get(i).save();	//calls the save command to save the player data to invdividual files
			System.out.println("Stats for " + player.get(i).getName() + ": " + player.get(i).getMatchesWon() + " matches won, "
					+ player.get(i).getGamesWon() + " games won, " + player.get(1).getMatchesPlayed() + " matches played, " 
					+ player.get(i).getGamesPlayed() + " games played, " + player.get(i).getForfeits() + " forfeits");
		}
	}
}
