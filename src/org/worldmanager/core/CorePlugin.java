package org.worldmanager.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.worldmanager.core.listeners.BlockListener;
import org.worldmanager.core.listeners.PlayerListener;

public class CorePlugin extends JavaPlugin  {

	private ArrayList<Listener> currentListeners;

	/* Files */
	private File configFile;
	private File worldFile;

	/* Configurations */
	private FileConfiguration configConfig;
	private FileConfiguration worldConfig;

	public void onEnable() {
		currentListeners = new ArrayList<Listener>();
		currentListeners.add(new PlayerListener(this));
		currentListeners.add(new BlockListener(this));
		initListeners();
		initConfigs();
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


	private void initConfigs() {
		configFile = new File(getDataFolder(), "config.yml");
		worldFile = new File(getDataFolder(), "worlds.yml");

		try {
			if(!configFile.exists()) {
				configFile.createNewFile();
				getLogger().log(Level.INFO, "Created config.yml");
			}
			if(!worldFile.exists()) { 
				worldFile.createNewFile();
				getLogger().log(Level.INFO, "Created worlds.yml");
			}
		} catch (IOException e) {
			getLogger().log(Level.WARNING, "Could not create configuration files.");
			e.printStackTrace();
		} 


	}
}
