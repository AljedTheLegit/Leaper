package com.sgtcaze.Leaper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		World world = player.getWorld();
		if (this.config.getList("Worlds.EnabledWorlds").contains(
				world.getName())) {
			if ((event.getPlayer().getGameMode() != GameMode.CREATIVE)
					&& (event.getPlayer().getLocation().getBlock()
							.getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
				event.getPlayer().setAllowFlight(true);
			}

		}
		if (this.config.getList("Worlds.DisabledWorlds").contains(
				world.getName())) {
			if ((event.getPlayer().getGameMode() != GameMode.CREATIVE)
					&& (event.getPlayer().getLocation().getBlock()
							.getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
				event.getPlayer().setAllowFlight(false);
			}

		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void FallDamage(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		Location loc = entity.getLocation();
		World world = entity.getWorld();
		if (this.config.getList("Worlds.EnabledWorlds").contains(
				world.getName())) {
			if (entity instanceof Player) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onFly(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		World world = player.getWorld();

		if (this.config.getList("Worlds.EnabledWorlds").contains(
				world.getName())) {
			if ((player.getGameMode() != GameMode.CREATIVE)) {
				event.setCancelled(true);
				player.setAllowFlight(false);
				player.setFlying(false);
				player.setVelocity(player.getLocation().getDirection()
						.multiply(1.0D * this.multiply).setY(1.0 * this.height));
				player.setFallDistance(0);

			}
		}

		if (this.config.getString("Particles.Smoke").equalsIgnoreCase("true")) {
			player.getLocation().getWorld()
					.playEffect(player.getLocation(), Effect.SMOKE, 2000);
		}

		if (this.config.getString("Particles.MobSpawnerFlames")
				.equalsIgnoreCase("true")) {
			player.getLocation()
					.getWorld()
					.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES,
							2004);
		}

		if (this.config.getString("Particles.EnderSignal").equalsIgnoreCase(
				"true")) {
			player.getLocation()
					.getWorld()
					.playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 2003);
		}

		if (this.config.getString("Particles.PotionBreak").equalsIgnoreCase(
				"true")) {
			player.getLocation()
					.getWorld()
					.playEffect(player.getLocation(), Effect.POTION_BREAK, 2002);
		}

		if (this.config.getString("Sounds.BatTakeOff").equalsIgnoreCase("true")) {
			player.getLocation()
					.getWorld()
					.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0F,
							-5.0F);
		}
		if (this.config.getString("Sounds.EnderDragonWings").equalsIgnoreCase(
				"true")) {
			player.getLocation()
					.getWorld()
					.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS,
							1.0F, -5.0F);
		}
		if (this.config.getString("Sounds.ShootArrow").equalsIgnoreCase("true")) {
			player.getLocation()
					.getWorld()
					.playSound(player.getLocation(), Sound.SHOOT_ARROW, 1.0F,
							-5.0F);
		}
	}
}