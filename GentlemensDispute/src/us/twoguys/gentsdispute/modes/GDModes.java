package us.twoguys.gentsdispute.modes;

import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.*;

public class GDModes {

	GentlemensDispute plugin;
	
	public GDModes(GentlemensDispute instance){
		plugin = instance;
	}
	
	public void tpToArena(Player[] players, String arena){
		//need methods for getting arena data from arena name as string
		//Save locations in hashMap
		//tp players to arena object p1 and p2 locations
	}
	
	public void tpBack(Player player){
		//from hashMap with old locations
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
			plugin.duel.playersAreReady();
		}else{
			return;
		}
	}
	
}
