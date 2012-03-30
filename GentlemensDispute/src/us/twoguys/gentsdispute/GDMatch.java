package us.twoguys.gentsdispute;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GDMatch {

	GentlemensDispute plugin;
	
	//Match Data
	public HashMap<List<Player>, String[]> matchData = new HashMap<List<Player>, String[]>();
	
	//Waiting Lists
	public HashMap<Player, Player[]> waitingOnAccept = new HashMap<Player, Player[]>();
	public HashSet<Player> waitingOnReady = new HashSet<Player>();
	
	//Damage Negation Handlers
	public HashSet<Player> damageProtection = new HashSet<Player>();
	public HashSet<Player> onlyMatchDamage = new HashSet<Player>();
	
	//ConfinedToArena
	public HashMap<String, Player[]> confinedToArena = new HashMap<String, Player[]>();
	
	//Died in a Match
	public HashMap<Player, Location> diedInMatch = new HashMap<Player, Location>();
	
	//Locations
	public HashMap<Player, Location> returnLocation = new HashMap<Player, Location>();
	
	//Inventories
	public HashMap<Player, ItemStack[]> inventoryContents = new HashMap<Player, ItemStack[]>();
	
	public GDMatch(GentlemensDispute instance){
		plugin = instance;
	}
	
	//MatchData Methods
	public void addMatchData(String mode, String arena, Player[] players){
		Player p1 = players[0];
		Player p2 = players[1];
		plugin.log("Adding match data: " + mode + " at " + arena + " for " + p1.getName() + " and " + p2.getName());
		String[] matchTypeAndArena = {mode, arena};
		matchData.put(Arrays.asList(players), matchTypeAndArena);
	}
	
	public String getMode(Player[] players){
		String[] data = matchData.get(Arrays.asList(players));
		String mode = data[0].toString();
		return mode;
	}
	
	public String getArena(Player[] players){
		String[] data = matchData.get(Arrays.asList(players));
		String arena = data[1].toString();
		plugin.log("Arena is: " + arena);
		return arena;
	}
	
	public boolean isInBattle(Player player){
		for (List<Player> list: matchData.keySet()){
			for (Player arrayPlayer: list){
				if (arrayPlayer.equals(player)){
					return true;
				}
			}
		}
		return false;
	}
	
	public Player[] getOtherPlayers(Player player){
		for (List<Player> list: matchData.keySet()){
			for (Player arrayPlayer: list){
				if (arrayPlayer.equals(player)){
					Player[] players = (Player[]) list.toArray();
					return players;
				}
			}
		}
		return null;
	}
	
	public boolean isOtherPlayer(Player player, Player otherPlayer){
		for (Player arrayPlayer: getOtherPlayers(player)){
			if (arrayPlayer.equals(otherPlayer)){
				return true;
			}
		}
		return false;
	}
	
	public void removeMatchData(Player[] players){
		matchData.remove(players);
	}
	
	//WaitingOnAccept Methods
	public void addWaitAccept(Player p1, Player[] players){
		waitingOnAccept.put(p1, players);
	}
	
	public boolean waitingOnAcceptContains(Player player){
		for (Player playerKey: waitingOnAccept.keySet()){
			if (playerKey == player){
				return true;
			}
			
			for (Player challenged: waitingOnAccept.get(playerKey)){
				if (player == challenged){
					return true;
				}
			}
		}
		return false;
	}
	
	public Player getChallenger(Player player){
		for (Player playerKey: waitingOnAccept.keySet()){
			for (Player challenged: waitingOnAccept.get(playerKey)){
				if (player == challenged){
					return playerKey;
				}
			}
		}
		return player;
	}
	
	public void removeWaitAccept(Player p1){
		waitingOnAccept.remove(p1);
	}
	
	//WaitingOnReady Methods
	public void addWaitReady(Player[] players){
		for (Player player: players){
			waitingOnReady.add(player);
		}
	}
	
	public boolean waitingOnReadyContains(Player player){
		for (Player p: waitingOnReady){
			if (player == p){
				return true;
			}
		}
		return false;
	}
	
	public void removeWaitReady(Player player){
		waitingOnReady.remove(player);
	}
	
	//DamageProtection Methods
	public boolean hasDamageProtection(Player player){
		if (damageProtection.contains(player)){
			return true;
		}else{
			return false;
		}
	}
	
	public void addDamageProtection(Player[] players){
		for (Player player: players){
			damageProtection.add(player);
		}
	}
	
	public void removeDamageProtection(Player[] players){
		for (Player player: players){
			damageProtection.remove(player);
		}
	}
	
	//OnlyMatchDamage Methods	
	public void addOnlyMatchDamage(Player player){
		onlyMatchDamage.add(player);
	}
	
	public boolean hasOnlyMatchDamage(Player player){
		if (damageProtection.contains(player)){
			return true;
		}else{
			return false;
		}
	}
	
	public void removeOnlyMatchDamage(Player player){
		onlyMatchDamage.remove(player);
	}
	
	public void addOnlyMatchDamage(Player[] players){
		for (Player player: players){
			onlyMatchDamage.add(player);
		}
	}
	
	public void removeOnlyMatchDamage(Player[] players){
		for (Player player: players){
			onlyMatchDamage.remove(player);
		}
	}
	
	//ConfinePlayersToArena
	public void confine(Player[] players, String arena){
		confinedToArena.put(arena, players);
	}
	
	public boolean isConfined(Player player, String arena){
		Player[] players = confinedToArena.get(arena);
		for (Player arrayPlayer: players){
			if (arrayPlayer.equals(player)){
				return true;
			}
		}
		return false;
	}
	
	public void release(Player[] players, String arena){
		confinedToArena.remove(arena);
	}
	
	//Died in a Match
	public void addDiedInMatch(Player player){
		Player[] players = plugin.match.getOtherPlayers(player);
		String arena = plugin.match.getArena(players);
		Location loc = plugin.arenaMaster.getArenaData(arena).getSpawnLocation();
		diedInMatch.put(player, loc);
	}
	
	public boolean diedInMatch(Player player){
		if (diedInMatch.containsKey(player)){
			return true;
		}else{
			return false;
		}
	}
	
	public Location getDiedInMatchLocation(Player player){
		Location loc = diedInMatch.get(player);
		return loc;
	}
	
	public void removeDiedInMatch(Player player){
		diedInMatch.remove(player);
	}
	
	//Locations
	public void addPlayerReturnLocation(Player player, Location loc){
		returnLocation.put(player, loc);
	}
	
	public Location getPlayerReturnLocation(Player player){
		Location loc = returnLocation.get(player);
		return loc;
	}
	
	public void removePlayerReturnLocation(Player player){
		returnLocation.remove(player);
	}
	
	//Inventories
	public void saveInventory(Player player){
		inventoryContents.put(player, player.getInventory().getContents());
	}
	
	public void loadInventory(Player player){
		player.getInventory().setContents(inventoryContents.get(player));
	}
	
	public void removeOldInventory(Player player){
		inventoryContents.remove(player);
	}
}
