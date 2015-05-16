package org.worldmanager.core.listeners;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.worldmanager.core.CorePlugin;

public class PlayerListener implements Listener {


	public PlayerListener(final CorePlugin corePlugin) {
	}
	
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player interactPlayer = event.getPlayer();
		World worldIn = interactPlayer.getWorld();
		if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(worldIn.getName() == "world" && !interactPlayer.isOp()) {
				event.setCancelled(true);
			}
		}
				
	}

}
