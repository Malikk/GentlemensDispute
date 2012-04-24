package us.twoguys.gentsdispute.wagers;

import java.util.ArrayList;
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
	
	//WagerData
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
	
	public void setWinningWagers(String arena, Player winner){
		HashSet<WagerData> temp = wagerData;
		
		for (WagerData wager: temp){
			if (wager.arenaName.equalsIgnoreCase(arena) && wager.betOn == winner){
				wager.setWinningWager();
			}
		}
	}
	
	public void removeWagerData(String arena){
		HashSet<WagerData> temp = wagerData;
		
		for (WagerData wager: temp){
			if (wager.arenaName.equalsIgnoreCase(arena)){
				wagerData.remove(wager);
			}
		}
	}
	
	//Combat Wagers
	public boolean setCombatWager(Player player, double wager){
		if (!(combatWager.containsKey(player))){
			combatWager.put(player, wager);
			return true;
		}else{
			double previous = combatWager.get(player);
			if (wager == previous){
				return false;
			}else if (wager > previous){
				combatWager.remove(player);
				combatWager.put(player, wager);
				return true;
			}else{
				return false;
			}
		}
	}
	
	public double getCombatWager(Player player){
		double wager = 0;
		Player otherPlayer = plugin.match.getOtherPlayer(player);
		
		if (combatWager.containsKey(player)){
			wager = combatWager.get(player);
		}else if (combatWager.containsKey(otherPlayer)){
				wager = combatWager.get(otherPlayer);
		}
		return wager;
	}
	
	public void removeCombatWager(Player[] players){
		for (Player player: players){
			combatWager.remove(player);
		}
	}
	
	//Combatant Payouts
	public void calculateCombatantPayouts(String arena, Player winner){
		//do stuff
	}
	
	//Payouts
	@SuppressWarnings("static-access")
	public void calculatePayouts(String arena, Player winner){
		
		//Edit wagerData for winning wagers
		setWinningWagers(arena, winner);
		
		//Get Player[]s
		Player[] winningWagers = getWinningWagers(arena);
		Player[] losingWagers = getLosingWagers(arena);
		
		//Calculate Payouts for winning wagers
		for (Player betWin: winningWagers){
			double winnings = getWinnings(arena, betWin);
			
			plugin.vault.addMoney(betWin, winnings);
			
			betWin.sendMessage(String.format("You have won %s %s!", winnings, plugin.vault.economy.currencyNamePlural()));
		}
		
		//Send Message to losing wagers
		for (Player betLose: losingWagers){
			double loses = getLoses(arena, betLose);
			betLose.sendMessage(String.format("You have lost %s %s.", loses, plugin.vault.economy.currencyNamePlural()));
		}
		
		//Remove all WagerData for this match
		removeWagerData(arena);
	}
	
	//Payout get methods
	public Player[] getWinningWagers(String arena){
		HashSet<WagerData> temp = wagerData;
		ArrayList<Player> players = new ArrayList<Player>();
		
		for (WagerData wager: temp){
			if (wager.arenaName.equalsIgnoreCase(arena) && wager.winner == true && wager.combatant == false){
				Player wagerer = wager.player;
				
				if (!(players.contains(wagerer))){
					players.add(wagerer);
				}
			}
		}
		
		return (Player[]) players.toArray();
	}
	
	public Player[] getLosingWagers(String arena){
		HashSet<WagerData> temp = wagerData;
		ArrayList<Player> players = new ArrayList<Player>();
		
		for (WagerData wager: temp){
			if (wager.arenaName.equalsIgnoreCase(arena) && wager.winner == false && wager.combatant == false){
				Player wagerer = wager.player;
				
				if (!(players.contains(wagerer))){
					players.add(wagerer);
				}
			}
		}
		
		return (Player[]) players.toArray();
	}
	
	public double getTotalPot(String arena){
		HashSet<WagerData> temp = wagerData;
		double total = 0;
		
		for (WagerData wager: temp){
			if (wager.arenaName.equalsIgnoreCase(arena) && wager.combatant == false){
				total = total + wager.amount;
			}
		}
		return total;
	}
	
	public double getWinningWagerTotal(String arena){
		HashSet<WagerData> temp = wagerData;
		double total = 0;
		
		for (WagerData wager: temp){
			if (wager.arenaName.equalsIgnoreCase(arena) && wager.winner == true && wager.combatant == false){
				total = total + wager.amount;
			}
		}
		return total;
	}
	
	public double getPlayersWinningWagerTotal(String arena, Player player){
		HashSet<WagerData> temp = wagerData;
		double total = 0;
		
		for (WagerData wager: temp){
			if (wager.arenaName.equalsIgnoreCase(arena) && wager.player == player && wager.winner == true && wager.combatant == false){
				total = total + wager.amount;
			}
		}
		return total;
	}
	
	public double getPercentOfWinningWagers(String arena, Player player){
		double total = getWinningWagerTotal(arena);
		double wager = getPlayersWinningWagerTotal(arena, player);
		
		return wager / total * 100;
	}
	
	public double getWinnings(String arena, Player player){
		double totalPot = getTotalPot(arena);
		double percent = getPercentOfWinningWagers(arena, player);
		
		return percent / 100 * totalPot;
	}
	
	public double getLoses(String arena, Player player){
		HashSet<WagerData> temp = wagerData;
		double total = 0;
		
		for (WagerData wager: temp){
			if (wager.arenaName.equalsIgnoreCase(arena) && wager.player == player && wager.winner == false && wager.combatant == false){
				total = total + wager.amount;
			}
		}
		return total;
	}
}
