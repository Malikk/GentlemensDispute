package us.twoguys.gentsdispute.arena;

import org.bukkit.Location;
/* In order to use the methods in this class, instantiate the constructor first.
 * Example:
 * RegionBasic rb;
 * rb = new RegionBasic(blah blah blah);
 * rb.getSize();
 */
public class RegionBasic {

	private int lX, lY, lZ;
	private int sX, sY, sZ;
	private Location lLoc;
	private Location sLoc;
	
	public RegionBasic(Location loc1, Location loc2){
		
		int b1X = loc1.getBlockX();
		int b1Y = loc1.getBlockY();
		int b1Z = loc1.getBlockZ();
		
		int b2X = loc2.getBlockX();
		int b2Y = loc2.getBlockY();
		int b2Z = loc2.getBlockZ();
		
		
		this.lX = (b1X > b2X ? b1X:b2X);
		this.lY = (b1Y > b2Y ? b1Y:b2Y);
		this.lZ = (b1Z > b2Z ? b1Z:b2Z);
		
		this.sX = (b1X < b2X ? b1X:b2X);
		this.sY = (b1Y < b2Y ? b1Y:b2Y);
		this.sZ = (b1Z < b2Z ? b1Z:b2Z);
		
		this.lLoc = new Location(loc1.getWorld(), this.lX, this.lY, this.lZ);
		this.sLoc = new Location(loc1.getWorld(), this.sX, this.sY, this.sZ);
		
	}
	
	public Location getLargeLoc(){
		return lLoc;
	}
	
	public Location getSmallLoc(){
		return sLoc;
	}
	
	public int getLargeX(){
		return lX;
	}
	
	public int getLargeY(){
		return lY;
	}
	
	public int getLargeZ(){
		return lZ;
	}
	
	public int getSmallX(){
		return sX;
	}
	
	public int getSmallY(){
		return sY;
	}
	
	public int getSmallZ(){
		return sZ;
	}
	
	public int getSizeX(){
		return this.getLargeX()-this.getSmallX();
	}
	
	public int getSizeY(){
		return this.getLargeY()-this.getSmallY();
	}
	
	public int getSizeZ(){
		return this.getLargeZ()-this.getSmallZ();
	}
	
	public int getSize(){
		return this.getSizeX() * this.getSizeY() * this.getSizeZ();
	}
	
	public boolean collides(Location R1L, Location R1S, Location R2L, Location R2S){
		int R1LX = R1L.getBlockX();
		int R1LY = R1L.getBlockY();
		int R1LZ = R1L.getBlockZ();
		
		int R1SX = R1S.getBlockX();
		int R1SY = R1S.getBlockY();
		int R1SZ = R1S.getBlockZ();
		
		int R2LX = R2L.getBlockX();
		int R2LY = R2L.getBlockY();
		int R2LZ = R2L.getBlockZ();
		
		int R2SX = R2S.getBlockX();
		int R2SY = R2S.getBlockY();
		int R2SZ = R2S.getBlockZ();
		
		if((R1LX >= R2SX && R1LX <= R2LX)||(R1SX >= R2SX && R1SX <= R2LX)||(R2LX >= R1SX && R2LX <= R1LX)||(R2SX >= R1SX && R2SX <= R1LX))
			if((R1LY >= R2SY && R1LY <= R2LY)||(R1SY >= R2SY && R1SY <= R2LY)||(R2LY >= R1SY && R2LY <= R1LY)||(R2SY >= R1SY && R2SY <= R1LY))
				if((R1LZ >= R2SZ && R1LZ <= R2LZ)||(R1SZ >= R2SZ && R1SZ <= R2LZ)||(R2LZ >= R1SZ && R2LZ <= R1LZ)||(R2SZ >= R1SZ && R2SZ <= R1LZ))
		           return true;
		
		return false;
		
	}
}
