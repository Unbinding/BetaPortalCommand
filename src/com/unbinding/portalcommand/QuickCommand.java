package com.unbinding.portalcommand;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class QuickCommand implements Listener {
	
	private List<String> allowedServers;
	private IRedirector redirector;
	
	public QuickCommand(IConfig config, IRedirector redirector){
		this.allowedServers = config.getAllowedServers();
		this.redirector = redirector;
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e){
		String command = e.getMessage().substring(1);
		String server = null;
		for(String allowedServer : allowedServers){
			if(!allowedServer.equalsIgnoreCase(command)){
				continue;
			}
			server = allowedServer;
		}
		if(server == null){
			return;
		}
		this.redirector.redirect(e.getPlayer(), server);
		e.setCancelled(true);
	}
}
