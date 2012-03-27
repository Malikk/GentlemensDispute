package us.twoguys.gentsdispute.commandExecutors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GentlemensDispute;

public class CmdSelection implements CommandExecutor{

	GentlemensDispute plugin;
	
	public CmdSelection(GentlemensDispute instance){
		plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			plugin.log("You must be logged in to use that command");
		}else{
			
		}
		return false;
	}

}
