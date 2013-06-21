package com.sgtcaze.Leaper;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Leaper extends JavaPlugin implements Listener {
	
	public void onEnable()
	  {
	    getServer().getPluginManager().registerEvents(this, this);
	  }
	
	@EventHandler
	  public void onEntityDamage(EntityDamageEvent e)
	  {
	    if (((e.getEntity() instanceof Player)) && (e.getCause() == EntityDamageEvent.DamageCause.FALL))
	      e.setCancelled(true);
	  }
	
	@EventHandler
	  public void onMove(PlayerMoveEvent event)
	  {
	    if ((event.getPlayer().hasPermission("leaper.use")) && (event.getPlayer().getGameMode() != GameMode.CREATIVE) && 
	        (event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
	         event.getPlayer().setAllowFlight(true);
	    }
	  }
	
	@EventHandler
	  public void onFly(PlayerToggleFlightEvent event)
	  {
	    Player player = event.getPlayer();
	    if ((player.hasPermission("leaper.use")) && (player.getGameMode() != GameMode.CREATIVE)) {
	      event.setCancelled(true);
	      player.setAllowFlight(false);
	      player.setFlying(false);
	      player.setVelocity(player.getLocation().getDirection().multiply(1.2D).setY(1D));
	    }
	  }
}
