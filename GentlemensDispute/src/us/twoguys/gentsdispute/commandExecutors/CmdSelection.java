package us.twoguys.gentsdispute.commandExecutors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GentlemensDispute;
import us.twoguys.gentsdispute.arena.SelectionMaster;

public class CmdSelection implements CommandExecutor{

	GentlemensDispute plugin;
	SelectionMaster selectionMaster;
	
	public CmdSelection(GentlemensDispute instance){
		plugin = instance;
		selectionMaster = plugin.selectionMaster;
		
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			plugin.log("You must be logged in to use that command");
			return false;
		}else if(selectionMaster.isSelecting((Player)sender)){
			selectionMaster.removeSelector((Player)sender);
			sender.sendMessage("You are no longer selecting an arena");
			plugin.visualizer.revertSelectedBlocks((Player)sender);
			return true;
		}else{
			Player player = (Player)sender;
			selectionMaster.addSelector(player);
			player.sendMessage("Select the boundaries of the arena.");
			return true;
		}
	}

}
