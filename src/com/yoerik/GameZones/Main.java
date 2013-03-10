package com.yoerik.GameZones;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static final Logger logger = Logger.getLogger("Minecraft");
	static FileConfiguration config;
	public static Map<String, Location> selectionsLeft = new HashMap<String, Location>();
	public static Map<String, Location> selectionsRight = new HashMap<String, Location>();

	public static Database db;

	public void onEnable() {

		Plugin we = Bukkit.getPluginManager().getPlugin("WorldEdit");
		if (we == null) {
			logger.severe("WorldEdit cannot be found. Please install WorldEdit before using this plugin.");
		}

		this.saveDefaultConfig();
		// getServer().getPluginManager().registerEvents(new BlockListener(),
		// this);

		getCommand("gz").setExecutor(new GameZonesCommandExecutor(this)); // register
																			// command

		PluginDescriptionFile PDF = getDescription();

		config = getConfig();
		if (config.getInt("version") != 1) {
			getConfig().options().copyDefaults(true);
			saveDefaultConfig();
		}
		saveDefaultConfig();
		new File(getDataFolder(), "config.yml");

		db = new Database(); // init database
		db.open(getDataFolder() + "/zones.db");

		logger.info((new StringBuilder(String.valueOf(PDF.getName())))
				.append(" version ").append(PDF.getVersion())
				.append(" enabled.").toString());
	}

	public void onDisable() {
		PluginDescriptionFile PDF = getDescription();
		logger.info((new StringBuilder(String.valueOf(PDF.getName()))).append(
				" disabled.").toString());

		db.close();
	}
}