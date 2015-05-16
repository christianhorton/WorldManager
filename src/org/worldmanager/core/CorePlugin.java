package org.worldmanager.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.worldmanager.core.listeners.BlockListener;
import org.worldmanager.core.listeners.PlayerListener;

public class CorePlugin extends JavaPlugin  {

	/* Files */
	private File configFile;
	private File worldFile;

	/* Configurations */
	private FileConfiguration configConfig;
	private FileConfiguration worldConfig;

	/* Lists */
	public List<String> worldsCreative = new ArrayList<String>();
	public List<String> worldsSurvival = new ArrayList<String>();
	private ArrayList<Listener> currentListeners;

	public void onEnable() {
		currentListeners = new ArrayList<Listener>();
		currentListeners.add(new PlayerListener(this));
		currentListeners.add(new BlockListener(this));
		initListeners();
		initConfigs();
		initWorldInventories();
		loadConfigValues();
		getLogger().log(Level.INFO, "Enabled");

		if(isSurvival("creative")) {
			getLogger().log(Level.INFO, "This world is survival");
		} else {
			getLogger().log(Level.INFO, "This world is not survival");
		}

	}

	public void onDisable() {
		getLogger().log(Level.INFO, "Disable");
	}

	/** 
	 * Init all the listeners
	 */
	private void initListeners() {
		final PluginManager pm = getServer().getPluginManager();

		Iterator<Listener> loading = currentListeners.iterator();
		while(loading.hasNext()) {
			Listener loadedListener = (Listener) loading.next();
			pm.registerEvents(loadedListener, this);
		}
	}

	/**
	 * Init the configuration files
	 */
	private void initConfigs() {
		configFile = new File(getDataFolder(), "config.yml");
		worldFile = new File(getDataFolder(), "worlds.yml");
		configConfig = new YamlConfiguration();
		worldConfig = new YamlConfiguration();
		try {
			this.configConfig.load(configFile);
			getLogger().log(Level.INFO, "Loaded config.yml");
			this.worldConfig.load(worldFile);
			getLogger().log(Level.INFO, "Loaded worlds.yml");
		} catch (InvalidConfigurationException e) {
			getLogger().log(Level.WARNING, "Could not load configuration files.");
			e.printStackTrace();
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	/**
	 * Create the world directories for player inventories
	 */
	private void initWorldInventories() {
		ArrayList<World> worldsCurrent = (ArrayList<World>) getServer().getWorlds();
		for(int world = 0; world < worldsCurrent.size(); world++) {
			World currentWorld = worldsCurrent.get(world);
			File worldDir = new File(getDataFolder() + "/" + currentWorld.getName());
			if(!worldDir.exists()) {
				getLogger().log(Level.INFO, "Created world directory '" + currentWorld.getName() + "'");
				worldDir.mkdir();
			}
		}	
	}

	/**
	 * Loads configuration files
	 */
	private void loadConfigValues() {
		this.worldsCreative = worldConfig.getStringList("creative");
		this.worldsSurvival = worldConfig.getStringList("survival");
	}

	/**
	 * Checks if the world is a Survival gamemode world.
	 * @param worldName
	 * @return Boolean
	 */
	public boolean isSurvival(String worldName) {
		if(worldsSurvival.contains(worldName)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if the world is a Creative gamemode world.
	 * @param worldName
	 * @return Boolean
	 */
	public boolean isCreative(String worldName) {
		if(worldsCreative.contains(worldName)) {
			return true;
		}
		return false;
	}
	

	/**
	 * Check the world for the gamemode it should use
	 * @param player
	 * @param worldName
	 */
	public void checkWorld(Player player, String worldName) {
		if(isSurvival(worldName)) { player.setGameMode(GameMode.SURVIVAL); }
		if(isCreative(worldName)) { player.setGameMode(GameMode.CREATIVE); }	
	}

	public void loadInvetory(Player player) {
		PlayerInventory inventory = player.getInventory();
	}
}
