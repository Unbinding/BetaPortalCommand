package com.unbinding.portalcommand;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ServerCommand implements CommandExecutor {
	
	private List<String> allowedServers;
	private IConfig config;
	private IRedirector redirector;
	
	public ServerCommand(IConfig config, IRedirector redirector){
		this.allowedServers = config.getAllowedServers();
		this.config = config;
		this.redirector = redirector;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(args.length == 0){
			sender.sendMessage(config.getMessage("servers-header"));
			for(String server : this.allowedServers){
				sender.sendMessage(config.getMessage("server-format").replace("{server}", server));
			}
			return true;
		}
		
		String server = null;
		for(String allowedServer : this.allowedServers){
			if(!allowedServer.equalsIgnoreCase(args[0])){
				continue;
			}
			server = allowedServer;
		}
		if(server == null) {
			return true;
		}
		if(!(sender instanceof Player)) {
			return true;
		}
		this.redirector.redirect((Player) sender, server);
		
		return true;
	}

}
