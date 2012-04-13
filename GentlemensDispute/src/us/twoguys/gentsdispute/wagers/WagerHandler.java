package us.twoguys.gentsdispute.wagers;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.*;

public class WagerHandler {
	
	GentlemensDispute plugin;
	public HashSet<WagerData> wagerData = new HashSet<WagerData>();
	public HashMap<Player, Double> combatWager = new HashMap<Player, Double>();
	
	public WagerHandler(GentlemensDispute instance){
		plugin = instance;
	}
	
	public void placeWager(Player player, Player betOn, double bet, boolean combat){
		String arenaName = plugin.match.getArena(plugin.match.getOtherPlayers(betOn));
		WagerData data = new WagerData(player, betOn, arenaName, bet, combat);
		wagerData.add(data);
	}
	
	public boolean setCombatWager(Player player, double wager){
		if (!(combatWager.containsKey(player))){
			combatWager.put(player, wager);
			return true;
		}else{
			if (wager > combatWager.get(player)){
				combatWager.remove(player);
				combatWager.put(player, wager);
				return true;
			}else{
				return false;
			}
		}
	}
	
	//get all matches open for bettings and who is in the match
	
	//get match and bets on each player
	
	//poot
	
	//calculate payouts
	
	//calculate combatant payouts
	
	//remove wager data
}
