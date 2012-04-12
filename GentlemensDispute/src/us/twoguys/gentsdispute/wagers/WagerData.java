package us.twoguys.gentsdispute.wagers;

import org.bukkit.entity.Player;

/**
 * 
 *This data class contains two constructors. One is a set-all style of instantiation, the other only requires
 *the player name. 
 */
public class WagerData {

	
	Player player, betOn;
	String arenaName;
	double bet;
	boolean combatant;
	
	
	public WagerData(Player player, Player betOn, String arenaName, double bet, boolean combatant){
		this.player = player;
		this.betOn = betOn;
		this.arenaName = arenaName;
		this.bet = bet;
		this.combatant = combatant;
	}
	
	public WagerData(Player player){
		this.player = player;
	}
	
	public void setBetOn(Player betOn){
		this.betOn = betOn;
	}
	
	public void setArenaName(String arenaName){
		this.arenaName = arenaName;
	}
	
	public void setBet(double bet){
		this.bet = bet;
	}
	
	public Player getBetOn(){
		return betOn;
	}
	
	
	public String getArenaName(){
		return arenaName;
	}
	
	public double getBet(){
		return bet;
	}
}
