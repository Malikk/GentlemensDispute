package us.twoguys.gentsdispute;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GDTemporaryDataStorage {

	GentlemensDispute plugin;
	
	//Match Data
	public HashMap<Player[], String[]> matchData = new HashMap<Player[], String[]>();
	
	//Waiting Lists
	public HashMap<Player, Player[]> waitingOnAccept = new HashMap<Player, Player[]>();
	public HashMap<Player, Player[]> waitingOnReady = new HashMap<Player, Player[]>();
	
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
	
	public void removeMatchData(Player[] players){
		matchData.remove(players);
	}
	
	//WaitingOnAccept Methods
	public void addWaitAccept(Player p1, Player[] players){
		waitingOnAccept.put(p1, players);
	}
	
	public boolean checkWaitingOnAccept(Player player){
		for (Player playerKey: waitingOnAccept.keySet()){
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
	public void addWaitReady(Player p1, Player[] players){
		waitingOnReady.put(p1, players);
	}
	
	public void removeWaitReady(Player p1){
		waitingOnReady.remove(p1);
	}
	
	public void addPlayerReturnLocation(Player player, Location loc){
		returnLocation.put(player, loc);
	}
	
	public void removePlayerReturnLocation(Player player){
		returnLocation.remove(player);
	}
	
}
