package us.twoguys.gentsdispute.arena;

import java.util.HashSet;

import org.bukkit.Location;
import us.twoguys.gentsdispute.GentlemensDispute;

/*
 * THis should be used as a primary means to create and modify arenas.
 */
public class ArenaMaster {

	GentlemensDispute plugin;
	
	private HashSet<ArenaData> arenaDataList = new HashSet<ArenaData>();
	private HashSet<ArenaData> deletedArenaList = new HashSet<ArenaData>();
	
	public ArenaMaster(GentlemensDispute instance) {
		plugin = instance;


	}
	
	public boolean createArena(Location corner1, Location corner2, Location spawn, String arenaName){
		
		if(!nameIsTaken(arenaName) && !collidesWithArena(corner1, corner2)){
			ArenaData arena = new ArenaData(corner1, corner2, spawn, arenaName);
			arenaDataList.add(arena);
			plugin.log("Created " + arenaName);
			return true;
		}else{
			plugin.logSevere("Could not create an arena!");
			return false;
		}
	}
	
	/**
	 * 
	 * @param corner1 - the first corner of a cuboid
	 * @param corner2 - the second corner of a cuboid
	 * @return True if the new cuboid collides with an arena. False if it does not collide with an arena
	 */
	public boolean collidesWithArena(Location corner1, Location corner2){
		RegionBasic regionBasic = new RegionBasic(corner1, corner2);
		Location R1L = regionBasic.getLargeLoc();
		Location R1S = regionBasic.getSmallLoc();
		
		for(ArenaData arenaData : getArenaDataList()){
			RegionBasic arena = new RegionBasic(arenaData.getCorner1(), arenaData.getCorner2());
			Location R2L = arena.getLargeLoc();
			Location R2S = arena.getSmallLoc();
			
			if(regionBasic.collides(R1L, R1S, R2L, R2S)){
				return true;
			}else{
				return false;
			}
		}
		return false;
	
	}
	
	public boolean nameIsTaken(String arenaName){
		for(ArenaData arena: getArenaDataList()){
			if(arena.getName().equalsIgnoreCase(arenaName)){
				return true;
			}
		}
		return false;	
	}
	
	public ArenaData getArenaData(String arenaName){
		ArenaData arenaData = null;
		for(ArenaData arena: arenaDataList){
			if(arena.getName() == arenaName){
				arenaData = arena;
			}else{
				plugin.logSevere(arenaName + " does not exist!");
			}
		}
		return arenaData;
	}

	/**
	 * 
	 * @return HashSet containing all the ArenaData objects that are loaded.
	 */
	public HashSet<ArenaData> getArenaDataList(){
		return this.arenaDataList;
	}
	
	/**
	 * 
	 * @return An string with a list of all the current arenas in the format "arena1, arena2, arena3..."
	 */
	public String getArenaNamesString(){
		StringBuilder string = new StringBuilder();
		for(ArenaData arena: getArenaDataList()){
			string.append(arena.getName() + ", ");
		}
		return string.toString();
	}

	private void deleteArena(ArenaData arenaData){
		deletedArenaList.add(arenaData);
	}
	
	public void deleteArena(String arenaName){
		for(ArenaData arenaData: getArenaDataList()){
			if(arenaData.getName().equalsIgnoreCase(arenaName)){
				deleteArena(arenaData);
				plugin.log(arenaData.getName()+" has been deleted!");
			}
		}
	}
	
	public boolean isDeleted(String arenaName){
		for(ArenaData arenaData : deletedArenaList){
			if(getArenaData(arenaName).getName().equalsIgnoreCase(arenaData.getName())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param loc - the location to check
	 * @param arenaName - the arena in which to check the location
	 * @return True if the location is in the arena. False if the location is not in the arena.
	 */
	public boolean arenaContainsLocation(Location loc, String arenaName){
		if(loc.getWorld().getName().equalsIgnoreCase(getArenaData(arenaName).getWorldName())){return false;}
		
		Location corner1 = getArenaData(arenaName).getCorner1();
		Location corner2 = getArenaData(arenaName).getCorner2();
		
		if(cuboidContainsLocation(corner1, corner2, loc)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 
	 * @param corner1 - the first corner location of a cuboid
	 * @param corner2 - the second corner location of the same cuboid
	 * @param loc - the location that is desired to be checked
	 * @return true if the location is within the cuboid region.
	 * false if the location is not within the cuboid region
	 */
	public boolean cuboidContainsLocation(Location corner1, Location corner2, Location loc){
		
		RegionBasic rb = new RegionBasic(corner1, corner2);
		
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		
		int xl = rb.getLargeX();
		int yl = rb.getLargeY();
		int zl = rb.getLargeZ();
		
		int xs = rb.getSmallX();
		int ys = rb.getSmallY();
		int zs = rb.getSmallZ();
		
		if(xl > x && x > xs ){
			if(yl > y && y > ys){
				if(zl > z && z > zs){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isInAnyArena(Location loc){
		for(ArenaData arena : getArenaDataList()){
			if(arenaContainsLocation(loc, arena.getName())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * 
	 * @return ArenaData that the point is inside. Returns null if the location is not in an arena
	 */
	public ArenaData getContainingArena(Location loc){
		for(ArenaData arena: getArenaDataList()){
			if(arenaContainsLocation(loc, arena.getName())){
				return arena;
			}
		}
		plugin.logSevere("getContainingArena returned null!");
		return null;
	}
}
