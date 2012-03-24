package us.twoguys.gentsdispute;

public class GDConfig {
	
	GentlemensDispute plugin;
	
	public GDConfig(GentlemensDispute instance){
		plugin = instance;
	}
	
	public void loadConfiguration(){
		String version = plugin.getDescription().getVersion();
		
		plugin.getConfig().options().header("\n Gentlemen'sDispute v" + version +"\n By Malikk and Arzeyt \n \n For help with the Config file, see the BukkitDev page.  \n ");
		
		plugin.getConfig().addDefault("Modes.Duel.Enabled", true);
		
		plugin.getConfig().addDefault("Modes.Duel.ClearInventories", true);
		
		plugin.getConfig().addDefault("Modes.Duel.GivenWeaponsAndItems.Slot1.ItemID", 0);
		plugin.getConfig().addDefault("Modes.Duel.GivenWeaponsAndItems.Slot1.Amount", 0);
		plugin.getConfig().addDefault("Modes.Duel.GivenWeaponsAndItems.Slot2.ItemID", 0);
		plugin.getConfig().addDefault("Modes.Duel.GivenWeaponsAndItems.Slot2.Amount", 0);
		plugin.getConfig().addDefault("Modes.Duel.GivenWeaponsAndItems.Slot3.ItemID", 0);
		plugin.getConfig().addDefault("Modes.Duel.GivenWeaponsAndItems.Slot3.Amount", 0);
		plugin.getConfig().addDefault("Modes.Duel.GivenWeaponsAndItems.Slot4.ItemID", 0);
		plugin.getConfig().addDefault("Modes.Duel.GivenWeaponsAndItems.Slot4.Amount", 0);
		
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
