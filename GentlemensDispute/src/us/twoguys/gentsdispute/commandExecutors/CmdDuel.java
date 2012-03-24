package us.twoguys.gentsdispute.commandExecutors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GentlemensDispute;

public class CmdDuel implements CommandExecutor {
	
	GentlemensDispute plugin;

	public CmdDuel(GentlemensDispute instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)){console(); return false;}
		if (args[0] == null){noArgs(sender); return false;}
		
		return false;
	}
	
	private void console(){
		plugin.Log("You must be logged in to do that!");
	}
	
	private void noArgs(CommandSender sender){
		sender.sendMessage("You must name a player to challenge");
	}
}
