package us.twoguys.gentsdispute.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import us.twoguys.gentsdispute.GentlemensDispute;

public class PlayerListener implements Listener{

	GentlemensDispute plugin;
	
	public PlayerListener(GentlemensDispute instance){
		plugin = instance;
	}
	
	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event){
		if (event.isCancelled()){return;}
		if (!(event.getEntity() instanceof Player)){return;}
		
		Player damaged = (Player) event.getEntity();
		Entity damager = null;
		
		if (event.getDamager() instanceof Projectile){
			Entity shooter = ((Projectile) event.getDamager()).getShooter();
			damager = shooter;
		}else{
			damager = event.getDamager();
		}
		
		//Add Config options for non-player damages
	}
	
}
