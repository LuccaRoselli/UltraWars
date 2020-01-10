package com.ultrawars.lucca.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.ultrawars.lucca.main.Main;

public class UltraScroller {
	public static List<String> efeito = Arrays.asList(new String[] { "§e§lSKYWARS", "§f§lSKYWARS", "§e§lSKYWARS", "§f§lSKYWARS", "§e§lSKYWARS", "§f§lSKYWARS", "§e§lSKYWARS", "§e§lSKYWARS", "§e§lSKYWARS", ""
			+ "§f§lS§6§lKYWARS", "§f§lSK§6§lYWARS", "§f§lSKY§6§lWARS", "§f§lSKYW§6§lARS", "§f§lSKYWA§6§lRS", "§f§lSKYWAR§6§lS", "§f§lSKYWARS", "§e§lSKYWARS", "§f§lSKYWARS", "§e§lSKYWARS", "§f§lSKYWARS", "§e§lSKYWARS", "§f§lSKYWARS"});
	public static List<String> efeito2 = Arrays.asList(new String[] { "§e§lSKYWARS", "§e§lSKYWARS", "§f§lS§6§lKYWARS", "§f§lSK§6§lYWARS", "§f§lSKY§6§lWARS", "§f§lSKYW§6§lARS", "§f§lSKYWA§6§lRS", "§f§lSKYWAR§6§lS", "§f§lSKYWARS", "§e§lSKYWARS", "§f§lSKYWARS"});

	static Iterator<String> ilista = efeito.iterator();
	static Iterator<String> ilista2 = efeito2.iterator();

	static int taskid;
	public static String lastname = "";

	static int vez = 1;

	@SuppressWarnings("deprecation")
	public static void setup() {
		for (Player pls : Bukkit.getOnlinePlayers()) {
			if (pls.getScoreboard() != null && pls.isOnline()) {
				Scoreboard score = pls.getScoreboard();
				Objective stats = score.getObjective(DisplaySlot.SIDEBAR);
				if (vez == 2) {
					if (stats != null){
					stats.setDisplayName("§e§lSKYWARS");
					}
				} else {
					if (stats != null){
					stats.setDisplayName("§e§lSKYWARS");
					}
				}
			}
		}
		new BukkitRunnable() {
			public void run() {
				new BukkitRunnable() {
					public void run() {
						if (vez == 2) {
							if (!ilista.hasNext()) {
								ilista = efeito.iterator();
								for (Player pls : Bukkit.getOnlinePlayers()) {
									if (pls.getScoreboard() != null) {
										Scoreboard score = pls.getScoreboard();
										Objective stats = score
												.getObjective(DisplaySlot.SIDEBAR);
										if (stats != null){
										stats.setDisplayName("§e§lSKYWARS");
										}
									}
								}
								vez = 1;
								setup();
								cancel();
								return;
							}
							String next = ilista.next();
							lastname = next;
							for (Player pls : Bukkit.getOnlinePlayers()) {
								Scoreboard score = pls.getScoreboard();
								if (score != null) {
									if (!score.getObjectives().isEmpty()) {
										Objective stats = score
												.getObjective(DisplaySlot.SIDEBAR);
										if (stats != null){
										stats.setDisplayName(next);
										}
									}
								}
							}
						} else {
							if (!ilista2.hasNext()) {
								ilista2 = efeito2.iterator();
								for (Player pls : Bukkit.getOnlinePlayers()) {
									if (pls.getScoreboard() != null) {
										Scoreboard score = pls.getScoreboard();
										Objective stats = score
												.getObjective(DisplaySlot.SIDEBAR);
										if (stats != null){
										stats.setDisplayName("§e§lSKYWARS");
										}
									}
								}
								vez = 2;
								setup();
								cancel();
								return;
							}
							String next = ilista2.next();
							lastname = next;
							for (Player pls : Bukkit.getOnlinePlayers()) {
								Scoreboard score = pls.getScoreboard();
								if (score != null) {
									if (!score.getObjectives().isEmpty()) {
										Objective stats = score
												.getObjective(DisplaySlot.SIDEBAR);
										if (stats != null){
										stats.setDisplayName(next);
										}
									}
								}
							}
						}
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 4);
				cancel();
			}
		}.runTaskLater(Main.getPlugin(Main.class), 15);
	}
}



