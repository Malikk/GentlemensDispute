package us.twoguys.gentsdispute.commandExecutors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GDScheduler;
import us.twoguys.gentsdispute.GentlemensDispute;

public class CmdDuel implements CommandExecutor {
	
	GentlemensDispute plugin;

	public CmdDuel(GentlemensDispute instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)){console(); return true;}
		if (plugin.match.hasMatchData((Player)sender)){existingMatch(sender); return true;}
		if (args.length == 0){noArgs(sender); return false;}
		if (args.length > 3){tooManyArgs(sender); return false;}
		
		//Get Players
		Player p1 = (Player) sender;
		Player p2 = null;
		
		for (Player player: Bukkit.getOnlinePlayers()){
			if (player.getName().equalsIgnoreCase(args[0])){
				p2 = Bukkit.getPlayer(args[0]);
				break;
			}
		}
		
		if (p2 == null){notFound(sender); return false;}
		if (p2 == p1){samePlayer(sender); return false;}
		if (plugin.match.hasMatchData(p2)){otherExistingMatch(sender, p2); return true;}
		
		//Get Arena and wager
		String arena = "";
		double wager = 0;
		
			//No Wager or Arena specified
			if (args.length == 1){
				//arena = plugin.arenaMaster.getDefaultArena(); 
				arena = "LavaArena";
				
				
			//Wager OR Arena specified
			}else if (args.length == 2){
				//Attempt to parse arg as double
				try{
					wager = Double.parseDouble(args[1]);
					arena = plugin.arenaMaster.getDefaultArena();
					
				}catch(Exception e){
					//If arg cannot be parsed as a double, then it is the arena name
					arena = args[1].toString();
				}
				
				
			//Wager AND Arena specified
			}else if (args.length == 3){
				try{
					//Attempt to parse the args[1] to a double
					wager = Double.parseDouble(args[1]);
					arena = args[2].toString();
					
				}catch(Exception e){
					//If args[1] cannot be parsed as a double, then switch the args checked
					wager = Double.parseDouble(args[2]);
					arena = args[1].toString();
					
				}
			}
			
			//Check if Wager and Arena are valid
			if (wager < 0){negativeWager(sender); return false;}
			if (plugin.vault.hasMoney((Player)sender, wager)){notEnoughMoney(sender); return true;}
			if (plugin.arenaMaster.nameIsTaken(arena) == false){invalidArena((Player)sender); return false;}
			if (plugin.match.arenaIsInUse(arena)){inUse(sender, arena); return true;}
		
		//Successful Command
		success(p1, p2, arena, wager);
		broadcast(p1, p2, arena, wager);
		
		Player[] players = {p1, p2};
		plugin.match.addMatchData("duel", arena, players);
		plugin.match.switchTurn(p1);
		
		plugin.wager.setCombatWager(p1, wager);
		
		GDScheduler sche = new GDScheduler(plugin);
		sche.acceptTimer(p1, p2);
		return true;
	}
	
	@SuppressWarnings("static-access")
	private void success(Player p1, Player p2, String arena, double wager){
		if (wager == 0){
			p1.sendMessage(String.format("You have challenged %s to a Friendly Duel at %s!", p2.getName(), arena));
			p2.sendMessage(String.format("%s has challenged you to a Friendly Duel at %s!", p1.getName(), arena));
			p2.sendMessage(String.format("You have %s seconds to /challenge <accept, decline, or raise>", plugin.config.getTimeToRespond()));
		}else{
			p1.sendMessage(String.format("You have challenged %s to a Duel at %s for %s %s!", p2.getName(), arena, wager, plugin.vault.economy.currencyNamePlural()));
			p2.sendMessage(String.format("%s has challenged you to a Duel at %s for %s %s!", p1.getName(), arena, wager, plugin.vault.economy.currencyNamePlural()));
			p2.sendMessage(String.format("You have %s seconds to /challenge <accept, decline, or raise>", plugin.config.getTimeToRespond()));
		}
	}
	
	@SuppressWarnings("static-access")
	private void broadcast(Player p1, Player p2, String arena, double wager){
		if (plugin.config.broadcastEnabled("Challenges") == false){return;}
		
		Player[] players = {p1, p2};
		
		if (wager == 0){
			plugin.broadcastExcept(players, String.format("%s has challenged %s to a Friendly duel at %s!", p1.getName(), p2.getName(), arena));
		}else{
			plugin.broadcastExcept(players, String.format("%s has challenged %s to a duel at %s for %s %s!", p1.getName(), p2.getName(), arena, wager, plugin.vault.economy.currencyNamePlural()));
		}
	}
	
	private void existingMatch(CommandSender sender){
		sender.sendMessage("You are already in a match.");
	}
	
	private void otherExistingMatch(CommandSender sender, Player player){
		sender.sendMessage(String.format("%s is already in a match", player.getName()));
	}
	
	private void negativeWager(CommandSender sender){
		sender.sendMessage("Wagers must be positive.");
	}
	
	@SuppressWarnings("static-access")
	private void notEnoughMoney(CommandSender sender){
		sender.sendMessage("You do not have enough " + plugin.vault.economy.currencyNamePlural());
	}
	
	private void invalidArena(Player player){
		player.sendMessage("Invalid arena name.");
	}
	
	private void inUse(CommandSender sender, String arena){
		sender.sendMessage(arena + " is already in use!");
	}
	
	private void noArgs(CommandSender sender){
		sender.sendMessage("You must name a player to challenge");
	}
	
	private void tooManyArgs(CommandSender sender){
		sender.sendMessage("You have entered too many arguments");
	}
	
	private void notFound(CommandSender sender){
		sender.sendMessage("Player not found.");
	}
	
	private void samePlayer(CommandSender sender){
		sender.sendMessage("You cannot challenge yourself!");
	}
	
	private void console(){
		plugin.log("You must be logged in to do that!");
	}
	
}
