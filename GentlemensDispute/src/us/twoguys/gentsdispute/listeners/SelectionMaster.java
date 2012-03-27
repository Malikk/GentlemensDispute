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
		selectors.put(player, 1);
	}
	
	public void removeSelector(Player player){
		selectors.remove(player);
	}
	
	public HashMap<Player, Integer> getSelectors(){
		return selectors;
	}
	
	public boolean isSelecting(Player player){
		if(selectors.containsKey(player)){
			return true;
		}else{
			return false;
		}
	}
	
	public int getSelectorInt(Player player){
		return selectors.get(player);
	}
	
	public void setSelector(Player player, int x){
		selectors.put(player, x);
	}
}
