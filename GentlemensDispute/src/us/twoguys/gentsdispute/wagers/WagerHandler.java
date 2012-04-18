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
	
	@SuppressWarnings("static-access")
	public void placeWager(Player player, Player betOn, double wager, boolean combat){
		if (plugin.vault.hasMoney(player, wager)){
			plugin.vault.removeMoney(player, wager);
			
			String arenaName = plugin.match.getArena(plugin.match.getOtherPlayers(betOn));
			WagerData data = new WagerData(player, betOn, arenaName, wager, combat);
			wagerData.add(data);
			
		}else{
			player.sendMessage("You do not have enough " + plugin.vault.economy.currencyNamePlural());
		}
		
		
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
