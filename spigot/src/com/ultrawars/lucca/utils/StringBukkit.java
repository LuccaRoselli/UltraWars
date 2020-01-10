package com.ultrawars.lucca.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StringBukkit {

	public static String format(String text) {
		return (text.replaceAll("&", "§"));
	}

	public static List<String> format(List<String> text) {
		if (text == null)
			return new ArrayList<>();

		List<String> replace = new ArrayList<>();

		for (String s : text) {
			replace.add(s.replaceAll("&", "§"));
		}

		return replace;
	}

	public static void send(Player player, String text) {
		player.sendMessage(format(text));
	}

	public static void send(Player player, String text, ChatColor colorAll) {
		if (text.contains("&"))
			text.replaceAll("&", "");
		if (text.contains("§"))
			text.replaceAll("§", "");

		player.sendMessage(colorAll + text);
	}
	
	private final static int CENTER_PX = 154;

	public static void sendCenteredMessage(Player player, String message) {
		if (message == null || message.equals(""))
			player.sendMessage("");
		message = ChatColor.translateAlternateColorCodes('&', message);

		int messagePxSize = 0;
		boolean previousCode = false;
		boolean isBold = false;

		for (char c : message.toCharArray()) {
			if (c == '§') {
				previousCode = true;
				continue;
			} else if (previousCode == true) {
				previousCode = false;
				if (c == 'l' || c == 'L') {
					isBold = true;
					continue;
				} else
					isBold = false;
			} else {
				DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
				messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
				messagePxSize++;
			}
		}

		int halvedMessageSize = messagePxSize / 2;
		int toCompensate = CENTER_PX - halvedMessageSize;
		int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
		int compensated = 0;
		StringBuilder sb = new StringBuilder();
		while (compensated < toCompensate) {
			sb.append(" ");
			compensated += spaceLength;
		}
		player.sendMessage(sb.toString() + message);
	}
}
