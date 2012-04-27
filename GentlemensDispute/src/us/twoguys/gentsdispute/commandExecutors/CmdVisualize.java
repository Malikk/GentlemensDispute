package us.twoguys.gentsdispute.commandExecutors;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GentlemensDispute;

public class CmdVisualize implements CommandExecutor{

	GentlemensDispute plugin;
	
	HashSet<Player> toggleVisualize = new HashSet<Player>();
	
	public CmdVisualize(GentlemensDispute instance){
		plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command label, String arg, String[] args) {
		if(!(sender instanceof Player)){
			plugin.log("You must be logged in to do that!");
			return false;
		}
		else if(args.length < 1){
			sender.sendMessage(ChatColor.RED+"Type an arena name next time");
		}
		else if(toggleVisualize.contains((Player)sender)==false){
			plugin.visualizerPlus.visualizeArena((Player)sender, args[0]);
			toggleVisualize.add((Player)sender);
			return true;
			
		}
		else if(toggleVisualize.contains((Player)sender)==true){
			plugin.visualizerPlus.revertPlayerBlocks((Player)sender);
			toggleVisualize.remove((Player)sender);
			return true;
		}
		return false;		
	}




}
