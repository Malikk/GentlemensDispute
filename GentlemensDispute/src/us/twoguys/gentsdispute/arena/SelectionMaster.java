package us.twoguys.gentsdispute.arena;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GentlemensDispute;
/**
 * 
 * @author Nick
 * @Description Handles everything to do with setting and getting selected points, and assigning them to hashmaps.
 */
public class SelectionMaster{



	GentlemensDispute plugin;
	
	HashMap<Player, String> selectors = new HashMap<Player, String>();
	
	/**
	 * [0]=corner1 [1]=corner2 [2]=spawn [3]=spectator spawn
	 */
	HashMap<Player, Location[]> selectedLocations = new HashMap<Player, Location[]>();
	
	
	public SelectionMaster(GentlemensDispute instance) {
		plugin = instance;
	}
	
	public void addSelector(Player player){
		if(selectors.containsKey(player)){
			plugin.visualizerPlus.revertPlayerBlocks(player);
		}
		selectors.put(player, "corner1");
		//plugin.log("selector added");
	}
	
	public void removeSelector(Player player){
		selectors.remove(player);
	}
	
	public HashMap<Player, String> getSelectors(){
		return selectors;
	}
	
	public boolean isSelecting(Player player){
		if(selectors.containsKey(player)){
			return true;
		}else{
			return false;
		}
	}
	
	public String getSelectorString(Player player){
		if(isSelecting(player)){
			return selectors.get(player);
		}else{
			plugin.sendWarningMessage(player, "You must select points first.");
			return null;
		}
	}
	
	public void setSelector(Player player, String string){
		selectors.put(player, string);
	}
	
	public void addCorner1(Player player, Location loc){
		Location[] locs = new Location[4];
		selectedLocations.put(player, locs);
		
			locs[0] = loc.getBlock().getLocation();
			selectedLocations.put(player, locs);
			setSelector(player, "corner2");
			plugin.sendMessage(player, ChatColor.GREEN+"First point selected");
	        plugin.sendMessage(player, ChatColor.DARK_GREEN+"select the second corner");
			
	        plugin.visualizerPlus.delayedVisualize(player, loc.getBlock(), plugin.visualizerPlus.getDefaultBlockMaterial());
	}
	
	public void addCorner2(Player player, Location loc){
		if(isSelectable(player, loc)){	
			Location[] locs = selectedLocations.get(player);
			locs[1] = loc.getBlock().getLocation();
			selectedLocations.put(player, locs);
		    setSelector(player, "spawn");
		    plugin.sendMessage(player, ChatColor.GREEN+"Second point selected.");
		    plugin.sendMessage(player, ChatColor.DARK_GREEN+"select the spawn");
		    
		    plugin.visualizerPlus.delayedVisualize(player, loc.getBlock(), plugin.visualizerPlus.getDefaultBlockMaterial());
			plugin.visualizerPlus.visualizeCuboidBasic(player, loc, selectedLocations.get(player)[0]);
		   
		}else{
			plugin.sendWarningMessage(player, "You have already selected this point!");
		}
	}
	
	public void addSpawn(Player player, Location loc){
		if(isSelectable(player, loc)){
			Location[] locs = selectedLocations.get(player);
			locs[2] = loc.getBlock().getLocation();
			
			if(this.plugin.arenaMaster.cuboidContainsLocation(locs[0], locs[1], loc)){
				selectedLocations.put(player, locs);
				plugin.visualizerPlus.visualizeSpawn(player, Bukkit.getWorld(loc.getWorld().getName()).getBlockAt(loc));
				plugin.sendMessage(player, ChatColor.GREEN+"The spawn was set successfully.");
				
				setSelector(player, "spectatorSpawn");
				plugin.sendMessage(player, ChatColor.DARK_GREEN+"select the spectator spawn");
			}else{
				plugin.sendWarningMessage(player, "The spawn point must be inside the arena");
			}
		}else{
			plugin.sendWarningMessage(player, "You have already selected this point!");
		}
	}
	
	public void addSpectatorSpawn(Player player, Location loc){
		if(isSelectable(player, loc)){
			Location[] locs = selectedLocations.get(player);
			locs[3] = loc.getBlock().getLocation();
			setSelector(player, "complete");

            plugin.sendMessage(player, ChatColor.GREEN + "The spectator spawn location has been set");
        	plugin.sendMessage(player, ChatColor.GREEN + "Finished selecting arena!");
        	
			plugin.visualizerPlus.visualizeSpawn(player, Bukkit.getWorld(loc.getWorld().getName()).getBlockAt(loc).getRelative(0,1,0));
		}else{
			plugin.sendWarningMessage(player, "You have already selected this point!");
		}
	}
	
	public Location[] getSelectedLocations(Player player){
		
			return selectedLocations.get(player);		
	}
	
	public Location getSelectedCorner1(Player player){
		return selectedLocations.get(player)[0];
	}
	
	public Location getSelectedCorner2(Player player){
		return selectedLocations.get(player)[1];
	}
	
	public Location getSelectedSpawn(Player player){
		return selectedLocations.get(player)[2];
	}
	
	public Location getSelectedSpectatorSpawn(Player player){
		return selectedLocations.get(player)[3];
	}
	
	public void createArenaWithSelectedPoints(Player player, String arenaName){
		Location corner1 = getSelectedCorner1(player);
		Location corner2 = getSelectedCorner2(player);
		Location spawn = getSelectedSpawn(player);
		Location spectatorSpawn = getSelectedSpectatorSpawn(player);
		
		if(this.plugin.arenaMaster.createArena(corner1, corner2, spawn, spectatorSpawn, arenaName)){
			plugin.sendMessage(player, ChatColor.GREEN +"Arena created successfully");
		}else{
			if(this.plugin.arenaMaster.collidesWithArena(corner1, corner2)){
				plugin.sendWarningMessage(player, "Arena collides with another arena.");
			}else if(this.plugin.arenaMaster.nameIsTaken(arenaName)){
				plugin.sendWarningMessage(player, "This arena name is already taken");
			}
		}
	}
	
	private boolean isSelectable(Player player, Location loc){
		Location[] locs = getSelectedLocations(player);
			for(Location sLoc : locs){
				if(sLoc == null){
					break;
				}
				Location argLoc = loc.getBlock().getLocation();
				
				if(sLoc.getX()== argLoc.getX()){
					if( sLoc.getY() == argLoc.getY()){
						if(sLoc.getZ() == argLoc.getZ()){
							return false;
						}
					}
				}
			}
			return true;
	}
}
