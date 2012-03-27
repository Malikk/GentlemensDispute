package us.twoguys.gentsdispute.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

import us.twoguys.gentsdispute.GentlemensDispute;

public class PlayerListener implements Listener{

	GentlemensDispute plugin;
	
	public PlayerListener(GentlemensDispute instance){
		plugin = instance;
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event){
		if (event.isCancelled()){return;}
		if (!(event.getEntity() instanceof Player)){return;}
		if (!(plugin.tempData.hasDamageProtection((Player)event.getEntity())) && !(plugin.tempData.hasOnlyMatchDamage((Player)event.getEntity()))){return;}
		
		Player damaged = (Player) event.getEntity();
		Player[] players;
		String mode;
		
		try{
			players = plugin.tempData.getOtherPlayers(damaged);
			mode = plugin.tempData.getMode(players);
		}catch(Exception e){
			return;
		}
		
		
		if (event instanceof EntityDamageByEntityEvent){
			playerDamagedByEntity(damaged, mode, (EntityDamageByEntityEvent) event);
		}else{
			playerDamaged(damaged, mode, event);
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		if (!(plugin.tempData.isInBattle(event.getEntity()))){return;}
		
		Player player = event.getEntity();
		
		event.setKeepLevel(true);
		event.getDrops().clear();
	}
	
	public void playerDamaged(Player damaged, String mode, EntityDamageEvent event){
		DamageCause cause = event.getCause();
		String type = null;
		if (cause.equals(DamageCause.LAVA) || cause.equals(DamageCause.FIRE) || cause.equals(DamageCause.FIRE_TICK)){
			type = "LavaAndFire";
		}else if (cause.equals(DamageCause.BLOCK_EXPLOSION) || cause.equals(DamageCause.ENTITY_EXPLOSION)){
			type = "Explosions";
		}else if (cause.equals(DamageCause.CONTACT)){
			type = "Cactus";
		}else if (cause.equals(DamageCause.FALL)){
			type = "Fall";
		}else{
			event.setCancelled(true);
			return;
		}
		
		if (plugin.tempData.hasDamageProtection(damaged)){
			event.setCancelled(true);
		}else if (plugin.tempData.hasOnlyMatchDamage(damaged)){
			if (plugin.config.damageTypeAllowed(mode, type)){return;}
			event.setCancelled(true);
		}
	}
	
	public void playerDamagedByEntity(Player damaged, String mode, EntityDamageByEntityEvent event){
		Entity damager = null;
		
		if (event.getDamager() instanceof Projectile){
			Entity shooter = ((Projectile) event.getDamager()).getShooter();
			damager = shooter;
		}else{
			damager = event.getDamager();
		}
		
		if (plugin.tempData.hasDamageProtection(damaged)){
			event.setCancelled(true);
		}else if (plugin.tempData.hasOnlyMatchDamage(damaged)){
			if (damager instanceof Player){
				if (plugin.tempData.isOtherPlayer(damaged, (Player)damager)){return;}
				event.setCancelled(true);
			}else{
				if (plugin.config.damageTypeAllowed(mode, "Mobs")){return;}
				event.setCancelled(true);
			}
		}
	}
	
}
