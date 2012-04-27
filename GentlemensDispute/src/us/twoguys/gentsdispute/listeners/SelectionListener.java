package us.twoguys.gentsdispute.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import us.twoguys.gentsdispute.GentlemensDispute;
import us.twoguys.gentsdispute.arena.SelectionMaster;

public class SelectionListener implements Listener{

	GentlemensDispute plugin;
	SelectionMaster selectionMaster;
	
	
	public SelectionListener(GentlemensDispute instance){
		plugin = instance;
		this.selectionMaster = plugin.selectionMaster;
	}
	
	@EventHandler
	public void onSelection(PlayerInteractEvent event){
		Player player = event.getPlayer();
		
		if(selectionMaster.isSelecting(player)==false){
			return;
		}
		else if(selectionMaster.getSelectorString(player).equalsIgnoreCase("corner1")){
			selectionMaster.addCorner1(player, event.getClickedBlock().getLocation());
	    }
		else if(selectionMaster.getSelectorString(player).equalsIgnoreCase("corner2")){
		    selectionMaster.addCorner2(player, event.getClickedBlock().getLocation());    
		}
		else if(selectionMaster.getSelectorString(player).equalsIgnoreCase("spawn")){			
			
			Location spawn = event.getClickedBlock().getLocation().add(0, 1, 0);		
			selectionMaster.addSpawn(player, spawn);
		}
		else if(selectionMaster.getSelectorString(player).equalsIgnoreCase("spectatorSpawn")){
			selectionMaster.addSpectatorSpawn(player, event.getClickedBlock().getLocation());
			
		}
	}
	
}
