package com.abner.codebase.reporting.infra;

public enum Symbol {
	QUOTE("\""),NULL("");
	
	private final String symbolRepresentation;
	
	Symbol(String symbolRepresentation) {
		this.symbolRepresentation = symbolRepresentation;
	}
	
	public String asString(){
		return symbolRepresentation;
	}
	
	public char asChar(){
		if(symbolRepresentation.length()!=1){
			throw new RuntimeException("unsupported type" + symbolRepresentation);
		}
		return symbolRepresentation.charAt(0);
	}
}
