package us.twoguys.gentsdispute.commandExecutors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GentlemensDispute;

public class CmdChallenged implements CommandExecutor {
	
	GentlemensDispute plugin;

	public CmdChallenged(GentlemensDispute instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)){console(); return true;}
		if (!(plugin.tempData.waitingOnAcceptContains((Player) sender))){noChallenge(sender); return true;}
		if (args.length == 0){noArgs(sender); return false;}
		
		Player p2 = (Player) sender;
		Player p1 =  plugin.tempData.getChallenger(p2);
		
		if (args[0].equalsIgnoreCase("accept")){
			Player[] players = {p1, p2};
			plugin.duel.prepareDuel(players);
			plugin.tempData.removeWaitAccept(p1);
			acceptMessages(p1, p2);
			return true;
			
		}else if (args[0].equalsIgnoreCase("decline")){
			plugin.tempData.removeWaitAccept(p1);
			declineMessages(p1, p2);
			return true;
		}
		
		return false;
	}
	
	private void acceptMessages(Player p1, Player p2){
		p1.sendMessage(p2.getName() + " has accepted your challenge!");
		p2.sendMessage("Challenge accepted");
	}
	
	private void declineMessages(Player p1, Player p2){
		p1.sendMessage(p2.getName() + " has declined your challenge");
		p2.sendMessage("Challenge declined");
	}
	
	private void console(){
		plugin.log("You must be logged in to do that.");
	}
	
	private void noArgs(CommandSender sender){
		sender.sendMessage("You must either accept or decline the challenge.");
	}
	
	private void noChallenge(CommandSender sender){
		sender.sendMessage("You have not been challenged.");
	}

}
