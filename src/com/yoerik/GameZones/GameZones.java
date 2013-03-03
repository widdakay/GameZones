package com.yoerik.GameZones;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.yoerik.GameZones.GameZonesCommandExecutor;

public class GameZones extends JavaPlugin {
	public final Logger logger = Logger.getLogger("Minecraft");
	public void onEnable() {
		
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