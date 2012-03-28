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
		if (args.length == 0){noArgs(sender); return false;}
		if (args.length > 2){tooManyArgs(sender); return false;}
		
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
		
		//Get Arena
		String arena = "";
		if (args.length == 1){
			//arena = getDefaultArena
		}else if (args.length == 2){
			String arenaArg = args[1].toString();
			if (!(plugin.arenaMaster.nameIsTaken(arenaArg))){invalidArena((Player)sender); return false;}
			arena = arenaArg;
		}
		
		//Successful Command
		success(p1, p2, arena);
		broadcast(p1, p2, arena);
		
		Player[] players = {p1, p2};
		plugin.match.addMatchData("duel", arena, players);
		
		GDScheduler sche = new GDScheduler(plugin);
		sche.acceptTimer(p1, p2);
		return true;
	}
	
	private void success(Player p1, Player p2, String arena){
		p1.sendMessage(String.format("You have challenged %s to a Duel at %s!", p2.getName(), arena));
		p2.sendMessage(String.format("%s has challenged you to a Duel at %s!", p1.getName(), arena));
		p2.sendMessage(String.format("You have %s seconds to /challenge <accept or decline>", plugin.config.getTimeToRespond()));
	}
	
	private void broadcast(Player p1, Player p2, String arena){
		if (plugin.config.broadcastEnabled("Challenges") == false){return;}
		
		Player[] players = {p1, p2};
		plugin.broadcastExcept(players, String.format("%s has challenged %s to a duel at %s!", p1.getName(), p2.getName(), arena));
	}
	
	private void invalidArena(Player player){
		player.sendMessage("Invalid arena name.");
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
