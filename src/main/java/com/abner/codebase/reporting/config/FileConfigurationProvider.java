package com.abner.codebase.reporting.config;

public class FileConfigurationProvider {
	private FileConfiguration configuration = new FileConfiguration();
	
	private static FileConfigurationProvider utils = new FileConfigurationProvider();
	
	public static FileConfiguration getFileConfiguration(){
		return utils.configuration;
	}
}
