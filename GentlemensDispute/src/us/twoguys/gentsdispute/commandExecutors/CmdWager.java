package us.twoguys.gentsdispute.commandExecutors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GentlemensDispute;

public class CmdWager implements CommandExecutor{
	
	GentlemensDispute plugin;

	public CmdWager(GentlemensDispute instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)){console(); return true;}
		if (args.length != 2){incorrectArgs(sender); return false;}
		if (plugin.match.hasMatchData((Player)sender)){cannotBetWhileInMatch(sender); return true;}
		
		//Get Player
		Player betOn = null;
		
		for (Player player: Bukkit.getOnlinePlayers()){
			if (player.getName().equalsIgnoreCase(args[0])){
				betOn = Bukkit.getPlayer(args[0]);
				break;
			}
		}
		
		if (betOn == null){notFound(sender); return false;}
		
		//Check Match
		if (!(plugin.match.hasMatchData(betOn))){betOnNotInMatch(sender, betOn); return true;}
		if (!(plugin.match.isOpenForWagers(plugin.match.getOtherPlayers(betOn)))){matchNotOpen(sender); return true;}
		
		//Get Wager
		double wager = 0;
		try{
			wager = Double.parseDouble(args[1]);
		}catch(Exception e){
			invalidWagerArg(sender);
			return false;
		}
		
		//Check Wager
		if (wager < 0){negativeWager(sender); return false;}
		if (!(plugin.vault.hasMoney((Player)sender, wager))){notEnoughMoney(sender); return true;}
		
		
		//Successful Command
		plugin.wager.placeWager((Player)sender, betOn, wager, false);
		
		
		return false;
	}
	
	private void cannotBetWhileInMatch(CommandSender sender){
		sender.sendMessage("You cannot make wagers while you are in a match");
	}
	
	private void betOnNotInMatch(CommandSender sender, Player player){
		sender.sendMessage(player.getName() + " is not in a match");
	}
	
	private void matchNotOpen(CommandSender sender){
		sender.sendMessage("That match is not open for wagers");
	}
	
	private void invalidWagerArg(CommandSender sender){
		sender.sendMessage("Wager amount must be a number");
	}
	
	private void negativeWager(CommandSender sender){
		sender.sendMessage("Wagers must be positive.");
	}
	
	@SuppressWarnings("static-access")
	private void notEnoughMoney(CommandSender sender){
		sender.sendMessage("You do not have enough " + plugin.vault.economy.currencyNamePlural());
	}
	
	private void incorrectArgs(CommandSender sender){
		sender.sendMessage("Invalid number of arguments");
	}
	
	private void notFound(CommandSender sender){
		sender.sendMessage("Player not found.");
	}
	
	private void console(){
		plugin.log("You must be logged in to do that!");
	}
	

}
