package us.twoguys.gentsdispute.commandExecutors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GentlemensDispute;

public class CmdDuel implements CommandExecutor {
	
	GentlemensDispute plugin;

	public CmdDuel(GentlemensDispute instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)){console(); return false;}
		if (args.length == 0){noArgs(sender); return false;}
		
		Player P1 = (Player) sender;
		Player P2 = null;
		
		for (Player player: Bukkit.getOnlinePlayers()){
			if (player.getName().equalsIgnoreCase(args[0])){
				P2 = Bukkit.getPlayer(args[0]);
				break;
			}
		}
		
		if (P2 == null){notFound(sender); return false;}
		if (P2 == P1){samePlayer(sender); return false;}
		
		//Successful Command
		success(sender, P2);
		broadcast(P1, P2);
		return true;
	}
	
	private void success(CommandSender sender, Player P2){
		sender.sendMessage(String.format("You have challenged %s to a Duel!", P2.getName()));
	}
	
	private void broadcast(Player P1, Player P2){
		if (plugin.getConfig().getBoolean("Challenges.BroadcastToServer") == false){return;}
		
		for (Player player: Bukkit.getOnlinePlayers()){
			if (player == P1 || player == P2){
				continue;
			}else{
				player.sendMessage(String.format("%s has challenged %s to a duel!", P1.getName(), P2.getName()));
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
		plugin.Log("You must be logged in to do that!");
	}
	
}
