package us.twoguys.gentsdispute.arena;

import java.util.HashSet;

import org.bukkit.Location;

import us.twoguys.gentsdispute.GentlemensDispute;

/*
 * THis should be used as a primary means to create and modify arenas.
 */
public class ArenaMaster {

	GentlemensDispute plugin;
	ArenaPersister arenaPersister;
	SelectionMaster selectionMaster;
	
	private HashSet<ArenaData> arenaDataList = new HashSet<ArenaData>();
	private HashSet<ArenaData> deletedArenaList = new HashSet<ArenaData>();
	
	public ArenaMaster(GentlemensDispute instance) {
		plugin = instance;
		selectionMaster = new SelectionMaster(plugin);
		arenaPersister = new ArenaPersister(plugin);
		loadArenaData();

	}
	
	public boolean createArena(Location corner1, Location corner2, Location spawn, String arenaName){
		
		if(isValidArenaName(arenaName) && !collides(corner1, corner2)){
			ArenaData arena = new ArenaData(corner1, corner2, spawn, arenaName);
			arenaDataList.add(arena);
			return true;
		}else{
			plugin.logSevere("Could not create an arena!");
			return false;
		}
	}
	
	public boolean collides(Location loc1, Location loc2){
		RegionBasic regionBasic = new RegionBasic(loc1, loc2);
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
	
	public boolean isValidArenaName(String arenaName){
		for(ArenaData arena: getArenaDataList()){
			if(arena.getName().equalsIgnoreCase(arenaName)){
				return false;
			}else{
				return true;
			}
		}
		plugin.log(arenaName + "is already taken");
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
	
	private void loadArenaData(){
		arenaPersister.Deserialize();
	}
	
	public void saveArenaData(){
		arenaPersister.Serialize();
	}
	
	public HashSet<ArenaData> getArenaDataList(){
		return this.arenaDataList;
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
	
}
