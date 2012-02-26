package com.nuclearw.ingameconsole;

import java.util.HashSet;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class InGameConsole extends JavaPlugin {
	HashSet<Player> listeners = new HashSet<Player>();

	@Override
	public void onEnable() {
		Logger.getLogger("Minecraft").addHandler(new ConsoleLoggerHandler(this));

		getLogger().info("Finished Loading " + getDescription().getFullName());
	}

	@Override
	public void onDisable() {
		getLogger().info("Finished Unloading "+getDescription().getFullName());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("igc")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("Console cannot use this command.");
				return true;
			}

			Player player = (Player) sender;
			if(!player.hasPermission("ingameconsole.use")) {
				player.sendMessage("You do not have permission to use this command.");
				return true;
			}

			if(args.length > 1) return false;

			// Toggle
			if(args.length == 0) {
				if(listeners.contains(player)) {
					listeners.remove(player);
				} else {
					listeners.add(player);
				}
				return true;
			}

			// On or off explicitly stated
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("on")) {
					if(!listeners.contains(player)) {
						listeners.add(player);
					}
					return true;
				} else if(args[0].equalsIgnoreCase("off")) {
					if(listeners.contains(player)) {
						listeners.remove(player);
					}
					return true;
				} else {
					return false;
				}
			}
		}
		return true;
	}

	protected void sendToListeners(String message) {
		for(Player player : listeners) {
			player.sendMessage(message);
		}
	}
}
