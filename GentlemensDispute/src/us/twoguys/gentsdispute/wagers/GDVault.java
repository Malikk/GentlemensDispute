package us.twoguys.gentsdispute.wagers;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import us.twoguys.gentsdispute.*;

public class GDVault {

	GentlemensDispute plugin;
	public static Vault vault = null;
	public static Economy economy = null;
	
	public GDVault(GentlemensDispute instance){
		plugin = instance;
	}
	
	public void hookVault(){
		Plugin x = plugin.getServer().getPluginManager().getPlugin("Vault");
		if(x != null & x instanceof Vault) {
			vault = (Vault)x;
			plugin.log(String.format("[%s] Hooked %s %s", plugin.getDescription().getName(), vault.getDescription().getName(), vault.getDescription().getVersion()));
			setupEconomy();
		} else {
			plugin.log("Vault was not found.");
		}
	}
	
	private Boolean setupEconomy(){
        RegisteredServiceProvider<Economy> economyProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
	
	public void addMoney(Player player, Double amt){
		economy.depositPlayer(player.getName(), amt);
	}
	
	public void removeMoney(Player player, Double amt){
		economy.withdrawPlayer(player.getName(), amt);
	}
	
	public boolean hasMoney(Player player, Double amt){
		if (economy.has(player.getName(), amt)){
			return true;
		}else{
			return false;
		}
	}
	
}
