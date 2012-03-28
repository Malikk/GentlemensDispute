package us.twoguys.gentsdispute.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

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
		if (!(plugin.match.hasDamageProtection((Player)event.getEntity())) && !(plugin.match.hasOnlyMatchDamage((Player)event.getEntity()))){return;}
		
		Player damaged = (Player) event.getEntity();
		Player[] players;
		String mode;
		
		try{
			players = plugin.match.getOtherPlayers(damaged);
			mode = plugin.match.getMode(players);
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
		if (!(plugin.match.isInBattle(event.getEntity()))){return;}
		
		event.setKeepLevel(true);
		event.getDrops().clear();
		plugin.match.addDiedInMatch(event.getEntity());
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){
		if (!(plugin.match.diedInMatch(event.getPlayer()))){return;}
		
		Player player = event.getPlayer();
		
		player.teleport(plugin.match.getDiedInMatchLocation(player));
		plugin.match.removeDiedInMatch(player);
		
		plugin.match.loadInventory(player);
		plugin.match.removeOldInventory(player);
	}
	
	public boolean playerIsOnFire(Player player){
		if (player.getFireTicks() > 0){
			return true;
		}else{
			return false;
		}
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
		
		if (plugin.match.hasDamageProtection(damaged)){
			event.setCancelled(true);
		}else if (plugin.match.hasOnlyMatchDamage(damaged)){
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
		
		if (plugin.match.hasDamageProtection(damaged)){
			event.setCancelled(true);
		}else if (plugin.match.hasOnlyMatchDamage(damaged)){
			if (damager instanceof Player){
				if (plugin.match.isOtherPlayer(damaged, (Player)damager)){return;}
				event.setCancelled(true);
			}else{
				if (plugin.config.damageTypeAllowed(mode, "Mobs")){return;}
				event.setCancelled(true);
			}
		}
	}
	
}
