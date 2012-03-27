package us.twoguys.gentsdispute.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import us.twoguys.gentsdispute.GentlemensDispute;

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
		else if(selectionMaster.getSelectorInt(player) == 1){
			selectionMaster.addCorner1(player, event.getClickedBlock().getLocation());
	        selectionMaster.setSelector(player, 2);
	        player.sendMessage(ChatColor.GREEN + "First point selected");
	    }
		else if(selectionMaster.getSelectorInt(player) == 2){
		    selectionMaster.addCorner2(player, event.getClickedBlock().getLocation());
		    selectionMaster.setSelector(player, 3);
		    player.sendMessage(ChatColor.GREEN + "Second point selected.\n Select the spawn location.");
		}
		else if(selectionMaster.getSelectorInt(player) == 3){
			selectionMaster.addSpawn(player, event.getClickedBlock().getLocation());
			selectionMaster.setSelector(player, 4);
			player.sendMessage(ChatColor.GREEN + "The spawn location has been set");
		}
		
	}
}
