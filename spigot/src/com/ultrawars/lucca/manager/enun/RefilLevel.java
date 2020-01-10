package com.ultrawars.lucca.manager.enun;

public enum RefilLevel {
	
	UM("Refil I"), DOIS("Refil II"), TRES("Refil III"), FINAL("Sem eventos");
	
	
	private String display;
	
	RefilLevel(String display) {
		this.display = display;
	}
	
	public String getDisplay(){
		return display;
	}
	
	
	
}
