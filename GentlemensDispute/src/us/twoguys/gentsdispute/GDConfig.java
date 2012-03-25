package us.twoguys.gentsdispute;

import java.util.Arrays;

public class GDConfig {
	
	GentlemensDispute plugin;
	
	public GDConfig(GentlemensDispute instance){
		plugin = instance;
	}
	
	public void loadConfiguration(){
		String version = plugin.getDescription().getVersion();
		String[] givenItems = {"276 1", "261 1", "262 64"};
		
		plugin.getConfig().options().header("\n Gentlemen'sDispute v" + version +"\n By Malikk and Arzeyt \n \n For help with the Config file, see the BukkitDev page.  \n ");
		
		plugin.getConfig().addDefault("Modes.Duel.Enabled", true);
		plugin.getConfig().addDefault("Modes.Duel.ClearInventories", true);
		plugin.getConfig().addDefault("Modes.Duel.GivenWeaponsAndItems", Arrays.asList(givenItems));
		plugin.getConfig().addDefault("Modes.Duel.GivenArmor.Helmet", 0);
		plugin.getConfig().addDefault("Modes.Duel.GivenArmor.Chestplate", 0);
		plugin.getConfig().addDefault("Modes.Duel.GivenArmor.Leggings", 0);
		plugin.getConfig().addDefault("Modes.Duel.GivenArmor.Boots", 0);
		
		plugin.getConfig().addDefault("Challenges.TimeToRespond", 0);
		plugin.getConfig().addDefault("Challenges.BroadcastToServer", true);
		
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
	    
	}
}
