package org.worldmanager.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.worldmanager.core.listeners.BlockListener;
import org.worldmanager.core.listeners.PlayerListener;

public class CorePlugin extends JavaPlugin  {

	private ArrayList<Listener> currentListeners;
	
	public void onEnable() {
		currentListeners = new ArrayList<Listener>();
		currentListeners.add(new PlayerListener(this));
		currentListeners.add(new BlockListener(this));
		initListeners();
		getLogger().log(Level.INFO, "Enabled");
	}

	public void onDisable() {
		getLogger().log(Level.INFO, "Disable");
	}
	
	private void initListeners() {
		final PluginManager pm = getServer().getPluginManager();
		
		Iterator<Listener> loading = currentListeners.iterator();
		while(loading.hasNext()) {
			Listener loadedListener = (Listener) loading.next();
			pm.registerEvents(loadedListener, this);
		}
	}
}
