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

	public void revertBlock(Location loc){
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
			revertBlock(loc);
		}
	}
	
	public Material getDefaultBlockMaterial(){
		Material material = Material.GLOWSTONE;	
		return material;
	}
}
