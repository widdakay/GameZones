package com.yoerik.GameZones;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.yoerik.GameZones.GameZones;
import com.yoerik.GameZones.GameZone;

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
					if (!(sender instanceof Player)) {
						sender.sendMessage("This command can only be run by a player.");
					} else {
						Player player = (Player) sender;
						return claimPlot(player);
					}
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
	
	private boolean claimPlot(Player player) {
		Location playerLocation = player.getLocation();
		World world = playerLocation.getWorld();
		long xPos = playerLocation.getBlockX();
		long zPos = playerLocation.getBlockZ();
		
		GameZone zone = new GameZone(xPos, zPos, world);
		
		if (zone.isClaimed()) {
			player.sendMessage("This zone is already claimed by " + zone.getOwner());
		}
		else {
			return zone.claim(player);
		}
		return true;
	}
	private void printHelp(CommandSender sender) {
		sender.sendMessage("GameZones Help:");
		sender.sendMessage("/gz claim  - claims the plot you are in");
		sender.sendMessage("/gz set    - plot settings");
	}

}
