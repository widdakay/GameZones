package com.yoerik.GameZones;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.yoerik.GameZones.GameZones;
import com.yoerik.GameZones.GameZone;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class GameZonesCommandExecutor implements CommandExecutor {

	private GameZones plugin; // pointer to your main class, unrequired

	public GameZonesCommandExecutor(GameZones plugin) {
		this.plugin = plugin;
	}

	public static Selection getWorldEditSelection(Player ply) {
		Plugin we = Bukkit.getPluginManager().getPlugin("WorldEdit");
		if (we != null && we instanceof WorldEditPlugin) {
			return ((WorldEditPlugin) we).getSelection(ply);
		}
		return null;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (cmd.getName().equalsIgnoreCase("gz")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("help")) { // if help command
					printHelp(sender);
					return true;
				}

				if (args[0].equalsIgnoreCase("claim")) { // if claim command
					if (!(sender instanceof Player)) {
						sender.sendMessage("This command can only be run by a player.");
					} else {
						Player player = (Player) sender;
						return claimPlot(player);
					}
					return true;
				}

				if (args[0].equalsIgnoreCase("set")) { // if set command
					Selection sel = getWorldEditSelection((Player) sender);
					if (sel == null) {
						sender.sendMessage("You do not have a complete selection!");
						return true; // or false?
					}
					if (sel.getMaximumPoint().getWorld() != sel
							.getMinimumPoint().getWorld()) {
						sender.sendMessage("You do not have a complete selection!");
						return true; // or false?
					} else {
						double x1 = sel.getMaximumPoint().getX();
						double x2 = sel.getMinimumPoint().getX();
						double z1 = sel.getMaximumPoint().getZ();
						double z2 = sel.getMinimumPoint().getZ();
						//Save points here
						return true;
					}
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

		if (!(zone.isPlot())) {
			player.sendMessage("You are not in a zone!");
		} else {

			if (zone.isClaimed()) {
				player.sendMessage("This zone is already claimed by "
						+ zone.getOwner());
			} else {
				return zone.claim(player);
			}
			return true;
		}
		return false;
	}

	private void printHelp(CommandSender sender) {
		sender.sendMessage("GameZones Help:");
		sender.sendMessage("/gz claim  - claims the plot you are in");
		sender.sendMessage("/gz set    - plot settings");
	}

}
