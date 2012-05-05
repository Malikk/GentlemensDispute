package us.twoguys.gentsdispute.modes;

import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GDScheduler;
import us.twoguys.gentsdispute.GentlemensDispute;

public class GDDuel extends GDModes{
	
	public GDDuel(GentlemensDispute instance){
		super(instance);
	}
	
	//Outcomes
	
	public void declareWinner(Player[] players, Player winner){
		winMessage(players, winner);
	}
	
	public void declareDraw(Player[] players){
		drawMessage(players);
	}
	
	//Primary Methods
	
	public void prepareDuel(Player[] players){
		setGamemodes(players);
		tpToArena(players, plugin.match.getArena(players));
		preparePlayers(players);
		waitForPlayerReady(players);
	}
	
	public void beginDuel(Player[] players){
		plugin.match.removeDamageProtection(players);
		plugin.match.addOnlyMatchDamage(players);
	}
	
	public void endDuel(Player[] players){
		plugin.match.removeOnlyMatchDamage(players);
		plugin.match.addDamageProtection(players);
		plugin.match.addWaitingAfterMatch(players);
		endMessage(players);
		
		GDScheduler sche = new GDScheduler(plugin);
		sche.tpBackTimer(players);
	}
	
	//Secondary Methods
	
	public void preparePlayers(Player[] players){
		plugin.match.addDamageProtection(players);
		saveAndClearInventories(players);
		addGivenWeaponsAndItems(players);
		addArmor(players);
	}
	
	public void addGivenWeaponsAndItems(Player[] players){
		for (Player player: players){
			player.getInventory().setContents(plugin.config.getGivenItems("Duel"));
		}
	}
	
	public void addArmor(Player[] players){
		for (Player player: players){
			player.getInventory().setArmorContents(plugin.config.getGivenArmor("Duel"));
		}
	}
	
	//Message Methods
	
	public void winMessage(Player[] players, Player winner){
		if (plugin.config.broadcastEnabled("MatchOutcomes")){
			plugin.broadcast(String.format("%s has won the Duel!", winner.getName()));
		}else{
			plugin.arrayMessage(players, String.format("%s has won the Duel!", winner.getName()));
		}
	}
	
	public void drawMessage(Player[] players){
		if (plugin.config.broadcastEnabled("MatchOutcomes")){
			plugin.broadcast(String.format("The match was a Draw!"));
		}else{
			plugin.arrayMessage(players, String.format("The match was a Draw!"));
		}
	}
	
	public void endMessage(Player[] players){
		if (plugin.config.broadcastEnabled("MatchBeginAndEnd")){
			plugin.broadcastExcept(players, String.format("The Duel between %s and %s has ended!", players[0].getName(), players[1].getName()));
		}
	}
}
