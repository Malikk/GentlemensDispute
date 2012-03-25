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
					firstCheck = false;
					plugin.modes.acceptWaiting.put(p2, p1);
					counter = plugin.config.getTimeToRespond();
				}else if (firstCheck == false){
					if (counter > 0){
						plugin.log("Time Remaining: " + counter + " Seconds");
						counter--;
					}else if (counter == 0){
						p1.sendMessage("Challenge timed out!");
						p2.sendMessage("Challenge timed out!");
						plugin.modes.acceptWaiting.remove(p2);
						plugin.getServer().getScheduler().cancelTask(taskId);
					}
				}
				
				if (!(plugin.modes.acceptWaiting.containsKey(p2))){
					plugin.getServer().getScheduler().cancelTask(taskId);
				}
				
			}
		}, 0L, 20L);
		
	}
	
	//Match Begin Countdown
	
	//Ring Out Timer
	
	//Draw Timer
	
	
	
}
