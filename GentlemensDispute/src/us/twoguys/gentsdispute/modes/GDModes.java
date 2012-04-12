package us.twoguys.gentsdispute.modes;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.*;

public class GDModes {

	GentlemensDispute plugin;
	
	public GDModes(GentlemensDispute instance){
		plugin = instance;
	}
	
	public void drawMatchType(Player[] players){
		String mode = plugin.match.getMode(players);
		
		if (mode.equalsIgnoreCase("duel")){
			plugin.duel.endDuel(players);
			plugin.duel.declareDraw(players);
		}else{
			return;
		}
	}
	
	public void winnerMatchType(Player[] players, Player winner){
		String mode = plugin.match.getMode(players);
		
		if (mode.equalsIgnoreCase("duel")){
			plugin.duel.endDuel(players);
			plugin.duel.declareWinner(players, winner);
		}else{
			return;
		}
	}
	
	public void tpToArena(Player[] players, String arena){
		Location tpLoc = plugin.arenaMaster.getArenaData(arena).getSpawnLocation();
		
		for (Player player: players){
			plugin.match.addPlayerReturnLocation(player, player.getLocation());
			player.teleport(tpLoc);
		}
	}
	
	public void tpBack(Player player){
		Location tpLoc = plugin.match.getPlayerReturnLocation(player);
		player.teleport(tpLoc);
		restoreInventory(player);
		plugin.match.release(player);
		plugin.match.removeDamageProtection(player);
		plugin.match.removePlayerReturnLocation(player);
		plugin.match.removeWaitingAfterMatch(player);
	}
	
	public void waitForPlayerReady(Player[] players){
		plugin.match.addWaitReady(players);
		plugin.arrayMessage(players, "When you are ready, type /ready");
		runRingOutSche(players, plugin.match.getArena(players));
		GDScheduler sche = new GDScheduler(plugin);
		sche.waitingOnReady(players);
	}
	
	public void allPlayersReady(Player[] players){
		GDScheduler sche = new GDScheduler(plugin);
		sche.countdown(players);
	}
	
	public void runRingOutSche(Player[] players, String arena){
		plugin.match.confine(players, arena);
		GDScheduler sche = new GDScheduler(plugin);
		sche.ringOutTimer(players, arena);
	}
	
	public void prepareMatchType(Player[] players){
		String mode = plugin.match.getMode(players);
		
		plugin.match.openMatchForWagers(players);
		
		if (mode.equalsIgnoreCase("duel")){
			plugin.duel.prepareDuel(players);
		}else{
			return;
		}
	}
	
	public void beginMatchType(Player[] players){
		String mode = plugin.match.getMode(players);
		
		plugin.match.closeMatchForWagers(players);
		
		if (mode.equalsIgnoreCase("duel")){
			plugin.duel.beginDuel(players);
		}else{
			return;
		}
	}
	
	public void endMatchType(Player[] players){
		String mode = plugin.match.getMode(players);
		
		if (mode.equalsIgnoreCase("duel")){
			plugin.duel.endDuel(players);
		}else{
			return;
		}
	}
	
	public void saveAndClearInventories(Player[] players){
		for (Player player: players){
			plugin.match.saveInventory(player);
			player.getInventory().clear();
		}
	}
	
	public void clearInventories(Player[] players){
		for (Player player: players){
			player.getInventory().clear();
		}
	}
	
	public void restoreInventory(Player player){
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		plugin.match.loadInventory(player);
		plugin.match.removeOldInventory(player);
	}
}
