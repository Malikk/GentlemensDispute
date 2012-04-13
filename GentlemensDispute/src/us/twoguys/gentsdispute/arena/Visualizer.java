package us.twoguys.gentsdispute.arena;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GentlemensDispute;


public class Visualizer {

	GentlemensDispute plugin;
	
	HashMap<Location, Material> storedBlocks = new HashMap<Location, Material>();
	HashMap<String, HashSet<Location>> arenaLocations = new HashMap<String, HashSet<Location>>();
	
	HashSet<Material> illegalTypes = new HashSet<Material>();
	
	
	public Visualizer(GentlemensDispute instance){
		plugin = instance;
		illegalTypes.add(Material.BED);
		illegalTypes.add(Material.BED_BLOCK);
		illegalTypes.add(Material.BREWING_STAND);
		illegalTypes.add(Material.BURNING_FURNACE);
		illegalTypes.add(Material.CACTUS);
        illegalTypes.add(Material.CHEST);
		illegalTypes.add(Material.DISPENSER);
		illegalTypes.add(Material.FURNACE);
		illegalTypes.add(Material.IRON_DOOR_BLOCK);
		illegalTypes.add(Material.PISTON_BASE);
		illegalTypes.add(Material.PISTON_EXTENSION);
		illegalTypes.add(Material.PISTON_MOVING_PIECE);
		illegalTypes.add(Material.PISTON_STICKY_BASE);
		illegalTypes.add(Material.PORTAL);
		illegalTypes.add(Material.SIGN);
		illegalTypes.add(Material.SIGN_POST);
		illegalTypes.add(Material.TRAP_DOOR);
		illegalTypes.add(Material.STONE_BUTTON);
		illegalTypes.add(Material.WALL_SIGN);
		illegalTypes.add(Material.WOOD_DOOR);
		illegalTypes.add(Material.WOODEN_DOOR);
		illegalTypes.add(Material.VINE);
		illegalTypes.add(Material.SUGAR_CANE_BLOCK);
	}
	
	public void saveBlock(Block block){
		if(!storedBlocks.containsKey(block.getLocation())){
			storedBlocks.put(block.getLocation(), block.getType());
		}else{return;}
	}
	
	public void visualizeBlock(Block block, Material material){
		if(illegalTypes.contains(block.getType())){
			return;
		}
		
		saveBlock(block);

		Block newBlock = block;
		newBlock.setType(material);
	}
	
	public void visualizeLocation(Location loc, Material material){
		visualizeBlock(Bukkit.getWorld(loc.getWorld().getName()).getBlockAt(loc), material);
	}
	
	public void visualizeSpawn(Block block){
		saveBlock(block);
		saveBlock(block.getRelative(0,1,0));
		
		Block foot = block;
		Block head = block.getRelative(0,1,0);
		
		foot.setType(getDefaultBlockMaterial());
		head.setType(Material.PUMPKIN);
	}
	

	public void visualizeCuboidBasic(Location loc1, Location loc2){
		RegionBasic rb = new RegionBasic(loc1, loc2);
		
		int x = Math.abs(rb.getLargeX()-rb.getSmallX());
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
	
	public void revertLocation(Location loc){
		try{
			Material original = getStoredBlocks().get(loc);
			loc.getBlock().setType(original);
			
		}catch(NullPointerException np){
			return;
		}catch(Exception e){
			plugin.logSevere("Error in reversion");
		}
		removeStoredItem(loc);
		
		//plugin.log("Block successfully reverted to: "+getStoredBlocks().get(loc).toString());

	}
	
	public void revertSelectedBlocks(Player player){
		Location[] locs = plugin.selectionMaster.getSelectedLocations(player);
		
		try{
			locs[1].getBlock();
		}catch(Exception e){
			return;
		}
		
		for(Location loc : locs){
			revertLocation(loc);
		}
	}

	public void removeStoredItem(Location loc){
		HashMap<Location, Material> tempStorage = getStoredBlocks();
		tempStorage.remove(tempStorage.get(loc));
		storedBlocks = tempStorage;
		plugin.log("Location removed");
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
	
	@SuppressWarnings("unused")
	private int getDefaultIncrementValue(){
		int value = 2;
		return value;
	}
}
