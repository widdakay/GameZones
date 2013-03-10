package com.yoerik.GameZones;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import java.sql.*;

public class GameZone {
	long xPos;
	long zPos;
	World world;
	Player owner;
	long id = -1;

	public GameZone(long x, long z, World worldPos) {
		setPos(x, z, worldPos);
		this.loadData();
	}

	public void setPos(long x, long z, World worldPos) {
		xPos = x / 4;
		zPos = z / 4;
		world = worldPos;
	}

	public void setPos(Location loc) {
		this.setPos(loc.getBlockX(), loc.getBlockZ(), loc.getWorld());
	}

	public boolean isClaimed() {
		if (this.getOwner() != null) {
			return true;
		}
		return false;
	}

	public boolean isPlot() {
		// check if world is in config file
		if (Main.config.getStringList("worlds").contains(this.world.getName())) {
			return true;
		}
		return false;
	}

	public long getPlotId() {
		return id;
	}

	public boolean isClaimable() {
		if (this.isPlot() && !(this.isClaimed())) {
			return true;
		} else if (!(this.isPlot()) || this.isClaimed()) {
			return false;
		}
		return false;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player player) {
		owner = player;
	}

	public boolean claim(Player player) {
		if (!(this.isClaimable())) {
			return false;
		} else {
			this.setOwner(player);

			return false;
		}

	}

	public boolean saveData() {
		String query = "INSERT INTO zones (z, x, world, Owner) VALUES ("
				+ this.xPos + ", " + this.zPos + ", '" + this.world.getName()
				+ "', '" + this.getOwner().getName() + "')";
		// TODO escape username to avoid sql injection

		if (Main.db.execute(query) < 1) {
			return false;
		}

		return false;
	}

	public boolean loadData() {
		try {
			// load owner
			String queryString = "SELECT * FROM zones WHERE x=" + xPos
					+ " AND z=" + zPos;
			ResultSet rs = Main.db.query(queryString);

			owner = Bukkit.getOfflinePlayer(rs.getString("Owner")).getPlayer();
			id = rs.getLong("id");

		} catch (SQLException e) {
			saveData(); // create row in table for the data;
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
