package org.worldmanager.core.listeners;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
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
		checkWorld(event.getPlayer(), worldName);
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		String worldName = event.getTo().getWorld().getName();
		checkWorld(event.getPlayer(), worldName);
	}
	
	public void checkWorld(Player player, String worldName) {
		if(plugin.isSurvival(worldName)) { player.setGameMode(GameMode.SURVIVAL); }
		if(plugin.isCreative(worldName)) { player.setGameMode(GameMode.CREATIVE); }	
	}

}

