package us.twoguys.gentsdispute.commandExecutors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GDScheduler;
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
		Player[] switchPlayers = {p2, p1};
		String response = args[0].toString();
		
		if (response.equalsIgnoreCase("accept")){
			acceptMessages(p1, p2);
			broadcast(players, p1, p2, response);
			try{
				plugin.modes.prepareMatchType(players);
				plugin.match.removeWaitAccept(p1);
			}catch(Exception e){
				plugin.modes.prepareMatchType(switchPlayers);
				plugin.match.removeWaitAccept(p1);
			}
			return true;
		}else if (response.equalsIgnoreCase("decline")){
			plugin.match.removeWaitAccept(p1);
			plugin.match.removeMatchData(players);
			declineMessages(p1, p2);
			broadcast(players, p1, p2, response);
			return true;
		}else if (response.equalsIgnoreCase("raise")){
			if (args.length >= 2){
				//Attempt to parse arg as Double
				try{
					double raise = Double.parseDouble(args[1]);
					
					plugin.match.removeWaitAccept(p1);
					raiseMessages(p1,p2, raise);
					broadcastRaise(players, p1, p2, raise);
					
					GDScheduler sche = new GDScheduler(plugin);
					sche.acceptTimer(p2, p1);
					
					return true;
					
				}catch(Exception e){
					failedToParse(sender);
					return false;
				}
			}else{
				noAmount(sender);
				return false;
			}
		}else{
			invalidResponse(sender, response);
			return false;
		}
	}
	
	private void acceptMessages(Player p1, Player p2){
		p1.sendMessage(p2.getName() + " has accepted your challenge!");
		p2.sendMessage("Challenge accepted");
	}
	
	private void declineMessages(Player p1, Player p2){
		p1.sendMessage(p2.getName() + " has declined your challenge");
		p2.sendMessage("Challenge declined");
	}
	
	@SuppressWarnings("static-access")
	private void raiseMessages(Player p1, Player p2, double raise){
		p1.sendMessage(String.format("%s has raised you %s %s!", p2.getName(), raise, plugin.vault.economy.currencyNamePlural()));
		p2.sendMessage(String.format("You have raised %s %s %s", p1.getName(), raise, plugin.vault.economy.currencyNamePlural()));
	}
	
	private void broadcast(Player[] players, Player p1, Player p2, String response){
		if (plugin.config.broadcastEnabled("Responses")){
			plugin.broadcastExcept(players, String.format("%s has " + response + "ed %s's Challenge!", p2.getName(), p1.getName()));
		}
	}
	
	@SuppressWarnings("static-access")
	private void broadcastRaise(Player[] players, Player p1, Player p2, double raise){
		if (plugin.config.broadcastEnabled("Responses")){
			plugin.broadcastExcept(players, String.format("%s has raised %s to %s %s.", p2.getName(), p1.getName(), raise, plugin.vault.economy.currencyNamePlural()));
		}
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
	
	private void failedToParse(CommandSender sender){
		sender.sendMessage("Raise amount must be a number");
	}
	
	private void noAmount(CommandSender sender){
		sender.sendMessage("If you raise, you must specify an amount");
	}
	
	private void invalidResponse(CommandSender sender, String response){
		sender.sendMessage(response + " is not a valid response");
	}

}
