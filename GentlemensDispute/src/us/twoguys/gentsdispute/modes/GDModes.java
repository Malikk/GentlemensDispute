package us.twoguys.gentsdispute.modes;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.*;

public class GDModes {

	GentlemensDispute plugin;
	
	public GDModes(GentlemensDispute instance){
		plugin = instance;
	}
	
	public void tpToArena(Player[] players, String arena){
		Location tpLoc = plugin.arenaMaster.getArenaData(arena).getSpawnLocation();
		
		for (Player player: players){
			plugin.tempData.addPlayerReturnLocation(player, player.getLocation());
			player.teleport(tpLoc);
		}
	}
	
	public void tpBack(Player player){
		Location tpLoc = plugin.tempData.getPlayerReturnLocation(player);
		player.teleport(tpLoc);
		plugin.tempData.removePlayerReturnLocation(player);
	}
	
	public void waitForPlayerReady(Player[] players){
		//add players to readyWaiting hashMap
		//send messages asking the players if they are ready
		//ringOutSche
	}
	
	public void allPlayersReady(Player[] players){
		GDScheduler sche = new GDScheduler(plugin);
		sche.countdown(players);
	}
	
	public void runRingOutSche(Player p1, Player p2, String arena){
		//checks as long as the players names are in confinePlayers HashMap
	}
	
	public void beginMatchType(Player[] players){
		String mode = plugin.tempData.getMode(players);
		
		if (mode.equalsIgnoreCase("duel")){
			plugin.duel.beginDuel(players);
		}else{
			return;
		}
	}
	
	public void saveAndClearInventories(){
		
	}
	
	public void clearInventories(){
		
	}
}
