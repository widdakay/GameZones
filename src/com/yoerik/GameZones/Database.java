package com.yoerik.GameZones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	String filename;
	public Connection c = null; // database connection

	public Database() {

	}

	public Database(String file) {
		this.open(file);
	}

	public boolean open(String file) {
		// "Connect" to SQLite
		try {
			Class.forName("org.sqlite.JDBC");
			// Create database file if not exists
			c = DriverManager.getConnection("jdbc:sqlite:" + file, "", "");
			c.setAutoCommit(false);

			Statement st = c.createStatement();
			ResultSet result = st
					.executeQuery("SELECT version from version"); // verify
																				// database
																				// version
			Main.logger.info("Found database, version " + result.getInt("version"));
			result.close();
			st.close();
			filename = file;
			return true;

		} catch (Exception e) {
			Main.logger.severe(e.getClass().getName() + ": " + e.getMessage());

			try {
				Main.logger.info("Creating new table.");

				Statement st = c.createStatement();
				int rc = st.executeUpdate("CREATE table version (id INTEGER PRIMARY KEY AUTOINCREMENT, version int)");
				rc += st.executeUpdate("CREATE table zones (id INTEGER PRIMARY KEY AUTOINCREMENT, x INT, z INT, world VARCHAR(32), Owner VARCHAR(32), OwnerMode INT, VisitorMode INT)");
				rc += st.executeUpdate("INSERT INTO version (version) VALUES (1)");
				st.close();

				Main.logger.info("Table sucessfully created with " + rc
						+ " row(s) affected.");
				return true;

			} catch (SQLException sql) {
				try {
					Main.logger.severe("an error occured");
					sql.printStackTrace();
					if (c != null && !c.isClosed()) {
						c.rollback();
					}
					return false;
				} catch (SQLException ignore) {
					return false;
				}
			}
		}
	}

	public ResultSet query(String query) {
		try {
			Statement st = c.createStatement();
			ResultSet result = st.executeQuery(query);
			st.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int execute(String query) {
		try {
			Statement st = c.createStatement();
			int result = st.executeUpdate(query);
			st.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public boolean close() {
		// disconnect from database

		try {
			c.commit();
			c.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Main.logger.severe(e.getClass().getName() + ": " + e.getMessage());
			try {
				if (c != null && !c.isClosed()) {
					c.rollback();
					c.close();
				}
				return false;
			} catch (SQLException sql) {
				return false;
			}
		}
	}
}
