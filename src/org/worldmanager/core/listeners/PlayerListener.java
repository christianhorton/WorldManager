package org.worldmanager.core.listeners;

import java.io.File;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.worldmanager.core.CorePlugin;

public class PlayerListener implements Listener {

	private CorePlugin plugin;

	public PlayerListener(final CorePlugin corePlugin) {
		this.plugin = corePlugin;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player interactPlayer = event.getPlayer();
		World worldIn = interactPlayer.getWorld();
		if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(worldIn.getName() == "world" && !interactPlayer.isOp()) {
				event.setCancelled(true);
			}
		}			
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		String worldName = event.getPlayer().getWorld().getName();
		if(!event.getPlayer().isOp()) {
			plugin.checkWorld(event.getPlayer(), worldName);
		}
		
		File playerWorldInventory = new File(plugin.getDataFolder() + "/" + worldName + "/" + event.getPlayer().getUniqueId().toString());
		if(playerWorldInventory.exists()) {
			plugin.loadInvetory(event.getPlayer());
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		World world = event.getPlayer().getWorld();
		Inventory inventory = player.getInventory();

	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		String worldName = event.getTo().getWorld().getName();
		if(!event.getPlayer().isOp()) {
			plugin.checkWorld(event.getPlayer(), worldName);
		}
	}


}

