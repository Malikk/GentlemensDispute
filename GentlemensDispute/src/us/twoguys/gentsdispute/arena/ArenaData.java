package us.twoguys.gentsdispute.arena;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class ArenaData implements Serializable{

	
	private static final long serialVersionUID = 5102457006382574656L;

	//selection locations
	private int x1, y1, z1;
	private int x2, y2, z2;
	
	//spawn location
	private int sx, sy, sz; //duelers
	private int specx, specy, specz; //spectators
	
	private String worldName;
	private boolean isDefault;
	private String arenaName;
	 
	public ArenaData(Location corner1, Location corner2, Location spawn, Location spectatorSpawn, String arenaName){
		x1 = corner1.getBlockX();
		y1 = corner1.getBlockY();
		z1 = corner1.getBlockZ();
		
		x2 = corner2.getBlockX();
		y2 = corner2.getBlockY();
		z2 = corner2.getBlockZ();
		
		sx = spawn.getBlockX();
		sy = spawn.getBlockY();
		sz = spawn.getBlockZ();
		
		specx = spectatorSpawn.getBlockX();
		specy = spectatorSpawn.getBlockY();
		specz = spectatorSpawn.getBlockZ();
		
		this.arenaName = arenaName;
		
		worldName = corner1.getWorld().getName();
	}

	public String getName(){
		return arenaName;
	}
	
	public Location getCorner1(){
		return stringToLocation(worldName, x1, y1, z1);
	}
	
	public Location getCorner2(){
		return stringToLocation(worldName, x2, y2, z2);
	}
	
	public String getWorldName(){
		return worldName;
	}
	
	public World getWorld(){
		return Bukkit.getWorld(worldName);
	}
	
	public Location getSpawnLocation(){
		return stringToLocation(worldName, sx, sy, sz);
	}
	
	public Location getSpectatorSpawn(){
		return stringToLocation(getWorldName(), specx, specy, specz);
	}
	
	public boolean isDefault(){
		return isDefault;
	}
	
	public void setSpawn(Location loc){
		sx= loc.getBlockX();
		sy= loc.getBlockY();
		sz= loc.getBlockZ();
	}
	
	public void setSpectatorSpawn(Location loc){
		specx = loc.getBlockX();
		specy = loc.getBlockY();
		specz = loc.getBlockZ();
	}
	
	public void setDefault(boolean bool){
		isDefault = bool;
	}

	private Location stringToLocation(String worldName, int x, int y, int z){
		return new Location(Bukkit.getWorld(worldName), x, y, z);		
	}
	
}
