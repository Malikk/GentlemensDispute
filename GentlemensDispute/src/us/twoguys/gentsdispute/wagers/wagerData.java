package us.twoguys.gentsdispute.wagers;

/**
 * 
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
	
	public void setBetOn(String playerName){
		betOn = playerName;
	}
	
	public void setArenaName(String arenaName){
		this.arenaName = arenaName;
	}
	
	public void setBet(double bet){
		this.bet = bet;
	}
	
	public String getBetOn(){
		return betOn;
	}
	
	
	public String getArenaName(){
		return arenaName;
	}
	
	public double getBet(){
		return bet;
	}
}
