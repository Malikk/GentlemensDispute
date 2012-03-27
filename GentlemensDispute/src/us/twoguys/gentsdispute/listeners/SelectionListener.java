package us.twoguys.gentsdispute.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import us.twoguys.gentsdispute.GentlemensDispute;

public class SelectionListener implements Listener{

	GentlemensDispute plugin;
	SelectionMaster selectionMaster;
	
	Location loc1, loc2;
	
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
			loc1 = event.getClickedBlock().getLocation();
	        selectionMaster.setSelector(player, 2);
	        player.sendMessage("first point selected");
	    }
		else if(selectionMaster.getSelectorInt(player) == 2){
		    loc2 = event.getClickedBlock().getLocation();
		    selectionMaster.removeSelector(player);
		    player.sendMessage("second point selected");
		}
	}
}
