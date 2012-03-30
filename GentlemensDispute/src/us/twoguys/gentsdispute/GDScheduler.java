package us.twoguys.gentsdispute;

import java.util.HashSet;

import org.bukkit.entity.Player;

public class GDScheduler {
	
	GentlemensDispute plugin;
	private int taskId;
	private Player stillAlive;
	private HashSet<Player> isAlive = new HashSet<Player>();
	
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
					plugin.match.addWaitAccept(p1, players);
					counter = plugin.config.getTimeToRespond();
					firstCheck = false;
				}else if (firstCheck == false){
					if (counter > 0){
						plugin.log("Time Remaining: " + counter + " Seconds");
						counter--;
					}else if (counter == 0){
						p1.sendMessage("Challenge timed out!");
						p2.sendMessage("Challenge timed out!");
						plugin.match.removeWaitAccept(p1);
						plugin.getServer().getScheduler().cancelTask(taskId);
					}
				}
				
				if (!(plugin.match.waitingOnAcceptContains(p1)) || !(plugin.match.waitingOnAcceptContains(p2))){
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
					if (plugin.match.waitingOnReadyContains(player)){
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
						
						if (plugin.config.broadcastEnabled("MatchBeginAndEnd")){
							plugin.broadcastExcept(players, "Match has begun.");
						}
						
						plugin.getServer().getScheduler().cancelTask(taskId);
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
				//plugin.tempData.isConfined(Player, arenanameasstring)
			}
		}, 0L, 20L);
	}
	
	//Draw Timer
	public void drawChecker(final Player[] players){
		for (Player arrayPlayer: players){
			isAlive.add(arrayPlayer);
		}
		
		taskId = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable(){
			
			public void run(){
				int counter = 0;
				
				for (Player player: players){
					if (player.isDead()){
						isAlive.remove(player);
					}
				}
				
				for (Player hashPlayer: isAlive){
					if (plugin.playerListener.playerIsOnFire(hashPlayer)){
						return;
					}
					stillAlive = hashPlayer;
					counter++;
				}
				
				if (counter > 1){
					return;
				}else if (counter == 1){
					Player winner = stillAlive;
					plugin.modes.winnerMatchType(players, winner);
					plugin.getServer().getScheduler().cancelTask(taskId);
				}else if (counter == 0){
					plugin.modes.drawMatchType(players);
					plugin.getServer().getScheduler().cancelTask(taskId);
				}
				
			}
		}, 0L, 20L);
	}
	
	//tp Back Timer
	public void tpBackTimer(final Player[] players){
		taskId = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable(){
			int counter = plugin.config.getForcedTpTime();
			boolean firstCheck = true;
			
			public void run(){
				int playerCounter = 0;
				
				//If no players are still waiting
				for (Player waiting: players){
					if (plugin.match.isWaitingAfterMatch(waiting)){
						continue;
					}else{
						playerCounter++;
					}
					
					if (playerCounter == players.length){
						plugin.match.removeMatchData(players);
						plugin.getServer().getScheduler().cancelTask(taskId);
					}
				}
				
				//If players are still waiting
				if (firstCheck){
					plugin.arrayMessage(players, String.format("You will be teleported away in %s seconds.", counter));
					plugin.arrayMessage(players, String.format("type /gdtp to leave now"));
					firstCheck = false;
				}else{
					if (counter > 0){
						counter--;
						return;
					}else if (counter == 0){
						for (Player player: players){
							String arena = plugin.match.getArena(players);
							if (plugin.match.isConfined(player, arena)){
								plugin.modes.tpBack(player);
							}else{
								continue;
							}
						}
						plugin.match.removeMatchData(players);
						plugin.getServer().getScheduler().cancelTask(taskId);
					}
				}
			}
		}, 0L, 20L);
	}
	
}
