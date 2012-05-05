package us.twoguys.gentsdispute.listeners;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import us.twoguys.gentsdispute.GDScheduler;
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
		
		Player damaged = (Player) event.getEntity();
		
		//For non-Combatants
		if (event instanceof EntityDamageByEntityEvent){
			Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
			
			if (damager instanceof Player){
				if (plugin.match.hasDamageProtection((Player) damager)){
					event.setCancelled(true);
					return;
				}else if (plugin.match.hasOnlyMatchDamage((Player)damager) && !(plugin.match.hasOnlyMatchDamage(damaged))){
					event.setCancelled(true);
					return;
				}
			}
		}
		
		//For Combatants
		if (!(plugin.match.hasDamageProtection((Player)event.getEntity())) && !(plugin.match.hasOnlyMatchDamage((Player)event.getEntity()))){return;}
		
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
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String cmd = event.getMessage();
		
		if (!(cmd.equalsIgnoreCase("ready")) && !(cmd.equalsIgnoreCase("gdtp"))){
			
			if (plugin.match.hasDamageProtection(player) || plugin.match.hasOnlyMatchDamage(player)){
				plugin.sendWarningMessage(player, "You cannot use that command now.");
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		Player player = event.getPlayer();
		
		if (plugin.match.hasDamageProtection(player) || plugin.match.hasOnlyMatchDamage(player)){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		if (!(plugin.match.hasMatchData(event.getEntity()))){return;}
		
		Player player = event.getEntity();
		
		event.setKeepLevel(true);
		event.setDroppedExp(0);
		event.getDrops().clear();
		plugin.match.addDiedInMatch(player);
		
		Player[] players = plugin.match.getOtherPlayers(player);
		
		if (plugin.match.alreadyChecking(player)){
			return;
		}else{
			plugin.match.addDrawChecker(player);
			GDScheduler sche = new GDScheduler(plugin);
			sche.drawChecker(players);
		}
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){
		if (!(plugin.match.diedInMatch(event.getPlayer()))){return;}
		
		Player player = event.getPlayer();

		GDScheduler sche = new GDScheduler(plugin);
		sche.respawn(player);
		
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
