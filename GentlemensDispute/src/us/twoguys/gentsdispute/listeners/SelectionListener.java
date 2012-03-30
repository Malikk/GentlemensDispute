package us.twoguys.gentsdispute.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import us.twoguys.gentsdispute.GentlemensDispute;
import us.twoguys.gentsdispute.arena.SelectionMaster;
import us.twoguys.gentsdispute.arena.Visualizer;

public class SelectionListener implements Listener{

	GentlemensDispute plugin;
	SelectionMaster selectionMaster;
	Visualizer visualizer;
	
	
	public SelectionListener(GentlemensDispute instance){
		plugin = instance;
		this.selectionMaster = plugin.selectionMaster;
		visualizer = plugin.visualizer;
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
	        visualizer.visualizeBlock(event.getClickedBlock(), visualizer.getDefaultBlockMaterial());
	        player.sendMessage(ChatColor.GREEN + "First point selected");
	    }
		else if(selectionMaster.getSelectorInt(player) == 2){
		    selectionMaster.addCorner2(player, event.getClickedBlock().getLocation());
		    selectionMaster.setSelector(player, 3);
		    visualizer.visualizeBlock(event.getClickedBlock(), visualizer.getDefaultBlockMaterial());
		    player.sendMessage(ChatColor.GREEN + "Second point selected." +ChatColor.DARK_GREEN+" Select the spawn location.");
		}
		else if(selectionMaster.getSelectorInt(player) == 3){			
			Location spawn = new Location(event.getClickedBlock().getWorld(), event.getClickedBlock().getX(),
					event.getClickedBlock().getY() + 1, event.getClickedBlock().getZ());
			
			boolean spawnSet = selectionMaster.addSpawn(player, spawn);
			
			if(spawnSet==false){
				player.sendMessage(ChatColor.RED + "The spawn location must be within the arena");
			}else{
				selectionMaster.setSelector(player, 4);
				player.sendMessage(ChatColor.GREEN + "The spawn location has been set");
			}
		}
		
	}
}
