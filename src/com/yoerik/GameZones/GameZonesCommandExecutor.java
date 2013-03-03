package com.yoerik.GameZones;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.yoerik.GameZones.GameZones;

public class GameZonesCommandExecutor implements CommandExecutor {

	private GameZones plugin; // pointer to your main class, unrequired

	public GameZonesCommandExecutor(GameZones plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (cmd.getName().equalsIgnoreCase("gz")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("help")) {	// if help command
					printHelp(sender);
					return true;
				}
				
				if (args[0].equalsIgnoreCase("claim")) {	// if claim command
					printHelp(sender);
					return true;
				}
				
				if (args[0].equalsIgnoreCase("set")) {	// if set command
					printHelp(sender);
					return true;
				}
				
			}
			
			sender.sendMessage("GameZones main command.  Use /gz help for help.");
		}
		return false;
	}
	
	private void printHelp(CommandSender sender) {
		sender.sendMessage("GameZones Help:");
		sender.sendMessage("/gz claim  - claims the plot you are in");
		sender.sendMessage("/gz set    - plot settings");
	}

}
