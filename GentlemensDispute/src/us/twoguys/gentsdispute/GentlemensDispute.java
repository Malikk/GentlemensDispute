package us.twoguys.gentsdispute;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import us.twoguys.gentsdispute.commandExecutors.*;

public class GentlemensDispute extends JavaPlugin{
	
	//Logger
	private Logger log = Logger.getLogger("Minecraft");
	
	//Command Executors
	private CmdDuel duel;
	private CmdChallenged response;
	
	//Classes
	GDConfig config = new GDConfig(this);
	
	public void onEnable(){
		config.loadConfiguration();
		loadCommandExecutors();
		Log("Enabled");
	}
	  
	public void onDisable(){
		Log("Disabled");
	}
	
	public void Log(String msg){
		PluginDescriptionFile pdfile = this.getDescription();		
		this.log.info("[" + pdfile.getName() + "] " + msg);
	}
	
	public void loadCommandExecutors(){
		
		duel = new CmdDuel(this);
		this.getCommand("duel").setExecutor(duel);
		
		response = new CmdChallenged(this);
		this.getCommand("challenge").setExecutor(response);
	}
	
}
