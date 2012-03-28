package us.twoguys.gentsdispute.modes;

import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GentlemensDispute;

public class GDDuel extends GDModes{
	
	public GDDuel(GentlemensDispute instance){
		super(instance);
	}
	
	//Primary Methods
	
	public void prepareDuel(Player[] players){
		tpToArena(players, plugin.match.getArena(players));
		preparePlayers(players);
		waitForPlayerReady(players);
	}
	
	public void beginDuel(Player[] players){
		plugin.match.removeDamageProtection(players);
		plugin.match.addOnlyMatchDamage(players);
	}
	
	public void endDuel(Player[] players){
		plugin.match.removeMatchData(players);
		plugin.match.removeOnlyMatchDamage(players);
		
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
}
