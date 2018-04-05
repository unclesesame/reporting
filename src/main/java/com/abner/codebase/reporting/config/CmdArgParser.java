package com.abner.codebase.reporting.config;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

public class CmdArgParser {
	private CommandLine commandLine;
	
	public void parse(String[] args){
		List<ReportConfig> configs = Arrays.asList(ReportConfig.values());
		Options options =  new Options();
		configs.forEach(conf-> 
		options.addOption(conf.getKey(),conf.getLongKey(),true,conf.getDescription()));
		CommandLineParser parser = new DefaultParser();
		try{
			commandLine = parser.parse(options, args);
		}catch(ParseException e){
			HelpFormatter helpFormatter = new HelpFormatter();
			helpFormatter.printHelp("reporting", options);
			throw new RuntimeException();
		}
	}
	
	public String getValue(ReportConfig reportConfig){
		if(commandLine.hasOption(reportConfig.getKey())){
			return commandLine.getOptionValue(reportConfig.getKey());
		}
		return StringUtils.EMPTY;
	}
	
	public boolean hasOption(ReportConfig reportConfig){
		return StringUtils.isNotBlank(getValue(reportConfig));
	}
}
