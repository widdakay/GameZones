package com.yoerik.GameZones;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	

    public Connection c = null;	// database connection
	
	public void onEnable() {
		
		Plugin we = Bukkit.getPluginManager().getPlugin("WorldEdit");
		if (we == null) {
			logger.severe("WorldEdit cannot be found. Please install WorldEdit before using this plugin.");
		}
		
		this.saveDefaultConfig();
		//getServer().getPluginManager().registerEvents(new BlockListener(), this);
		
		getCommand("gz").setExecutor(new GameZonesCommandExecutor(this));	// register command
		
		PluginDescriptionFile PDF = getDescription();
        logger.info((new StringBuilder(String.valueOf(PDF.getName()))).append(" version ").append(PDF.getVersion()).append(" enabled.").toString());
        
        config = getConfig();
        if(config.getInt("version") != 1) {
        	getConfig().options().copyDefaults(true);
        	saveDefaultConfig();
        }
        saveDefaultConfig();
        new File(getDataFolder(), "config.yml");
        
        // "Connect" to SQLite
		try {
			Class.forName("org.sqlite.JDBC");
			// Note: /test.db is the test.db in the *current* working directory
			c = DriverManager.getConnection("jdbc:sqlite:"+getDataFolder()+"/zones.db", "", "");
			c.setAutoCommit(false);

			Statement st = c.createStatement();
			ResultSet result = st.executeQuery("SELECT version from version where id=1");		// verify database version
			logger.info("Found database, version "+result.getInt(0));

			ResultSet rs = st.executeQuery("SELECT * FROM zones where id=1");		
			while (rs.next()) {
				int i = rs.getInt(1);
				String s = rs.getString(2);
				logger.info("i=" + i + ", s=" + s);
			}
			rs.close();
			st.close();


		} catch (Exception e) {
			logger.severe(e.getClass().getName() + ": " + e.getMessage());
			logger.severe(e.getClass().getName() + ": " + e.getMessage());
			try {
				logger.info("Creating new table.");
				
				Statement st = c.createStatement();
				int rc = st.executeUpdate("CREATE table version (id INTEGER PRIMARY KEY AUTOINCREMENT, version int)");
				rc += st.executeUpdate("CREATE table zones (id INTEGER PRIMARY KEY AUTOINCREMENT, x INT, z INT, Owner VARCHAR(32), OwnerMode INT, VisitorMode INT)");
				st.close();
				
				logger.info("Table sucessfully created with "+rc+" row(s) affected.");
				
			} catch (SQLException sql) {
				try {
					if (c != null && !c.isClosed()) {
						c.rollback();
					}
				} catch (SQLException ignore) {
					// ignore
				}
			}
		}
        
        
	}

	public void onDisable() {
		PluginDescriptionFile PDF = getDescription();
        logger.info((new StringBuilder(String.valueOf(PDF.getName()))).append(" disabled.").toString());
        
        // disconnect from database
		try {
			c.commit();
			c.close();
		} catch (Exception e) {
			logger.severe(e.getClass().getName() + ": " + e.getMessage());
			try {
				if (c != null && !c.isClosed()) {
					c.rollback();
					c.close();
				}
			} catch (SQLException sql) {
				// ignore
			}
		}
	}
}