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
	private int sx, sy, sz;
	
	private String worldName;
	
	private String arenaName;
	
	public ArenaData(Location corner1, Location corner2, Location spawn, String arenaName){
		x1 = corner1.getBlockX();
		y1 = corner1.getBlockY();
		z1 = corner1.getBlockZ();
		
		x2 = corner2.getBlockX();
		y2 = corner2.getBlockY();
		z2 = corner2.getBlockZ();
		
		sx = spawn.getBlockX();
		sy = spawn.getBlockY();
		sz = spawn.getBlockZ();
		
		
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
	
	public void setSpawn(Location loc){
		sx= loc.getBlockX();
		sy= loc.getBlockY();
		sz= loc.getBlockZ();
	}

	private Location stringToLocation(String worldName, int x, int y, int z){
		return new Location(Bukkit.getWorld(worldName), x, y, z);		
	}
	
}
