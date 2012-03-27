package us.twoguys.gentsdispute.modes;

import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GentlemensDispute;

public class GDDuel extends GDModes{
	
	public GDDuel(GentlemensDispute instance){
		super(instance);
	}
	
	//Primary Methods
	
	public void prepareDuel(Player[] players){
		tpToArena(players, plugin.tempData.getArena(players));
		preparePlayers(players);
		waitForPlayerReady(players);
	}
	
	public void beginDuel(Player[] players){
		plugin.tempData.removeDamageProtection(players);
		plugin.tempData.addOnlyMatchDamage(players);
	}
	
	public void endDuel(Player[] players){
		plugin.tempData.removeMatchData(players);
		plugin.tempData.removeOnlyMatchDamage(players);
		
	}
	
	//Secondary Methods
	
	public void preparePlayers(Player[] players){
		saveAndClearInventories(players);
		addGivenWeaponsAndItems(players);
	}
	
	public void addGivenWeaponsAndItems(Player[] players){
		for (Player player: players){
			player.getInventory().setContents(plugin.config.getGivenDuelItems());
		}
	}
	
	public void addArmor(Player[] players){
		for (Player player: players){
			player.getInventory().setArmorContents(plugin.config.getGivenDuelArmor());
		}
	}
}
