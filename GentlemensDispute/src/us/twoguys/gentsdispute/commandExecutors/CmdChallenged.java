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
		if (!(plugin.match.waitingOnAcceptContains((Player) sender))){noChallenge(sender); return true;}
		if (args.length == 0){noArgs(sender); return false;}
		
		Player p2 = (Player) sender;
		Player p1 =  plugin.match.getChallenger(p2);
		Player[] players = {p1, p2};
		String response = args[0].toString();
		
		if (response.equalsIgnoreCase("accept")){
			plugin.modes.prepareMatchType(players);
			plugin.match.removeWaitAccept(p1);
			acceptMessages(p1, p2);
			broadcast(players, p1, p2, response);
			return true;
			
		}else if (response.equalsIgnoreCase("decline")){
			plugin.match.removeWaitAccept(p1);
			plugin.match.removeMatchData(players);
			declineMessages(p1, p2);
			broadcast(players, p1, p2, response);
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
	
	private void broadcast(Player[] players, Player p1, Player p2, String response){
		plugin.broadcastExcept(players, String.format("%s has " + response + "ed %s's Challenge!", p2.getName(), p1.getName()));
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
