package com.unbinding.portalcommand;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import lilypad.client.connect.api.Connect;
import lilypad.client.connect.api.request.RequestException;
import lilypad.client.connect.api.request.impl.RedirectRequest;
import lilypad.client.connect.api.result.FutureResultListener;
import lilypad.client.connect.api.result.StatusCode;
import lilypad.client.connect.api.result.impl.RedirectResult;
import net.md_5.bungee.api.ChatColor;

public class PortalCommand extends JavaPlugin implements IConfig, IRedirector {
	
	@Override
	public void onLoad(){
		super.getConfig().options().copyDefaults(true);
		super.saveConfig();
		super.reloadConfig();
	}
	
	@Override
	public void onEnable(){
		super.getCommand("server").setExecutor(new ServerCommand(this, this));
		if(this.isQuickCommands()){
			super.getServer().getPluginManager().registerEvents(new QuickCommand(this, this), this);
		}
	}
	
	public Connect getConnect(){
		return super.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
	}
	
	public List<String> getAllowedServers(){
		return super.getConfig().getStringList("allowed-servers");
	}
	
	public boolean isQuickCommands(){
		return super.getConfig().getBoolean("quick-commands");
	}
	
	public String getMessage(String id){
		return ChatColor.translateAlternateColorCodes('&', super.getConfig().getString("messages." + id));
	}
	
	public void redirect(final Player player, String server){
		Connect connect = this.getConnect();
		if(connect.getSettings().getUsername().equals(server)){
			return;
		}
		try{
			connect.request(new RedirectRequest(server, player.getName())).registerListener(new FutureResultListener<RedirectResult>() {
				
				@Override
				public void onResult(RedirectResult redirectResult) {
					if(redirectResult.getStatusCode() == StatusCode.SUCCESS){
						return;
					}
					
					player.sendMessage(getMessage("server-offline"));
					
				}
			});
		}catch(RequestException e){
			//
		}
	}

}
