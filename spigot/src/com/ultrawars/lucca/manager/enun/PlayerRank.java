package com.ultrawars.lucca.manager.enun;

import java.util.UUID;

import com.ultrawars.lucca.storage.PlayerMySQL;

public enum PlayerRank {
	
	DIRETOR("DIRETOR", '9', true), ADMINSTRADOR("ADM", 'c', true), MODERADOR("MOD", '5', true), TITAN("TITAN", 'e', true), NORMAL("NORMAL", '7', false);
	
	
	private String display;
	private char color;
	private boolean bold;
	
	PlayerRank(String display, char color, boolean bold) {
		this.display = display;
		this.color = color;
		this.bold = bold;
	}
	
	public String getDisplay(){
		return display;
	}
	
	public String getColor(){
		return bold ? "§" + String.valueOf(color) + "§l" : "§" + String.valueOf(color);
	}
	
	public String getSimpleColor(){
		return "§" + String.valueOf(color);
	}
	
	public static boolean isRank(UUID uuid, PlayerRank rank){
		if (PlayerRank.valueOf(PlayerMySQL.getRank(uuid)) == rank){
			return true;
		}
		return false;	
	}
	
	
	
}

