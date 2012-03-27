package us.twoguys.gentsdispute;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import us.twoguys.gentsdispute.commandExecutors.*;
import us.twoguys.gentsdispute.listeners.SelectionListener;
import us.twoguys.gentsdispute.modes.*;
import us.twoguys.gentsdispute.arena.*;

public class GentlemensDispute extends JavaPlugin{
	
	//Logger
	private Logger log = Logger.getLogger("Minecraft");
	
	//Command Executors
	private CmdDuel duelPlayer;
	private CmdChallenged response;
	private CmdReady ready;
	private CmdSelection select;
	private CmdCreateArena createArena;
	
	//Classes
	public GDConfig config = new GDConfig(this);
	public GDTemporaryDataStorage tempData = new GDTemporaryDataStorage(this);
	public GDModes modes = new GDModes(this);
	public GDDuel duel = new GDDuel(this);
	public SelectionMaster selectionMaster = new SelectionMaster(this);
	public ArenaMaster arenaMaster;
	
	//Listeners
	SelectionListener selectionListener = new SelectionListener(this);
	
	public void onEnable(){
		config.loadConfiguration();
		loadCommandExecutors();
		
		arenaMaster = new ArenaMaster(this);
		
		//listeners
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(selectionListener, this);
		
		log("Enabled");
	}
	  
	public void onDisable(){
		arenaMaster.saveArenaData();
		log("Disabled");
	}
	
	public void log(String msg){
		PluginDescriptionFile pdfile = this.getDescription();	
		log.info("[" + pdfile.getName() + "] " + msg);
	}
	
	public void logSevere(String msg){
		PluginDescriptionFile pdfile = this.getDescription();		
		log.severe("[" + pdfile.getName() + "] " + msg);
	}
	
	public void broadcast(String msg){
		for (Player player: Bukkit.getOnlinePlayers()){
			player.sendMessage(msg);
		}
	}
	
	public void broadcastExcept(Player[] players, String msg){
		for (Player player: Bukkit.getOnlinePlayers()){
			for (Player p1: players){
				if (player.equals(p1)){
					return;
				}
			}
			player.sendMessage(msg);
		}
	}
	
	public void arrayMessage(Player[] players, String msg){
		for (Player player: players){
			player.sendMessage(msg);
		}
	}
	
	public void loadCommandExecutors(){
		
		duelPlayer = new CmdDuel(this);
		this.getCommand("duel").setExecutor(duelPlayer);
		
		response = new CmdChallenged(this);
		this.getCommand("challenge").setExecutor(response);
		
		ready = new CmdReady(this);
		this.getCommand("ready").setExecutor(ready);
		
		select = new CmdSelection(this);
		this.getCommand("select").setExecutor(select);
		
		createArena = new CmdCreateArena(this);
		this.getCommand("create").setExecutor(createArena);
		
	}
	
}
