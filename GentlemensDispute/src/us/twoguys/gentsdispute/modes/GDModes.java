package us.twoguys.gentsdispute.modes;

import java.util.HashMap;

import org.bukkit.entity.Player;

import us.twoguys.gentsdispute.GentlemensDispute;

public class GDModes {

	GentlemensDispute plugin;
	public HashMap<Player, Player> acceptWaiting = new HashMap<Player, Player>();
	
	public GDModes(GentlemensDispute instance){
		plugin = instance;
	}
	
	//onChallengeAccept
		//add players to isBattling hashmap
		//teleport players to arena
			//players can no longer be damaged
			//edit inventories
	
	//onPlayerReady
		//begin countdown
	
	//Begin Match
		//run scheduler to make sure players stay in the arena
	
}
