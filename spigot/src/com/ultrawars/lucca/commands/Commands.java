package com.ultrawars.lucca.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.ultrawars.lucca.config.Config;
import com.ultrawars.lucca.gamechest.GameChest;
import com.ultrawars.lucca.gamechest.GameChestUtils;
import com.ultrawars.lucca.main.Main;
import com.ultrawars.lucca.manager.Game;
import com.ultrawars.lucca.manager.enun.PlayerRank;
import com.ultrawars.lucca.storage.PlayerMySQL;
import com.ultrawars.lucca.utils.LocaisSerialize;
import com.ultrawars.lucca.utils.StringManager;

public class Commands implements  CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))
			return false;
		
		Player p = (Player) sender;
		
		
		if(label.equalsIgnoreCase("skywars") || label.equalsIgnoreCase("sw")){
			if(!PlayerRank.isRank(p.getUniqueId(), PlayerRank.DIRETOR) && !PlayerRank.isRank(p.getUniqueId(), PlayerRank.ADMINSTRADOR)){
				p.sendMessage(StringManager.NO_PERMISSION);
				return false;
			}
			
			if(args.length <= 0){
				p.sendMessage("§7----------------------------------------------------");
				p.sendMessage("§7                     Comandos");
				p.sendMessage("§7/sw setlobby - §asetar o lobby de espera do minigame");
				p.sendMessage("§7/sw setilha <numero> - §asetar as ilha do skywars");
				p.sendMessage("§7/sw setborder <1|2> - §asete os limites do mapa!");
				p.sendMessage("§7/sw setfeastborder - §asete os limites do feast");
				p.sendMessage("§7/sw setspec - §aseta o spawn do espactator");
				p.sendMessage("§7/sw addchest (1|2|3) - §aseta os baús que respawnaram item (inicial) (3 = feast)");
				
				p.sendMessage("§7-----------------------------------------------------");
				return false;
			}
			if(args[0].equalsIgnoreCase("buykit")){
				Game.buyKit(p, Integer.valueOf(args[1]));
				return false;
			}
			if(args[0].equalsIgnoreCase("setlobby")){
				Main.getLocsYml().set("Lobby", LocaisSerialize.setLocal(p.getLocation(), true));
				Main.saveLocsYml();
				
				p.sendMessage(StringManager.INFO + "Lobby setado");
				
				return false;
			}
			
			if(args[0].equalsIgnoreCase("setilha")){
				if(args.length <= 1){
					p.sendMessage(StringManager.ERROR + "Use /sw setilha <numero>");
					return false;
				}
				
				try{
					int number = Integer.valueOf(args[1]);
					
					Main.getLocsYml().set("Ilhas." + number, LocaisSerialize.setLocal(p.getLocation(), true));
					Main.saveLocsYml();
					
					p.sendMessage(StringManager.INFO + "Ilha " + args[1] + " setada");
					return true;
				}catch(Exception e){
					p.sendMessage(StringManager.ERROR + "Digite apenas numeros");
				}
				return false;
			}
			if(args[0].equalsIgnoreCase("setborder")){
				if(args.length <= 1){
					p.sendMessage(StringManager.ERROR + "Use /sw setborder <1|2>");
					return false;
				}
				
				try{
					int number = Integer.valueOf(args[1]);
					
					Main.getLocsYml().set("Borda." + number, LocaisSerialize.setLocal(p.getLocation(), true));
					Main.saveLocsYml();
					
					p.sendMessage(StringManager.INFO + "Ponta da borda " + args[1] + " setada");
					return true;
				}catch(Exception e){
					p.sendMessage(StringManager.ERROR + "Digite apenas numeros");
				}
				return false;
			}
			if(args[0].equalsIgnoreCase("setfeastborder")){
				if(args.length <= 1){
					p.sendMessage(StringManager.ERROR + "Use /sw setfeastborder <1|2>");
					return false;
				}
				
				try{
					int number = Integer.valueOf(args[1]);
					
					Main.getLocsYml().set("Feast.Borda." + number, LocaisSerialize.setLocal(p.getLocation(), true));
					Main.saveLocsYml();
					
					p.sendMessage(StringManager.INFO + "Ponta da borda do feast " + args[1] + " setada");
					return true;
				}catch(Exception e){
					p.sendMessage(StringManager.ERROR + "Digite apenas numeros");
				}
				return false;
			}
			if(args[0].equalsIgnoreCase("addchest")){
				int tier;
				try {
					tier = Integer.parseInt(args[1]);
				}
				
				catch (Exception e) {
					p.sendMessage(StringManager.ERROR + ChatColor.RED + "Digite um numero");
					return true;
				}
				
				if (tier != 1 && tier != 2 && tier != 3) {
					p.sendMessage(StringManager.ERROR + ChatColor.RED + "Tamanho invalido! Tamanho tem que ser 1, 2 ou 3");
					return true;
				}
				
				@SuppressWarnings("deprecation")
				Block target = p.getTargetBlock(null, 10);
				
				if (target == null) {
					p.sendMessage(StringManager.ERROR + ChatColor.RED + "Olhe para um bloco");
					return true;
				}
				
				if (!(target.getState() instanceof Chest)) {
					p.sendMessage(StringManager.ERROR + ChatColor.RED + "Olhe para um bau");
					return true;
				}
				
				Chest chest = (Chest) target.getState();
				
				if (!Main.getLocsYml().contains(Config.mapa + ".chests")) {
					Main.getLocsYml().createSection(Config.mapa + ".chests");
				}
				
				new GameChest(chest, tier).save(Main.getLocsYml().createSection(Config.mapa + ".chests." + ((ConfigurationSection) Main.get(Config.mapa + ".chests")).getKeys(false).size()));
				Main.saveLocsYml();
				
				p.sendMessage(StringManager.INFO + ChatColor.GREEN + "Bau adicionado");
				GameChestUtils.loadChests();
				}
			
			if(args[0].equalsIgnoreCase("setspec")){
				
				Main.getLocsYml().set("Espectador", LocaisSerialize.setLocal(p.getLocation(), true));
				Main.saveLocsYml();
				p.sendMessage(StringManager.INFO + "Spawn do espectador setado");
			}
			if(args[0].equalsIgnoreCase("coin")){
				if(args.length <= 3){
					p.sendMessage(StringManager.ERROR + "/sw coin <set/add/remove> <player> <value>");
					return false;
				}
				
				if(args[1].equalsIgnoreCase("set")){
					Player target = Bukkit.getPlayer(args[2]);
				
					if(target == null){
						p.sendMessage(StringManager.PLAYER_NULL);
						return false;
					}
					
					int valueSet = 0 ;
					
					try{
						 valueSet = Integer.valueOf(args[3]);
					}catch(Exception e){
						p.sendMessage(StringManager.ERROR + "Digite apenas numero");
						return false;
					}
					
					PlayerMySQL.setCoin(target.getUniqueId(), valueSet);
					p.sendMessage(StringManager.INFO + "Setado " + valueSet + " coins na conta do " + target.getName());
				}
				if(args[1].equalsIgnoreCase("add")){
					Player target = Bukkit.getPlayer(args[2]);
					
					if(target == null){
						p.sendMessage(StringManager.PLAYER_NULL);
						return false;
					}
					
					int valueAdd = 0;
					
					try{
						 valueAdd = Integer.valueOf(args[3]);
					}catch(Exception e){
						p.sendMessage(StringManager.ERROR + "Digite apenas numero");
						return false;
					}
					
					PlayerMySQL.setCoin(target.getUniqueId(), (PlayerMySQL.getCoin(target.getUniqueId()) + valueAdd));
					p.sendMessage(StringManager.INFO + "Adcionado " + valueAdd + " coins na conta do " + target.getName());
				}
				if(args[1].equalsIgnoreCase("remove")){
					Player target = Bukkit.getPlayer(args[2]);
					
					if(target == null){
						p.sendMessage(StringManager.PLAYER_NULL);
						return false;
					}
					
					int valueRemove = 0;
					
					try{
						valueRemove = Integer.valueOf(args[3]);
					}catch(Exception e){
						p.sendMessage(StringManager.ERROR + "Digite apenas numero");
						return false;
					}
					
					if((PlayerMySQL.getCoin(target.getUniqueId()) - valueRemove) >= 1){
						PlayerMySQL.setCoin(target.getUniqueId(), (PlayerMySQL.getCoin(target.getUniqueId()) - valueRemove));
						p.sendMessage(StringManager.INFO + "Retirados " + valueRemove + " coins da conta de " + target.getName());
					}else{
						p.sendMessage(StringManager.INFO + "Retirados " + PlayerMySQL.getCoin(p.getUniqueId()) + " coins da conta de " + target.getName());
						PlayerMySQL.setCoin(target.getUniqueId(), 0);
					}
				}
			}
		}
		return false;
	}

}
