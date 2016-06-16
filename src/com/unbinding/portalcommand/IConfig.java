package com.unbinding.portalcommand;

import java.util.List;

public interface IConfig {
	public List<String> getAllowedServers();
	
	public boolean isQuickCommands();
	
	public String getMessage(String id);
}
