package us.twoguys.gentsdispute.commandExecutors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GentlemensDispute;

public class CmdReady implements CommandExecutor{
	
	GentlemensDispute plugin;

	public CmdReady(GentlemensDispute instance) {
		plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)){console(); return true;}
		if (!(plugin.match.waitingOnReadyContains((Player) sender))){notWaiting(sender); return true;}
		
		readyMessage((Player) sender);
		plugin.match.removeWaitReady((Player) sender);
		return true;
	}
	
	private void readyMessage(Player player){
		plugin.broadcast(player.getName() + " is ready");
	}

	private void notWaiting(CommandSender sender){
		sender.sendMessage("No one is waiting on you.");
	}
	
	private void console(){
		plugin.log("You must be logged in to do that!");
	}
}
