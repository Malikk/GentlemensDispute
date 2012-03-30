package us.twoguys.gentsdispute.arena;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import us.twoguys.gentsdispute.GentlemensDispute;


public class ArenaVisualizer {

	GentlemensDispute plugin;
	
	HashSet<Block> storedBlocks = new HashSet<Block>();
	
	public ArenaVisualizer(GentlemensDispute instance){
		plugin = instance;
	}
	
	public void saveBlock(Block block){
		storedBlocks.add(block);
	}
	
	public void visualizeBlock(Block block, Material material){
		saveBlock(block);
		block.setType(material);
	}
	
	public void revertBlock(Block block){
		boolean worked = false;
		for(Block b : storedBlocks){
			if(block.getLocation() == b.getLocation()){
				block.setType(b.getType());
				worked = true;
				break;
			} 
		}
		if(worked==true){
			plugin.log("Block successfully reverted");
		}
		else{
			plugin.logSevere("Block was not reverted!");
		}
	}
	public HashSet<Block> getStoredBlocks(){
		return storedBlocks;
	}
	
	public void revertAll(){
		
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
