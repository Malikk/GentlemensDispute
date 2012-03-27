package us.twoguys.gentsdispute;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GDTemporaryDataStorage {

	GentlemensDispute plugin;
	
	//Match Data
	public HashMap<Player[], String[]> matchData = new HashMap<Player[], String[]>();
	
	//Waiting Lists
	public HashMap<Player, Player[]> waitingOnAccept = new HashMap<Player, Player[]>();
	public HashSet<Player> waitingOnReady = new HashSet<Player>();
	
	//Damage Negation Handlers
	public HashSet<Player> damageProtection = new HashSet<Player>();
	public HashSet<Player> onlyMatchDamage = new HashSet<Player>();
	
	//ConfinedToArena
	public HashMap<String, Player[]> confinedToArena = new HashMap<String, Player[]>();
	
	//Locations
	public HashMap<Player, Location>  returnLocation = new HashMap<Player, Location>();
	
	public GDTemporaryDataStorage(GentlemensDispute instance){
		plugin = instance;
	}
	
	//MatchData Methods
	public void addMatchData(String mode, String arena, Player[] players){
		String[] matchTypeAndArena = {mode, arena};
		matchData.put(players, matchTypeAndArena);
	}
	
	public String getMode(Player[] players){
		String[] data = matchData.get(players);
		return data[0];
	}
	
	public String getArena(Player[] players){
		String[] data = matchData.get(players);
		return data[1];
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
	public void addWaitReady(Player player){
		waitingOnReady.add(player);
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
	public void addDamageProtection(Player player){
		damageProtection.add(player);
	}
	
	public boolean hasDamageProtection(Player player){
		if (damageProtection.contains(player)){
			return true;
		}else{
			return false;
		}
	}
	
	public void removeDamageProtection(Player player){
		damageProtection.remove(player);
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
	
}
