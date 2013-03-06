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
					if(!(GameZones.selectionsLeft.containsKey(sender.getName())) 
							|| !(GameZones.selectionsRight.containsKey(sender.getName()))){
						sender.sendMessage("You do not have a complete selection!");
						return true; // or false?
					} else if(GameZones.selectionsRight.containsKey(sender.getName()) 
							&& GameZones.selectionsLeft.containsKey(sender.getName())){
						Location loc1 = GameZones.selectionsRight.get(sender.getName());
						Location loc2 = GameZones.selectionsLeft.get(sender.getName());
						if(loc1.getWorld() != loc2.getWorld()){
							sender.sendMessage("You do not have a complete selection!");
							return true; // or false?
						}
						double x1 = loc1.getX();
						double z1 = loc1.getZ();
						double x2 = loc2.getX();
						double z2 = loc2.getZ();
						World world = loc1.getWorld();
						if(x1 < x2){
							double tmp = x1;
							x1 = x2;
							x2 = tmp;
						}
						if(z1 < z2){
							double tmp = z1;
							z1 = z2;
							z2 = tmp;
							// store x's, z's & world
						}
					}
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

		if (!(zone.isClaimable())) {
			player.sendMessage("You are not in a zone!");
		} else {

			if (zone.isClaimed() && (zone.getOwner() != player.getName())) {
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
