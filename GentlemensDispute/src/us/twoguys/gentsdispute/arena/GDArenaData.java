package us.twoguys.gentsdispute.arena;

import java.io.Serializable;

import org.bukkit.Location;

public class GDArenaData implements Serializable{

	
	private static final long serialVersionUID = 5102457006382574656L;

	int aX1, aY1, aZ1;
	int aX2, aY2, aZ2;
	
	int p1X, p1Y, p1Z;
	int p2X, p2Y, p2Z;
	
	String arenaName;
	
	public GDArenaData(Location loc1, Location loc2, String name){
		
	}

}
