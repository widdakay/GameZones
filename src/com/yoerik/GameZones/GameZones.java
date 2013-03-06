package com.yoerik.GameZones;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.yoerik.GameZones.GameZonesCommandExecutor;

public class GameZones extends JavaPlugin {
	
	public final Logger logger = Logger.getLogger("Minecraft");
	FileConfiguration config;
	public static Map<String, Location> selectionsLeft = new HashMap<String, Location>();
	public static Map<String, Location> selectionsRight = new HashMap<String, Location>();
	
	public void onEnable() {
		
		Plugin we = Bukkit.getPluginManager().getPlugin("WorldEdit");
		if (we == null) {
			logger.severe("WorldEdit cannot be found. Please install WorldEdit before using this plugin.");
		}
		
		this.saveDefaultConfig();
		//getServer().getPluginManager().registerEvents(new CreativeSurvivalBlockListener(), this);
		
		getCommand("gz").setExecutor(new GameZonesCommandExecutor(this));	// register command
		
		PluginDescriptionFile PDF = getDescription();
        logger.info((new StringBuilder(String.valueOf(PDF.getName()))).append(" version ").append(PDF.getVersion()).append(" enabled.").toString());
	}

	public void onDisable() {
		PluginDescriptionFile PDF = getDescription();
        logger.info((new StringBuilder(String.valueOf(PDF.getName()))).append(" disabled.").toString());
	}
}