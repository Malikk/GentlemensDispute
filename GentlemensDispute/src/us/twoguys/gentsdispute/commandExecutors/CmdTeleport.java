package us.twoguys.gentsdispute.commandExecutors;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GentlemensDispute;

public class CmdTeleport implements CommandExecutor {
	
	GentlemensDispute plugin;

	public CmdTeleport(GentlemensDispute instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)){console(); return true;}
		if (args.length > 1){return false;}
		
		if (plugin.match.isWaitingAfterMatch((Player)sender)){
			if (args.length == 1){notAllowed(sender);}
			
			leavingArena(sender);
			plugin.modes.tpBack((Player)sender);
			return true;
		}else{
			if (args.length == 0){
				if (plugin.match.hasReturnLocation((Player)sender)){
					((Player) sender).teleport(plugin.match.getPlayerReturnLocation((Player)sender));
					plugin.match.removePlayerReturnLocation((Player)sender);
					return true;
				}else{
					noDestination(sender);
					return false;
				}
			}else if (args.length == 1){
				if (plugin.arenaMaster.nameIsTaken(args[0])){
					if (!(plugin.match.hasReturnLocation((Player)sender))){
						plugin.match.addPlayerReturnLocation((Player)sender, (Location)((Player) sender).getLocation());
					}
					
					Location tpLoc = plugin.arenaMaster.getArenaData(args[0]).getSpectatorSpawn();
					((Player) sender).teleport(tpLoc);
					return true;
				}else{
					invalidName(sender);
					return false;
				}
			}
		}
		return true;
	}
	
	private void leavingArena(CommandSender sender){
		Player[] player = {(Player)sender};
		plugin.broadcastExcept(player, String.format("%s has left the arena", sender.getName()));
		sender.sendMessage("You have left the Arena");
	}
	
	private void notAllowed(CommandSender sender){
		sender.sendMessage("You cannot specify a location after a match");
	}
	
	private void noDestination(CommandSender sender){
		sender.sendMessage("You must select a destination.");
	}
	
	private void invalidName(CommandSender sender){
		sender.sendMessage("Invalid arena name.");
	}
	
	private void console(){
		plugin.log("You must be logged in to do that.");
	}
}
