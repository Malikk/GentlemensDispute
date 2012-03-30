package us.twoguys.gentsdispute;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import us.twoguys.gentsdispute.commandExecutors.*;
import us.twoguys.gentsdispute.listeners.*;
import us.twoguys.gentsdispute.modes.*;
import us.twoguys.gentsdispute.arena.*;

public class GentlemensDispute extends JavaPlugin{
	
	//Logger
	private Logger log = Logger.getLogger("Minecraft");
	
	//Command Executors

	
	//Classes
	public GDConfig config = new GDConfig(this);
	public GDMatch match = new GDMatch(this);
	public GDModes modes = new GDModes(this);
	public GDDuel duel = new GDDuel(this);
	public SelectionMaster selectionMaster = new SelectionMaster(this);
	public ArenaMaster arenaMaster = new ArenaMaster(this);
	public ArenaPersister arenaPersister = new ArenaPersister(this);
	public ArenaVisualizer visualizer = new ArenaVisualizer(this);

	
	//Listeners
	SelectionListener selectionListener = new SelectionListener(this);
	PlayerListener playerListener = new PlayerListener(this);
	
	public void onEnable(){
		config.loadConfiguration();
		
		arenaPersister.Deserialize();
		
		loadCommandExecutors();
		
		//listeners
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(selectionListener, this);
		pm.registerEvents(playerListener, this);
		
		log("Enabled");
	}
	  
	public void onDisable(){
		arenaPersister.Serialize();
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
		CmdDuel duelPlayer;
		CmdChallenged response;
		CmdReady ready;
		CmdTeleport teleport;
		CmdSelection select;
		CmdCreateArena createArena;
		CmdDeleteArena deleteArena;
		CmdListArenas listArenas;
		
		duelPlayer = new CmdDuel(this);
		this.getCommand("duel").setExecutor(duelPlayer);
		
		response = new CmdChallenged(this);
		this.getCommand("challenge").setExecutor(response);
		
		ready = new CmdReady(this);
		this.getCommand("ready").setExecutor(ready);
		
		teleport = new CmdTeleport(this);
		this.getCommand("gdtp").setExecutor(teleport);
		
		select = new CmdSelection(this);
		this.getCommand("select").setExecutor(select);
		
		createArena = new CmdCreateArena(this);
		this.getCommand("create").setExecutor(createArena);
		
		deleteArena = new CmdDeleteArena(this);
		this.getCommand("delete").setExecutor(deleteArena);
		
		listArenas = new CmdListArenas(this);
		this.getCommand("listarenas").setExecutor(listArenas);
		
	}
	
}
