package com.sgtcaze.Leaper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

@SuppressWarnings("unused")
public class Leaper extends JavaPlugin implements Listener {

	public double height = 1.0D;
	public double multiply = 1.0D;

	public FileConfiguration config;

	public void onEnable() {
		saveDefaultConfig();
		this.config = getConfig();
		getServer().getPluginManager().registerEvents(this, this);
		this.height = getConfig().getDouble("height");
		this.multiply = getConfig().getDouble("multiply");
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		if (this.config.getString("SmootherWalk").equalsIgnoreCase("true")) {
			p.setWalkSpeed(0.25F);
		}

	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if (((e.getEntity() instanceof Player))
				&& (e.getCause() == EntityDamageEvent.DamageCause.FALL)) {
			Player p = (Player) e.getEntity();
			if (p.hasPermission("leaper.use")) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if ((event.getPlayer().hasPermission("leaper.use"))
				&& (event.getPlayer().getGameMode() != GameMode.CREATIVE)
				&& (event.getPlayer().getLocation().getBlock()
						.getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
			event.getPlayer().setAllowFlight(true);
		}
	}

	@EventHandler
	public void onFly(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		if ((player.hasPermission("leaper.use"))
				&& (player.getGameMode() != GameMode.CREATIVE)) {
			event.setCancelled(true);
			player.setAllowFlight(false);
			player.setFlying(false);
			player.setVelocity(player.getLocation().getDirection()
					.multiply(1.0D * this.multiply).setY(1.0 * this.height));
			if (this.config.getString("BatTakeOff").equalsIgnoreCase("true")) {
				player.getLocation()
						.getWorld()
						.playSound(player.getLocation(), Sound.BAT_TAKEOFF,
								1.0F, -5.0F);
			}
			if (this.config.getString("EnderDragonWings").equalsIgnoreCase(
					"true")) {
				player.getLocation()
						.getWorld()
						.playSound(player.getLocation(),
								Sound.ENDERDRAGON_WINGS, 1.0F, -5.0F);
			}
			if (this.config.getString("ShootArrow").equalsIgnoreCase("true")) {
				player.getLocation()
						.getWorld()
						.playSound(player.getLocation(), Sound.SHOOT_ARROW,
								1.0F, -5.0F);
			}
		}
	}
}
