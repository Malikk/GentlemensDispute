package us.twoguys.gentsdispute;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import us.twoguys.gentsdispute.commandExecutors.*;
import us.twoguys.gentsdispute.modes.*;
import us.twoguys.gentsdispute.arena.*;

public class GentlemensDispute extends JavaPlugin{
	
	//Logger
	private Logger log = Logger.getLogger("Minecraft");
	
	//Command Executors
	private CmdDuel duelPlayer;
	private CmdChallenged response;
	private CmdReady ready;
	
	//Classes
	public GDConfig config = new GDConfig(this);
	public GDTemporaryDataStorage tempData = new GDTemporaryDataStorage(this);
	public GDModes modes = new GDModes(this);
	public GDDuel duel = new GDDuel(this);
	public ArenaMaster arenaMaster = new ArenaMaster(this);
	
	public void onEnable(){
		config.loadConfiguration();
		loadCommandExecutors();
		log("Enabled");
	}
	  
	public void onDisable(){
		log("Disabled");
	}
	
	public void log(String msg){
		PluginDescriptionFile pdfile = this.getDescription();		
		this.log.info("[" + pdfile.getName() + "] " + msg);
	}
	
	public void logSevere(String msg){
		PluginDescriptionFile pdfile = this.getDescription();		
		this.log.severe("[" + pdfile.getName() + "] " + msg);
	}
	
	public void loadCommandExecutors(){
		
		duelPlayer = new CmdDuel(this);
		this.getCommand("duel").setExecutor(duelPlayer);
		
		response = new CmdChallenged(this);
		this.getCommand("challenge").setExecutor(response);
		
		ready = new CmdReady(this);
		this.getCommand("ready").setExecutor(ready);
	}
	
}
