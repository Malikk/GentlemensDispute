package us.twoguys.gentsdispute.listeners;

import java.util.HashMap;

import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GentlemensDispute;

public class SelectionMaster {

	GentlemensDispute plugin;
	HashMap<Player, Integer> selectors = new HashMap<Player, Integer>();
	
	public SelectionMaster(GentlemensDispute instance){
		plugin = instance;
	}
	
	public void addSelector(Player player){
		
	}
}
