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
	double amount;
	boolean winner, combatant;
	
	
	public WagerData(Player player, Player betOn, String arenaName, double amount, boolean combatant){
		this.player = player;
		this.betOn = betOn;
		this.arenaName = arenaName;
		this.amount = amount;
		this.combatant = combatant;
		this.winner = false;
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
	
	public void setAmount(double amount){
		this.amount = amount;
	}
	
	public Player getBetOn(){
		return betOn;
	}
	
	public String getArenaName(){
		return arenaName;
	}
	
	public double getAmount(){
		return amount;
	}
	
	public void setWinningWager(){
		this.winner = true;
	}
}
