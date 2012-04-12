package us.twoguys.gentsdispute.wagers;

import java.util.HashSet;

import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.*;

public class WagerHandler {
	
	GentlemensDispute plugin;
	public HashSet<WagerData> wagerData = new HashSet<WagerData>();
	
	public WagerHandler(GentlemensDispute instance){
		plugin = instance;
	}
	
	public void placeBet(Player player, Player betOn, double bet, boolean combat){
		String arenaName = plugin.match.getArena(plugin.match.getOtherPlayers(betOn));
		WagerData data = new WagerData(player, betOn, arenaName, bet, combat);
		wagerData.add(data);
	}
	
	//get all matches open for bettings and who is in the match
	
	//get match and bets on each player
	
	//calculate payouts
	
	//calculate combatant payouts
	
	//remove wager data
}
