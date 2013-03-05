package com.yoerik.GameZones;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.yoerik.GameZones.GameZonesCommandExecutor;

public class GameZones extends JavaPlugin {
	
	public final Logger logger = Logger.getLogger("Minecraft");
	FileConfiguration config;
	public static int toolId;
	public static Map<String, Location> selectionsLeft = new HashMap<String, Location>();
	public static Map<String, Location> selectionsRight = new HashMap<String, Location>();
	
	public void onEnable() {
		
		this.saveDefaultConfig();
		//getServer().getPluginManager().registerEvents(new CreativeSurvivalBlockListener(), this);
		getServer().getPluginManager().registerEvents(new ClickListener(), this);
		
		getCommand("gz").setExecutor(new GameZonesCommandExecutor(this));	// register command
		
		PluginDescriptionFile PDF = getDescription();
        logger.info((new StringBuilder(String.valueOf(PDF.getName()))).append(" version ").append(PDF.getVersion()).append(" enabled.").toString());
        
        toolId = config.getInt("gztool"); //Get item ID for selection tool
	}

	public void onDisable() {
		PluginDescriptionFile PDF = getDescription();
        logger.info((new StringBuilder(String.valueOf(PDF.getName()))).append(" disabled.").toString());
	}
}