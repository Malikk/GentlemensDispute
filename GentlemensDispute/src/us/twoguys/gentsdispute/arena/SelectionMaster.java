package us.twoguys.gentsdispute.arena;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GentlemensDispute;

public class SelectionMaster{



	GentlemensDispute plugin;
	
	HashMap<Player, String> selectors = new HashMap<Player, String>();
	HashMap<Player, Location[]> selectedLocations = new HashMap<Player, Location[]>(); //[0]=corner1 [1]=corner2 [2]=spawn
	
	
	public SelectionMaster(GentlemensDispute instance) {
		plugin = instance;
	}
	
	public void addSelector(Player player){
		if(selectors.containsKey(player)){
			plugin.visualizer.revertAll();
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
			player.sendMessage(ChatColor.RED + "You must select points first.");
			return null;
		}
	}
	
	public void setSelector(Player player, String string){
		selectors.put(player, string);
	}
	
	public void addCorner1(Player player, Location loc){
		Location[] locs = new Location[10];
		locs[0] = loc;
		selectedLocations.put(player, locs);
	}
	
	public void addCorner2(Player player, Location loc){
		Location[] locs = selectedLocations.get(player);
		locs[1] = loc;
		selectedLocations.put(player, locs);
	}
	
	public boolean addSpawn(Player player, Location loc){
		Location[] locs = selectedLocations.get(player);
		locs[2] = loc.add(0,1,0);
		if(this.plugin.arenaMaster.cuboidContainsLocation(locs[0], locs[1], loc)){
			selectedLocations.put(player, locs);
			return true;
		}else{
			return false;
		}
		
	}
	
	public void addSpectatorSpawn(Player player, Location loc){
		Location[] locs = selectedLocations.get(player);
		locs[3] = loc.add(0,1,0);
		
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
			player.sendMessage(ChatColor.GREEN +"Arena created successfully");
		}else{
			if(this.plugin.arenaMaster.collidesWithArena(corner1, corner2)){
				player.sendMessage(ChatColor.RED+"Arena collides with another arena.");
			}else if(this.plugin.arenaMaster.nameIsTaken(arenaName)){
				player.sendMessage(ChatColor.RED+"This arena name is already taken");
			}
		}
	}
}
