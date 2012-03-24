package us.twoguys.gentsdispute;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class GentlemensDispute extends JavaPlugin{
	
	private Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable(){  
		Log("Enabled");
	}
	  
	public void onDisable(){
		Log("Disabled");
	}

	public void loadConfiguration(){
		//config defaults
	}
	
	public void Log(String msg){
		PluginDescriptionFile pdfile = this.getDescription();		
		this.log.info("[" + pdfile.getName() + "] " + msg);
	}
	
}
