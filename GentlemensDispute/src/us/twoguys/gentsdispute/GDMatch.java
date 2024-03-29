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
	public HashSet<Player> waitingAfterMatch = new HashSet<Player>();
	
	//Open for wagers
	public HashSet<List<Player>> openWager = new HashSet<List<Player>>();
	
	//Command Volley
	public HashSet<Player> playersTurn = new HashSet<Player>();
	
	//Damage Negation Handlers
	public HashSet<Player> damageProtection = new HashSet<Player>();
	public HashSet<Player> onlyMatchDamage = new HashSet<Player>();
	
	//ConfinedToArena
	public HashMap<Player, String> confinedToArena = new HashMap<Player, String>();
	
	//Died in a Match
	public HashMap<Player, Location> diedInMatch = new HashMap<Player, Location>();
	public HashSet<Player> runningDrawCheckers = new HashSet<Player>();
	
	//Locations
	public HashMap<Player, Location> returnLocation = new HashMap<Player, Location>();
	
	//Inventories
	public HashMap<Player, ItemStack[]> inventoryContents = new HashMap<Player, ItemStack[]>();
	public HashMap<Player, ItemStack[]> armorContents = new HashMap<Player, ItemStack[]>();
	
	public GDMatch(GentlemensDispute instance){
		plugin = instance;
	}
	
	//MatchData Methods
	public void addMatchData(String mode, String arena, Player[] players){
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
		return arena;
	}
	
	public boolean arenaIsInUse(String arena){
		for (List<Player> list: matchData.keySet()){
			String hashArena = getArena((Player[]) list.toArray());
			if (arena.equalsIgnoreCase(hashArena)){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasMatchData(Player player){
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
	
	public Player getOtherPlayer(Player player){
		for (List<Player> list: matchData.keySet()){
			for (Player arrayPlayer: list){
				if (!(arrayPlayer.equals(player))){
					return arrayPlayer;
				}
			}
		}
		return null;
	}
	
	public void removeMatchData(Player[] players){
		matchData.remove(Arrays.asList(players));
		plugin.wager.removeCombatWager(players);
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
			for (Player challenger: waitingOnAccept.get(playerKey)){
				if (player == challenger){
					return playerKey;
				}
			}
		}
		return null;
	}
	
	public Player getChallenged(Player player){
		for (Player playerKey: waitingOnAccept.keySet()){
			for (Player arrayPlayer: waitingOnAccept.get(playerKey)){
				if (arrayPlayer != getChallenger(player)){
					return arrayPlayer;
				}
			}
		}
		return null;
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
	
	public boolean isWaitingOnReady(Player player){
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
	
	//Open for Wagers methods
	public void openMatchForWagers(Player[] players){
		openWager.add(Arrays.asList(players));
	}
	
	public boolean isOpenForWagers(Player[] players){
		if (openWager.contains(Arrays.asList(players))){
			return true;
		}else{
			return false;
		}
	}
	
	public void closeMatchForWagers(Player[] players){
		openWager.remove(Arrays.asList(players));
	}
	
	//Command Volley
	public void switchTurn(Player player){
		playersTurn.add(plugin.match.getOtherPlayer(player));
		playersTurn.remove(player);
	}
	
	public boolean isInTurn(Player player){
		if (playersTurn.contains(player)){
			return true;
		}else{
			return false;}
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
	
	public void removeDamageProtection(Player player){
		damageProtection.remove(player);
	}
	
	//Waiting After Match
	public void addWaitingAfterMatch(Player[] players){
		for (Player player: players){
			waitingAfterMatch.add(player);
		}
	}
	
	public boolean isWaitingAfterMatch(Player player){
		if (waitingAfterMatch.contains(player)){
			return true;
		}else{
			return false;
		}
	}
	
	public void removeWaitingAfterMatch(Player player){
		waitingAfterMatch.remove(player);
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
		for (Player player: players){
			confinedToArena.put(player, arena);
		}
	}
	
	public boolean isConfined(Player player, String arena){
		if (confinedToArena.containsKey(player)){
			return true;
		}else{
			return false;
		}
	}
	
	public void release(Player player){
		confinedToArena.remove(player);
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
	
	//Running Draw Checkers
	public void addDrawChecker(Player player){
		runningDrawCheckers.add(player);
	}
	
	public boolean alreadyChecking(Player player){
		Player[] players = plugin.match.getOtherPlayers(player);
		
		for (Player arrayPlayer: players){
			if (runningDrawCheckers.contains(arrayPlayer)){
				return true;
			}
		}
		return false;
	}
	
	public void removeDrawChecker(Player[] players){
		for (Player player: players){
			runningDrawCheckers.remove(player);
		}
	}
	
	//Locations
	public void addPlayerReturnLocation(Player player, Location loc){
		returnLocation.put(player, loc);
	}
	
	public Location getPlayerReturnLocation(Player player){
		Location loc = returnLocation.get(player);
		return loc;
	}
	
	public boolean hasReturnLocation(Player player){
		if (returnLocation.containsKey(player)){
			return true;
		}else{
			return false;
		}
	}
	
	public void removePlayerReturnLocation(Player player){
		returnLocation.remove(player);
	}
	
	//Inventories
	public void saveInventory(Player player){
		inventoryContents.put(player, player.getInventory().getContents());
		armorContents.put(player, player.getInventory().getArmorContents());
	}
	
	public void loadInventory(Player player){
		player.getInventory().setContents(inventoryContents.get(player));
		player.getInventory().setArmorContents(armorContents.get(player));
	}
	
	public void removeOldInventory(Player player){
		inventoryContents.remove(player);
		armorContents.remove(player);
	}
	
	public void revertAll(){
		//return locations
		for (Player tpPlayer: returnLocation.keySet()){
			tpPlayer.teleport(returnLocation.get(tpPlayer));
		}
		
		//inventories and armors
		for (Player invPlayer: inventoryContents.keySet()){
			loadInventory(invPlayer);
		}
	}
}
