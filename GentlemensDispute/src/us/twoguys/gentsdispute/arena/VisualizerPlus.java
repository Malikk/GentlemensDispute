package us.twoguys.gentsdispute.arena;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GentlemensDispute;


public class VisualizerPlus {

	GentlemensDispute plugin;
	
	HashMap<Location, Material> storedBlocks = new HashMap<Location, Material>();
	HashMap<String, HashSet<Location>> arenaLocations = new HashMap<String, HashSet<Location>>();
	HashSet<BlockData> blocks = new HashSet<BlockData>();
		
	
	public VisualizerPlus(GentlemensDispute instance){
		plugin = instance;
	}
	
	/*public void saveBlock(Block block){
		if(!storedBlocks.containsKey(block.getLocation())){
			storedBlocks.put(block.getLocation(), block.getType());
		}else{
			return;
		}
	}*/
	
	public void saveBlock(Player player, Block block){
		BlockData block1 = new BlockData(player, block);
		blocks.add(block1);
	}
	
	public void visualizeBlock(Player player, Block block, Material material){
		
		saveBlock(player, block);

		player.sendBlockChange(block.getLocation(), material, block.getData());
	}
	
	public void visualizeLocation(Player player, Location loc, Material material){
		visualizeBlock(player, Bukkit.getWorld(loc.getWorld().getName()).getBlockAt(loc), material);
	}
	
	public void visualizeSpawn(Player player, Block block){
		visualizeBlock(player, block, Material.GLOWSTONE);
		visualizeBlock(player, block.getRelative(0,1,0), Material.PUMPKIN);
	}
	

	public void visualizeCuboidBasic(Player player, Location loc1, Location loc2){
		RegionBasic rb = new RegionBasic(loc1, loc2);
		
		int x = Math.abs(rb.getLargeX()-rb.getSmallX());
		int z = Math.abs(rb.getLargeZ()-rb.getSmallZ());
		
		//layer1
		Block l1b1 = rb.getSmallLoc().getBlock();
		Block l1b2 = rb.getSmallLoc().getBlock().getRelative(0,0,z);
		Block l1b3 = rb.getSmallLoc().getBlock().getRelative(x,0,0);
		Block l1b4 = rb.getSmallLoc().getBlock().getRelative(x,0,z);
		
		visualizeBlock(player, l1b1, getDefaultBlockMaterial());
		visualizeBlock(player, l1b2, getDefaultBlockMaterial());
		visualizeBlock(player, l1b3, getDefaultBlockMaterial());
		visualizeBlock(player, l1b4, getDefaultBlockMaterial());
		
		//layer 2
		Block l2b1 = rb.getLargeLoc().getBlock();
		Block l2b2 = rb.getLargeLoc().getBlock().getRelative(0,0,-z);
		Block l2b3 = rb.getLargeLoc().getBlock().getRelative(-x,0,0);
		Block l2b4 = rb.getLargeLoc().getBlock().getRelative(-x,0,-z);
		
		visualizeBlock(player, l2b1, getDefaultBlockMaterial());
		visualizeBlock(player, l2b2, getDefaultBlockMaterial());
		visualizeBlock(player, l2b3, getDefaultBlockMaterial());
		visualizeBlock(player, l2b4, getDefaultBlockMaterial());
		

	}
	
	public void visualizeArena(Player player, String arenaName){
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
		
		visualizeCuboidBasic(player, loc1, loc2);
	}
	
	public HashSet<BlockData> getBlocks(){
		return blocks;
	}

	public HashSet<Block> getBlocks(Player player){
		HashSet<Block> pBlocks = new HashSet<Block>();
		
		for(BlockData block:getBlocks()){
			if(block.getPlayer()==player){
				pBlocks.add(block.getBlock());
			}
		}
		return pBlocks;
	}
	
	public HashSet<BlockData> getPlayerBlockData(Player player){
		HashSet<BlockData> pBlocks = new HashSet<BlockData>();
		
		for(BlockData blockData:getBlocks()){
			if(blockData.getPlayer().getName()==player.getName()){
				pBlocks.add(blockData);
			}
		}
		return pBlocks;
	}

	public void revertBlock(Player player, Block block){
		player.sendBlockChange(block.getLocation(), block.getType(), block.getData());
	}
	
	public void revertPlayerBlocks(Player player){
		for(Block block:getBlocks(player)){
			revertBlock(player, block);
		}
	}

	public void removeStoredBlock(Player player, Block block){
		HashSet<BlockData> tempBlocks = getBlocks();
		for(BlockData blockData : getPlayerBlockData(player)){
			if(blockData.getBlock()==block){
				tempBlocks.remove(blockData);
			}
		}
		this.blocks=tempBlocks;
		plugin.log("Location removed");
	}
	
	public void revertAll(){
	
		
	}
	
	public Material getDefaultBlockMaterial(){
		Material material = Material.GLOWSTONE;	
		return material;
	}
	
	@SuppressWarnings("unused")
	private int getDefaultIncrementValue(){
		int value = 2;
		return value;
	}
}
