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
		
		//Get Arena, if specified
		String arena = "";
		String at = "";
		if (args.length == 2){
			//once data is established, check to make sure arena exists
			at = " at ";
			arena = args[1].toString();
		}
		
		//Successful Command
		success(p1, p2, arena, at);
		broadcast(p1, p2, arena, at);
		
		Player[] players = {p1, p2};
		plugin.tempData.addMatchData("duel", arena, players);
		
		GDScheduler sche = new GDScheduler(plugin);
		sche.acceptTimer(p1, p2);
		return true;
	}
	
	private void success(Player p1, Player p2, String arena, String at){
		p1.sendMessage(String.format("You have challenged %s to a Duel%s%s!", p2.getName(), at, arena));
		p2.sendMessage(String.format("%s has challenged you to a Duel%s%s!", p1.getName(), at, arena));
		p2.sendMessage(String.format("You have %s seconds to /challenge <accept or decline>", plugin.config.getTimeToRespond()));
	}
	
	private void broadcast(Player p1, Player p2, String arena, String at){
		if (plugin.getConfig().getBoolean("Challenges.BroadcastToServer") == false){return;}
		
		for (Player player: Bukkit.getOnlinePlayers()){
			if (player == p1 || player == p2){
				continue;
			}else{
				player.sendMessage(String.format("%s has challenged %s to a duel!", p1.getName(), p2.getName()));
			}
		}
	}
	
	private void noArgs(CommandSender sender){
		sender.sendMessage("You must name a player to challenge");
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
