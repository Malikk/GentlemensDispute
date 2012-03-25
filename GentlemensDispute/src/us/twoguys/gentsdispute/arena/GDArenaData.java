package us.twoguys.gentsdispute.arena;

import java.io.Serializable;

import org.bukkit.Location;

public class GDArenaData implements Serializable{

	
	private static final long serialVersionUID = 5102457006382574656L;

	int x1, y1, z1;
	int x2, y2, z2;
	
	String arenaName;
	
	public GDArenaData(Location loc1, Location loc2, String name){
		
	}

}
