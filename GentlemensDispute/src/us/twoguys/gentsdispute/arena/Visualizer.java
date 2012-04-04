package us.twoguys.gentsdispute.arena;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import us.twoguys.gentsdispute.GentlemensDispute;


public class Visualizer {

	GentlemensDispute plugin;
	
	HashMap<Location, Material> storedBlocks = new HashMap<Location, Material>();
	HashMap<String, HashSet<Location>> arenaLocations = new HashMap<String, HashSet<Location>>();
	
	public Visualizer(GentlemensDispute instance){
		plugin = instance;
	}
	
	public void saveBlock(Block block){
		storedBlocks.put(block.getLocation(), block.getType());
	}
	
	public void visualizeBlock(Block block, Material material){
		saveBlock(block);

		Block newBlock = block;
		newBlock.setType(material);
	}
	
	public void visualizeLocation(Location loc, Material material){
		visualizeBlock(Bukkit.getWorld(loc.getWorld().getName()).getBlockAt(loc), material);
	}
	
	public void visualizeSpawn(Block block){
		saveBlock(block.getRelative(0,1,0));
		saveBlock(block.getRelative(0,2,0));
		
		Block foot = block.getRelative(0,1,0);
		Block head = block.getRelative(0,2,0);
		
		foot.setType(getDefaultBlockMaterial());
		head.setType(Material.PUMPKIN);
	}
	

	public void visualizeCuboidBasic(Location loc1, Location loc2){
		RegionBasic rb = new RegionBasic(loc1, loc2);
		
		int x = Math.abs(rb.getLargeX()-rb.getSmallX());
		//int y = rb.getLargeY()-rb.getSmallY();
		int z = Math.abs(rb.getLargeZ()-rb.getSmallZ());
		
		visualizeBlock(rb.getSmallLoc().getBlock(), getDefaultBlockMaterial());
		visualizeBlock(rb.getSmallLoc().add(0,0,z).getBlock(), getDefaultBlockMaterial());
		visualizeBlock(rb.getSmallLoc().add(x,0,0).getBlock(), getDefaultBlockMaterial());
		visualizeBlock(rb.getSmallLoc().add(0,0,-z).getBlock(), getDefaultBlockMaterial());
		
		visualizeBlock(rb.getLargeLoc().getBlock(), getDefaultBlockMaterial());
		visualizeBlock(rb.getLargeLoc().add(0,0,-z).getBlock(), getDefaultBlockMaterial());
		visualizeBlock(rb.getLargeLoc().add(-x,0,0).getBlock(), getDefaultBlockMaterial());
		visualizeBlock(rb.getLargeLoc().add(0,0,z).getBlock(), getDefaultBlockMaterial());
		
		
	}
	
	public void visualizeArena(String arenaName){
		ArenaData arena = plugin.arenaMaster.getArenaData(arenaName);
		Location loc1 = arena.getCorner1();
		Location loc2 = arena.getCorner2();
		
		HashSet<Location> arenaLocs;
		
			arenaLocs = arenaLocations.get(arenaName);
		try{
			arenaLocs.add(loc1);
		}catch(Exception e){
			arenaLocs = new HashSet<Location>();
			arenaLocs.add(loc1);
		}
		arenaLocs.add(loc2);
		
		arenaLocations.put(arenaName, arenaLocs);
		
		visualizeCuboidBasic(loc1, loc2);
	}
	
	/*public void revertArena(String arenaName){
		HashSet<Location> arenaLocs = arenaLocations.get(arenaName);
		for(Location loc : arenaLocs){
			revertLocation(loc);
		}
		arenaLocations.remove(arenaName);
	
	}
	 */
	public void revertLocation(Location loc){
		try{
			Material original = getStoredBlocks().get(loc);
			loc.getBlock().setType(original);
			
		}catch(Exception e){
			plugin.logSevere("Block was not reverted");
			return;
		}
		removeStoredItem(loc);
		
		//plugin.log("Block successfully reverted to: "+getStoredBlocks().get(loc).toString());

	}
	
	public void removeStoredItem(Location loc){
		HashMap<Location, Material> tempStorage = getStoredBlocks();
		tempStorage.remove(tempStorage.get(loc));
		storedBlocks = tempStorage;
	}
	public HashMap<Location, Material> getStoredBlocks(){
		return storedBlocks;
	}
	
	public void revertAll(){
		for(Location loc : getStoredBlocks().keySet()){
			revertLocation(loc);
		}
	}
	
	public Material getDefaultBlockMaterial(){
		Material material = Material.GLOWSTONE;	
		return material;
	}
	
	private int getDefaultIncrementValue(){
		int value = 2;
		return value;
	}
}
