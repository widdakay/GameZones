package com.yoerik.GameZones;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import java.sql.*;

public class GameZone {
	long xPos;
	long zPos;
	World world;
	Player owner;
	
	private GameZones plugin; // pointer to main class

	public GameZone(long x, long z, World worldPos) {
		setPos(x, z, worldPos);
	}

	public void setPos(long x, long z, World worldPos) {
		xPos = x;
		zPos = z;
		world = worldPos;
	}

	public boolean isClaimed() {
		try {
			Statement st = plugin.c.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM zones WHERE x="+xPos+"AND z="+zPos);		// verify database version
			
			if (rs.next()) {
				Array results = rs.getArray(0);
				if (results.getArray().equals("claimed")) {
					return false;
				}
				else {
					return true;
				}
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			plugin.logger.severe(e.getClass().getName() + ": " + e.getMessage());
			try {
				if (plugin.c != null && !plugin.c.isClosed()) {
					plugin.c.rollback();
				}
			} catch (SQLException sql) {
				// ignore
			}
		}
		return false;
	}

	public boolean isPlot() {
		// check if world is in config file
		if (plugin.config.getStringList("worlds").contains(world.getName())) {
			return true;
		}
		return false;
	}

	/*public int getPlotId() {
		long[] x1;
		long[] z1;
		long[] x2;
		long[] z2; // Arrays of the corners of the plots, 1 being larger corner
					// and 2 being smaller
		World[] world;
		int amnt = 0; // Amount of total corners/plots there are
		x1 = new long[amnt];
		z1 = new long[amnt];
		x2 = new long[amnt];
		z2 = new long[amnt];
		world = new World[amnt];
		// loop here to fill the arrays with the actual corners/worlds
		for (int i = 0; i < amnt; i++) {
			if (this.world == world[i]) {
				if (xPos <= x1[i] && xPos >= x2[i]) {
					if (zPos <= z1[i] && zPos >= z2[i]) {
						return i;
					}
				}
			}
		}
		return -1;
	}*/

	public boolean isClaimable() {
		if (this.isPlot() && !(this.isClaimed())) {
			return true;
		} else if (!(this.isPlot()) || this.isClaimed()) {
			return false;
		}
		return false;
	}

	public String getOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean claim(Player player) {
		// TODO Auto-generated method stub
		if (!(this.isClaimable())) {
			return false;
		}
		return true;

	}

	public GameMode getGamemode() {
		if (!(this.isPlot())) {
			return null;
		} else {
			GameMode gm;
			gm = null; // Get GameMode here
			return gm;
		}
	}

}
