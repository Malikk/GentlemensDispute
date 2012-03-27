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
		plugin.tempData.addWaitReady(players);
		plugin.arrayMessage(players, "When you are ready, type /ready");
		runRingOutSche(players, plugin.tempData.getArena(players));
	}
	
	public void allPlayersReady(Player[] players){
		GDScheduler sche = new GDScheduler(plugin);
		sche.countdown(players);
	}
	
	public void runRingOutSche(Player[] players, String arena){
		GDScheduler sche = new GDScheduler(plugin);
		sche.ringOutTimer(players, arena);
	}
	
	public void beginMatchType(Player[] players){
		String mode = plugin.tempData.getMode(players);
		
		if (mode.equalsIgnoreCase("duel")){
			plugin.duel.beginDuel(players);
		}else{
			return;
		}
	}
	
	public void saveAndClearInventories(Player[] players){
		for (Player player: players){
			plugin.tempData.saveInventory(player);
			player.getInventory().clear();
		}
	}
	
	public void clearInventories(Player[] players){
		for (Player player: players){
			player.getInventory().clear();
		}
	}
}
