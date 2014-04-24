package rockpaperscissors;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Player {
	String name;
	int number, gamesWon, gamesPlayed, matchesWon, matchesPlayed, forfeits, winsThisMatch;
	Player(String playerName, int playerNumber){
		name = playerName;
		number = playerNumber;
		gamesPlayed = 0;
		gamesWon = 0;
		matchesWon = 0;
		matchesPlayed = 0;
		forfeits = 0;
	}
	public int getNumber(){
		return number;
	}
	public String getName(){
		return name;
	}
	public  int getGamesWon(){
		return gamesWon;
	}
	public int getGamesPlayed(){
		return gamesPlayed;
	}
	public int getMatchesWon(){
		return matchesWon;
	}
	public int getMatchesPlayed(){
		return matchesPlayed;
	}
	public int getForfeits(){
		return forfeits;
	}
	public int getWinsThisMatch(){
		return winsThisMatch;
	}
	public void setGamesWon(){
		gamesWon++;
	}
	public void setGamesPlayed(){
		gamesPlayed++;
	}
	public void setMatchesWon(){
		matchesWon++;
	}
	public void setMatchesPlayed(){
		matchesPlayed++;
	}
	public void setForfeits(){
		forfeits++;
	}
	public void setWinsThisMatch(){
		winsThisMatch++;
	}
	public void save(){	//should save to a file named after the player
		try {
			FileOutputStream saveFile = new FileOutputStream(name + ".txt");
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			
			save.writeObject(gamesWon);
			save.writeObject(gamesPlayed);
			save.writeObject(matchesWon);
			save.writeObject(matchesPlayed);
			save.writeObject(forfeits);
			
			save.close();
		} catch (Exception e) {
			e.printStackTrace(); //tells what went wrong
		}
	}
	public void load(){
		try {	//look for and read existing player file
			FileInputStream saveFile = new FileInputStream(name + ".txt");
			ObjectInputStream save = new ObjectInputStream(saveFile);
			
			gamesWon = (Integer) save.readObject();
			gamesPlayed = (Integer) save.readObject();
			matchesWon = (Integer) save.readObject();
			matchesPlayed = (Integer) save.readObject();
			forfeits = (Integer) save.readObject();
			
			save.close();
		} catch (Exception e) {	//nothing happens if the player doesn't have an existing file
			
		}
	}
}
