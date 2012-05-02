package us.twoguys.gentsdispute.commandExecutors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GentlemensDispute;
import us.twoguys.gentsdispute.arena.ArenaMaster;

public class CmdDeleteArena implements CommandExecutor{

	GentlemensDispute plugin; 
	ArenaMaster arenaMaster;
	
	public CmdDeleteArena(GentlemensDispute instance){
		plugin = instance;
		arenaMaster = plugin.arenaMaster;
	}

	public boolean onCommand(CommandSender sender, Command label, String arg, String[] args) {
		if(!(sender instanceof Player)){
			plugin.log("You must be logged in to do that");
			return false;
		}else if(args.length > 1){
			return false;
		}else if(args.length == 0){
			return false;
		}else{
			arenaMaster.deleteArena(args[0]);
			plugin.sendMessage((Player)sender, ChatColor.GREEN+ args[0] +" was deleted.");
			return true;
		}
		
	}
}
