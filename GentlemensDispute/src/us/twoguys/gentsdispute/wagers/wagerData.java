package us.twoguys.gentsdispute.wagers;

/**
 * 
 *@description
 *This data class contains two constructors. One is a set-all style of instantiation, the other only requires
 *the player name. 
 */
public class wagerData {

	
	String playerName, betOn, arenaName;
	double bet;
	
	
	public wagerData(String playerName, String betOn, String arenaName, double bet){
		this.playerName = playerName;
		this.betOn = betOn;
		this.arenaName = arenaName;
		this.bet = bet;
	}
	
	public wagerData(String playerName){
		this.playerName = playerName;
	}
	
}
