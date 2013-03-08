package com.yoerik.GameZones;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private GameZones plugin; // pointer to main class

	public boolean open(String filename) {
		// "Connect" to SQLite
		try {
			Class.forName("org.sqlite.JDBC");
			// Note: /test.db is the test.db in the *current* working directory
			plugin.c = DriverManager.getConnection("jdbc:sqlite:" + filename,
					"", "");
			plugin.c.setAutoCommit(false);

			Statement st = plugin.c.createStatement();
			ResultSet result = st
					.executeQuery("SELECT version from version where id=1"); // verify
																				// database
																				// version
			plugin.logger.info("Found database, version " + result.getInt(0));

			ResultSet rs = st.executeQuery("SELECT * FROM zones where id=1");
			while (rs.next()) {
				int i = rs.getInt(1);
				String s = rs.getString(2);
				plugin.logger.info("i=" + i + ", s=" + s);
			}
			rs.close();
			st.close();
			return true;

		} catch (Exception e) {
			plugin.logger
					.severe(e.getClass().getName() + ": " + e.getMessage());
			plugin.logger
					.severe(e.getClass().getName() + ": " + e.getMessage());
			try {
				plugin.logger.info("Creating new table.");

				Statement st = plugin.c.createStatement();
				int rc = st
						.executeUpdate("CREATE table version (id INTEGER PRIMARY KEY AUTOINCREMENT, version int)");
				rc += st.executeUpdate("CREATE table zones (id INTEGER PRIMARY KEY AUTOINCREMENT, x INT, z INT, Owner VARCHAR(32), OwnerMode INT, VisitorMode INT)");
				st.close();

				plugin.logger.info("Table sucessfully created with " + rc
						+ " row(s) affected.");
				return true;

			} catch (SQLException sql) {
				try {
					if (plugin.c != null && !plugin.c.isClosed()) {
						plugin.c.rollback();
					}
					return false;
				} catch (SQLException ignore) {
					return false;
				}
			}
		}
	}
	
	public boolean close() {
		try {
			plugin.c.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}
