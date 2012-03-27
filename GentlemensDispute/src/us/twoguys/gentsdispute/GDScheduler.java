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
	
	//Checking if players are ready
	public void waitingOnReady(final Player[] players){
		taskId = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable(){
			
			public void run(){
				for (Player player: players){
					if (plugin.tempData.waitingOnReadyContains(player)){
						return;
					}
				}
				
				//Once all players are ready
				plugin.modes.allPlayersReady(players);
				plugin.getServer().getScheduler().cancelTask(taskId);
			}
		}, 0L, 20L);
	}
	
	//Match Begin Countdown
	public void countdown(final Player[] players){
		taskId = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable(){
			int count = plugin.config.getCountdownTime();
			boolean firstCheck = true;
			
			public void run(){
				if (firstCheck){
					plugin.arrayMessage(players, "Match starting in...");
					firstCheck = false;
				}else{
					if (count > 0){
						plugin.arrayMessage(players, "" + count);
						count--;
					}else if (count == 0){
						plugin.modes.beginMatchType(players);
						plugin.arrayMessage(players, "---BEGIN!---");
						plugin.broadcastExcept(players, "Match has begun.");
					}
				}
			}
		}, 0L, 20L);
	}
	
	//Ring Out Timer
	public void ringOutTimer(final Player[] players, final String arena){
		taskId = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable(){
			
			public void run(){
				//ready for Nick
			}
		}, 0L, 20L);
	}
	
	//Draw Timer
	
	
	
}
