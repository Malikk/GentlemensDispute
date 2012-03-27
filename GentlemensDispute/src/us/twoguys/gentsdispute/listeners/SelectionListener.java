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
	
	public SelectionListener(GentlemensDispute instance){
		plugin = instance;
		SelectionMaster selectionMaster = plugin.selectionMaster;
	}
	
	@EventHandler
	public void onSelection(PlayerInteractEvent event){
		Player player = event.getPlayer();
		
		if( == 0){
			return;
		}
		if(selectionMaster){
			Location loc1 = event.getClickedBlock().getLocation();
		}else if(selectionCounter == 2){
			Location loc2 = event.getClickedBlock().getLocation();
		}
	}
}
