package us.twoguys.gentsdispute.commandExecutors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GentlemensDispute;
import us.twoguys.gentsdispute.arena.ArenaMaster;
import us.twoguys.gentsdispute.arena.SelectionMaster;

public class CmdCreateArena implements CommandExecutor{

	GentlemensDispute plugin;
	SelectionMaster selectionMaster;
	ArenaMaster arenaMaster;
	
	public CmdCreateArena(GentlemensDispute instance){
		plugin = instance;
		selectionMaster = plugin.selectionMaster;
		arenaMaster = plugin.arenaMaster;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			plugin.log("You must be logged in to do that!");
			return false;
		}else if(!selectionMaster.getSelectorString((Player)sender).equalsIgnoreCase("complete")){
			plugin.sendMessage((Player)sender, ChatColor.RED + "You have not selected enough points to create an arena");
			return false;
		}
		else if(selectionMaster.getSelectorString((Player)sender).equalsIgnoreCase("complete")){
			
			if(args.length==0){
				plugin.sendMessage((Player)sender, ChatColor.RED + "Type an arena name in next time");
				return false;
			}else if(args.length==1){
				if(!arenaMaster.nameIsTaken(args[0])){
				
				selectionMaster.createArenaWithSelectedPoints((Player)sender, args[0]);		
				plugin.visualizerPlus.revertPlayerBlocks((Player)sender);
				
				return true;
				}else{
					plugin.sendMessage((Player)sender, ChatColor.RED+"That name is already taken");
				}
			}
		}
		plugin.sendMessage((Player)sender, ChatColor.RED+"Something went terribly wrong...");
		return false;
		
	}
	
	
}
