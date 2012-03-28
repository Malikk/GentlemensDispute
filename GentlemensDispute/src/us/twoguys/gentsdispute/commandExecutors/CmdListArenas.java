package us.twoguys.gentsdispute.commandExecutors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import us.twoguys.gentsdispute.GentlemensDispute;
import us.twoguys.gentsdispute.arena.ArenaMaster;

public class CmdListArenas implements CommandExecutor{

	GentlemensDispute plugin;
	ArenaMaster arenaMaster;
	
	public CmdListArenas(GentlemensDispute instance){
		plugin = instance;
		arenaMaster = plugin.arenaMaster;
	}

	public boolean onCommand(CommandSender sender, Command label, String arg, String[] args) {
		sender.sendMessage(arenaMaster.getArenaNamesString());	
		return true;
	}
}
