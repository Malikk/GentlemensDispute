package us.twoguys.gentsdispute;

import org.bukkit.entity.Player;

public class GDScheduler {
	
	GentlemensDispute plugin;
	int taskId;
	
	public GDScheduler(GentlemensDispute instance){
		plugin = instance;
	}
	
	//Challenge Acceptance Timer
	public void acceptTimer(final Player p1, final Player p2){
		taskId = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable(){
			boolean firstCheck = true;
			int counter;
			
			public void run(){
				if (firstCheck){
					Player[] players = {p1, p2};
					plugin.tempData.addWaitAccept(p1, players);
					counter = plugin.config.getTimeToRespond();
					firstCheck = false;
				}else if (firstCheck == false){
					if (counter > 0){
						plugin.log("Time Remaining: " + counter + " Seconds");
						counter--;
					}else if (counter == 0){
						p1.sendMessage("Challenge timed out!");
						p2.sendMessage("Challenge timed out!");
						plugin.tempData.removeWaitAccept(p1);
						plugin.getServer().getScheduler().cancelTask(taskId);
					}
				}
				
				if (!(plugin.tempData.waitingOnAcceptContains(p1)) || !(plugin.tempData.waitingOnAcceptContains(p2))){
					plugin.getServer().getScheduler().cancelTask(taskId);
				}
			}
		}, 0L, 20L);
		
	}
	
	public void waitingOnReady(final Player[] players){
		taskId = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable(){
			
			public void run(){
				for (Player player: players){
					if (plugin.tempData.waitingOnReadyContains(player)){
						return;
					}
				}
				
				//Once no players are in the waitingOnReady HashSet
				plugin.modes.readyMatchType(players);
			}
		}, 0L, 20L);
	}
	
	//Match Begin Countdown
	
	//Ring Out Timer
	public void ringOutTimer(Player p1, Player p2, String arena){
		
	}
	
	//Draw Timer
	
	
	
}
