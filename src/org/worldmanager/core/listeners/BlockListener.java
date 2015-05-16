package org.worldmanager.core.listeners;

import org.bukkit.event.Listener;
import org.worldmanager.core.CorePlugin;

public class BlockListener implements Listener {

	private final CorePlugin plugin;

	public BlockListener(final CorePlugin corePlugin) {
		this.plugin = corePlugin;
	}

}
