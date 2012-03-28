package us.twoguys.gentsdispute;

import java.util.Arrays;

import org.bukkit.inventory.ItemStack;

public class GDConfig {
	
	GentlemensDispute plugin;
	
	public GDConfig(GentlemensDispute instance){
		plugin = instance;
	}
	
	public void loadConfiguration(){
		String version = plugin.getDescription().getVersion();
		String[] givenItems = {"276 1", "261 1", "262 64"};
		
		plugin.getConfig().options().header("\n Gentlemen'sDispute v" + version + "\n By Malikk and Arzeyt \n \n For help with the Config file, see the BukkitDev page.  \n ");
		
		plugin.getConfig().addDefault("Modes.Duel.Enabled", true);
		plugin.getConfig().addDefault("Modes.Duel.ClearInventories", true);
		plugin.getConfig().addDefault("Modes.Duel.GivenWeaponsAndItems", Arrays.asList(givenItems));
		plugin.getConfig().addDefault("Modes.Duel.GivenArmor.Helmet", 310);
		plugin.getConfig().addDefault("Modes.Duel.GivenArmor.Chestplate", 311);
		plugin.getConfig().addDefault("Modes.Duel.GivenArmor.Leggings", 312);
		plugin.getConfig().addDefault("Modes.Duel.GivenArmor.Boots", 313);
		plugin.getConfig().addDefault("Modes.Duel.Damage.Mobs", false);
		plugin.getConfig().addDefault("Modes.Duel.Damage.LavaAndFire", true);
		plugin.getConfig().addDefault("Modes.Duel.Damage.Explosions", false);
		plugin.getConfig().addDefault("Modes.Duel.Damage.Cactus", true);
		plugin.getConfig().addDefault("Modes.Duel.Damage.Fall", true);
		
		plugin.getConfig().addDefault("Challenges.TimeToRespond", 30);
		plugin.getConfig().addDefault("Challenges.CountdownTime", 10);
		plugin.getConfig().addDefault("Challenges.BroadcastToServer.Challenges", true);
		plugin.getConfig().addDefault("Challenges.BroadcastToServer.AcceptsAndDeclines", true);
		plugin.getConfig().addDefault("Challenges.BroadcastToServer.PlayerReady", true);
		plugin.getConfig().addDefault("Challenges.BroadcastToServer.MatchBeginAndEnd", true);
		plugin.getConfig().addDefault("Challenges.BroadcastToServer.MatchOutcomes", true);
		
		plugin.getConfig().addDefault("Arenas.General.RingOutTime", 5);
		plugin.getConfig().addDefault("Arenas.General.PlayersWarpedBack", true);
		
		/*
		for (Object arena: nicksMagicalArenaList){
			plugin.getConfig().addDefault("Arenas.Specific." + arena + "setting", 0);
		}
		*/
		
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
	    
	}
	
	public ItemStack[] getGivenItems(String mode){
		String[] givenItems = (String[]) plugin.getConfig().getList("Modes." + mode + ".GivenWeaponsAndItems").toArray();
		ItemStack[] items = new ItemStack[givenItems.length];
		int counter = 0;
		
		for (String itemInfo: givenItems){
			String[] split = itemInfo.split(" ");
			
			try{
				ItemStack item = new ItemStack(((int)Integer.parseInt(split[0])), ((int)Integer.parseInt(split[1])));
				items[counter++] = item;
			}catch(Exception e){
				plugin.logSevere("ItemStack creation failed! Check config file to make sure only integers are present in the GivenWeaponsAndItems list fields. ");
			}
		}
		return items;
	}
	
	public ItemStack[] getGivenArmor(String mode){
		ItemStack[] armor = {getHelmet(mode), getChestplate(mode), getLeggings(mode), getBoots(mode)};
		return armor;
	}
	
	public ItemStack getHelmet(String mode){
		ItemStack helmet = new ItemStack(plugin.getConfig().getInt("Modes." + mode + ".GivenArmor.Helmet", 1));
		return helmet;
	}
	
	public ItemStack getChestplate(String mode){
		ItemStack chestplate = new ItemStack(plugin.getConfig().getInt("Modes." + mode + ".GivenArmor.Chestplate", 1));
		return chestplate;
	}
	
	public ItemStack getLeggings(String mode){
		ItemStack leggings = new ItemStack(plugin.getConfig().getInt("Modes." + mode + ".GivenArmor.Leggings", 1));
		return leggings;
	}
	
	public ItemStack getBoots(String mode){
		ItemStack boots = new ItemStack(plugin.getConfig().getInt("Modes." + mode + ".GivenArmor.Boots", 1));
		return boots;
	}
	
	public int getTimeToRespond(){
		int seconds = plugin.getConfig().getInt("Challenges.TimeToRespond");
		return seconds;
	}
	
	public int getCountdownTime(){
		int countdown = plugin.getConfig().getInt("Challenges.CountdownTime");
		return countdown;
	}
	
	public boolean broadcastEnabled(String type){
		if (plugin.getConfig().getBoolean("Challenges.BroadcastToServer." + type)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean damageTypeAllowed(String mode, String type){
		if (plugin.getConfig().getBoolean("Modes." + mode + ".Damage." + type)){
			return true;
		}else{
			return false;
		}
	}
	
}
