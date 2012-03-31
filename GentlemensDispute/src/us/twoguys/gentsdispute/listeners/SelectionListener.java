package us.twoguys.gentsdispute.listeners;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
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
	        selectionMaster.setSelector(player, "corner2");
	        plugin.visualizer.visualizeBlock(event.getClickedBlock(), plugin.visualizer.getDefaultBlockMaterial());
	        player.sendMessage(ChatColor.GREEN + "First point selected");
	    }
		else if(selectionMaster.getSelectorString(player).equalsIgnoreCase("corner2")){
		    selectionMaster.addCorner2(player, event.getClickedBlock().getLocation());
		    selectionMaster.setSelector(player, "spawn");
		    plugin.visualizer.visualizeBlock(event.getClickedBlock(), plugin.visualizer.getDefaultBlockMaterial());
		    player.sendMessage(ChatColor.GREEN + "Second point selected." +ChatColor.DARK_GREEN+" Select the spawn location.");
		}
		else if(selectionMaster.getSelectorString(player).equalsIgnoreCase("spawn")){			
			Location spawn = event.getClickedBlock().getLocation().add(0, 1, 0);
			
			boolean spawnSet = selectionMaster.addSpawn(player, spawn);
			
			if(spawnSet==false){
				player.sendMessage(ChatColor.RED + "The spawn location must be within the arena");
			}else{
				selectionMaster.setSelector(player, "spectatorSpawn");
				player.sendMessage(ChatColor.GREEN + "The spawn location has been set");
				plugin.visualizer.visualizeSpawn(event.getClickedBlock());

			}
		}
		else if(selectionMaster.getSelectorString(player).equalsIgnoreCase("spectatorSpawn")){
			selectionMaster.addSpectatorSpawn(player, event.getClickedBlock().getLocation());
			player.sendMessage(ChatColor.GREEN + "The spectator spawn location has been set");
			selectionMaster.setSelector(player, "complete");
			plugin.visualizer.visualizeSpawn(event.getClickedBlock());
			
		}
	}
	
	/*@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		Set<Location> locations = plugin.visualizer.getStoredBlocks().keySet();
		if(locations.isEmpty()){
			return;
		}
		for(Location loc : locations){
			if(event.getBlock().getLocation()==loc);
			event.setCancelled(true);
			return;
		}
	}
*/}
