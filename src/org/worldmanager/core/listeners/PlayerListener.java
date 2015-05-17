package org.worldmanager.core.listeners;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

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
		if(worldIn.getName() == plugin.noBreakWorld)
//		if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(!interactPlayer.isOp()) {
				event.setCancelled(true);
			}
//		}			
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		String worldName = event.getPlayer().getWorld().getName();
		if(!event.getPlayer().isOp()) {
			plugin.checkWorld(event.getPlayer(), worldName);
		}

		if(playerHasInventory(worldName, event.getPlayer())) {

		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		World world = event.getPlayer().getWorld();
		plugin.saveInventory(player, world.getName());
	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		String worldNameTo = event.getTo().getWorld().getName();
		String worldNameFrom = event.getFrom().getWorld().getName();
		
		if(!plugin.getPlayerInventorySave(event.getPlayer(), worldNameTo).exists()) {
			try {
				plugin.getPlayerInventorySave(event.getPlayer(), worldNameTo).createNewFile();
			} catch (IOException e) { e.printStackTrace(); }
		}
		
		if(!plugin.getPlayerInventorySave(event.getPlayer(), worldNameFrom).exists()) {
			try {
				plugin.getPlayerInventorySave(event.getPlayer(), worldNameFrom).createNewFile();
			} catch (IOException e) { e.printStackTrace(); }
		}
		this.plugin.saveInventory(event.getPlayer(), worldNameFrom);
		this.plugin.loadInvetory(event.getPlayer(), worldNameTo);
	}


	private boolean playerHasInventory(String worldName, Player player) {
		File playerWorldInventory = new File(plugin.getDataFolder() + "/" + worldName + "/" + player.getUniqueId().toString() +".yml");
		try {
			playerWorldInventory.createNewFile();
			plugin.getLogger().log(Level.INFO,  "Created player: " + playerWorldInventory.getPath());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}

