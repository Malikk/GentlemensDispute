package us.twoguys.gentsdispute.arena;

import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import us.twoguys.gentsdispute.GentlemensDispute;


public class Visualizer {

	GentlemensDispute plugin;
	
	HashMap<Location, Material> storedBlocks = new HashMap<Location, Material>();
	
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
		plugin.log("The block that was acutally saved is: " + getStoredBlocks().get(block.getLocation()).toString());
	}
	
	public void revertBlock(Location loc){
		try{
			Material original = getStoredBlocks().get(loc);
			loc.getBlock().setType(original);
			
		}catch(Exception e){
			plugin.logSevere("Block was not reverted");
			return;
		}
		removeStoredItem(loc);
		
		plugin.log("Block successfully reverted to: "+getStoredBlocks().get(loc).toString());

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
			revertBlock(loc);
		}
	}
	public void acquireVisualizerTargets(Location loc1, Location loc2){
		
	}
	public void visualizeCuboidBasic(Location loc1, Location loc2){
		
	}
	
	public Material getDefaultBlockMaterial(){
		Material material = Material.GLOWSTONE;	
		return material;
	}
}
