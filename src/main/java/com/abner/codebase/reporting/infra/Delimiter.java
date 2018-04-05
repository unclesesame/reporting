package com.abner.codebase.reporting.infra;

public enum Delimiter {
	COMMA(","),
	PIPELINE("|"),
	UNKNOWN("unknown");
	
	private final String value;
	
	Delimiter(String value) {
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public char asChar(){
		if(getValue().length() > 1){
			throw new RuntimeException("unsupported delimiter type "+getValue());
		}
		return getValue().charAt(0);
	}
	
	public static Delimiter parse(String value){
		switch(value) {
		case ",":
			return COMMA;
		case "|":
			return PIPELINE;
		default:
			return UNKNOWN;
		}
	}
}
