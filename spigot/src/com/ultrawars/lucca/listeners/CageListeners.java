
package com.ultrawars.lucca.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import ca.wacos.nametagedit.NametagAPI;

import com.ultrawars.lucca.config.Config;
import com.ultrawars.lucca.kit.KitType;
import com.ultrawars.lucca.kit.padrao.Armorer;
import com.ultrawars.lucca.kit.padrao.Armorsmith;
import com.ultrawars.lucca.kit.padrao.Cannoneer;
import com.ultrawars.lucca.kit.padrao.Ecologist;
import com.ultrawars.lucca.kit.padrao.Enchanter;
import com.ultrawars.lucca.kit.padrao.Farmer;
import com.ultrawars.lucca.kit.padrao.Fisherman;
import com.ultrawars.lucca.kit.padrao.Hunter;
import com.ultrawars.lucca.kit.padrao.Knight;
import com.ultrawars.lucca.kit.padrao.Pyro;
import com.ultrawars.lucca.kit.padrao.Snowman;
import com.ultrawars.lucca.kit.padrao.Speleologist;
import com.ultrawars.lucca.kit.padrao.Troll;
import com.ultrawars.lucca.main.Main;
import com.ultrawars.lucca.manager.PlayerGame;
import com.ultrawars.lucca.manager.enun.PlayerRank;
import com.ultrawars.lucca.manager.enun.PlayerStage;
import com.ultrawars.lucca.manager.enun.StatusGame;
import com.ultrawars.lucca.storage.PlayerMySQL;
import com.ultrawars.lucca.utils.ItemCustom;
import com.ultrawars.lucca.utils.StringManager;

public class CageListeners implements Listener{
	
	@EventHandler
	public void joinEnvet(final PlayerJoinEvent e){
		if(Main.getGame().getStatus() == StatusGame.CAGE){
			e.setJoinMessage(null);
			
			List<KitType> kits = new ArrayList<KitType>();
			for (KitType t : KitType.values()){
				kits.add(t);
			}
			PlayerMySQL.setKits(e.getPlayer().getUniqueId(), kits);
			PlayerMySQL.setRank(e.getPlayer().getUniqueId(), PlayerRank.DIRETOR);
			System.out.println("adicionado");
			
			Main.getGame().addPlayerInGame(e.getPlayer());
			
			e.getPlayer().getInventory().setArmorContents(null);
			
			e.getPlayer().setMaxHealth(20.0D);
			e.getPlayer().setHealth(20.0D);
			
			Main.getGame().bc(StringManager.PREFIX + e.getPlayer().getName() + " §eentrou (§b" +Main.getGame().getPlayers().size() + "§e/§b" + Bukkit.getMaxPlayers() + "§e)!" );
			new BukkitRunnable() {
				
				@Override
				public void run() {
					sendTag(e.getPlayer());
				}
			}.runTaskLaterAsynchronously(Main.getPlugin(), 2L);
			
			if(Main.getGame().getTimeStart() == -1 && Main.getGame().getPlayers().size() >= Config.min_players)
				Main.getGame().startContage();
			new PlayerGame(e.getPlayer()).sendScore();
		}
	}
	
	public void sendTag(Player p){
        if(PlayerRank.isRank(p.getUniqueId(), PlayerRank.DIRETOR)) {
			NametagAPI.setPrefix(p.getName(), PlayerRank.valueOf(PlayerMySQL.getRank(p.getUniqueId())).getColor() + PlayerRank.valueOf(PlayerMySQL.getRank(p.getUniqueId())).getDisplay() + " §r" + PlayerRank.valueOf(PlayerMySQL.getRank(p.getUniqueId())).getSimpleColor());
            return;
        } else if(PlayerRank.isRank(p.getUniqueId(), PlayerRank.ADMINSTRADOR)) {
        	NametagAPI.setPrefix(p.getName(), PlayerRank.valueOf(PlayerMySQL.getRank(p.getUniqueId())).getColor() + PlayerRank.valueOf(PlayerMySQL.getRank(p.getUniqueId())).getDisplay() + " §r" + PlayerRank.valueOf(PlayerMySQL.getRank(p.getUniqueId())).getSimpleColor());
            return;
        } else if(PlayerRank.isRank(p.getUniqueId(), PlayerRank.MODERADOR)) {
        	NametagAPI.setPrefix(p.getName(), PlayerRank.valueOf(PlayerMySQL.getRank(p.getUniqueId())).getColor() + PlayerRank.valueOf(PlayerMySQL.getRank(p.getUniqueId())).getDisplay() + " §r" + PlayerRank.valueOf(PlayerMySQL.getRank(p.getUniqueId())).getSimpleColor());
            return;
        } else if(PlayerRank.isRank(p.getUniqueId(), PlayerRank.TITAN)) {
        	NametagAPI.setPrefix(p.getName(), PlayerRank.valueOf(PlayerMySQL.getRank(p.getUniqueId())).getColor() + PlayerRank.valueOf(PlayerMySQL.getRank(p.getUniqueId())).getDisplay() + " §r" + PlayerRank.valueOf(PlayerMySQL.getRank(p.getUniqueId())).getSimpleColor());
            return;
        } else if(PlayerRank.isRank(p.getUniqueId(), PlayerRank.NORMAL)) {
        	NametagAPI.setPrefix(p.getName(), PlayerRank.valueOf(PlayerMySQL.getRank(p.getUniqueId())).getColor() + PlayerRank.valueOf(PlayerMySQL.getRank(p.getUniqueId())).getDisplay() + " §r" + PlayerRank.valueOf(PlayerMySQL.getRank(p.getUniqueId())).getSimpleColor());
            return;
        }
        System.out.println("mandei tag");
	}
	
	@EventHandler
	public void quitEvent(PlayerQuitEvent e){
		if(Main.getGame().getStatus() == StatusGame.CAGE){
			e.setQuitMessage(null);
			Main.getGame().removePlayerGame(e.getPlayer());
			Main.getGame().bc(StringManager.PREFIX + e.getPlayer().getName() + " §esaiu (§b" +Main.getGame().getPlayers().size() + "§e/§b" + Bukkit.getMaxPlayers() + "§e)" );
		
		}
	}
	
	@EventHandler
	public void kickEvent(PlayerKickEvent e){
		if(Main.getGame().getStatus() == StatusGame.CAGE){
			e.setLeaveMessage(null);
			Main.getGame().removePlayerGame(e.getPlayer());
			Main.getGame().bc(StringManager.PREFIX + e.getPlayer().getName() + " §esaiu (§b" +Main.getGame().getPlayers().size() + "§e/§b" + Bukkit.getMaxPlayers() + "§e)" );
			
		}
	}
	
	@EventHandler
	public void blockPlace(BlockPlaceEvent e){
		if(Main.getGame().getStatus() == StatusGame.CAGE)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void blockBreak(BlockBreakEvent e){
		if(Main.getGame().getStatus() == StatusGame.CAGE)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void blockBreakChest(BlockBreakEvent e){
		if(e.getBlock().getType() == Material.CHEST){
			e.setCancelled(true);
			e.getPlayer().sendMessage(StringManager.INFO + "Você não pode quebrar baús!");
		}
	}
	
	@EventHandler
	public void drop(PlayerDropItemEvent e){
		if(Main.getGame().getStatus() == StatusGame.CAGE)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void damage(EntityDamageEvent e){
		if(Main.getGame().getStatus() == StatusGame.CAGE)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void blockP(FoodLevelChangeEvent e){
		if(e.getEntity() instanceof Player)
			if(Main.getGame().getStatus() == StatusGame.CAGE)
				e.setCancelled(true);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void openChest(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getClickedBlock().getType() == Material.CHEST){
				if (Main.getGame().getStatus() != StatusGame.GAME){
					e.setCancelled(true);
					e.getPlayer().playEffect(e.getClickedBlock().getLocation(), Effect.SMOKE, 0);
				}
			}
		}
	}
	
	@EventHandler
	public void getItemDroped(PlayerPickupItemEvent e){
		if(Main.getGame().getStatus() == StatusGame.CAGE)
				e.setCancelled(true);
	}
	
	@EventHandler
	public void interact(PlayerInteractEvent e){
		if(Main.getGame().getStatus() == StatusGame.CAGE){
			Player p = e.getPlayer();
			ItemStack item = p.getItemInHand();
			if(item.equals(new ItemCustom().type(Material.BLAZE_POWDER).name("&6Opções vips").build())){
				
			}else if(item.equals(new ItemCustom().type(Material.BOW).name("&aSeletor de kits").build())){
				
				Inventory selector = Bukkit.createInventory(null, 54, "Seletor de kits");
				ItemStack vidro = new ItemCustom().type(Material.THIN_GLASS).name("&a ").build();
				selector.setItem(0, vidro);
				selector.setItem(1, vidro);
				selector.setItem(2, vidro);
				selector.setItem(3, vidro);
				selector.setItem(4, new ItemCustom().type(Material.INK_SACK).name("&a ").build());
				selector.setItem(5, vidro);
				selector.setItem(6, vidro);
				selector.setItem(7, vidro);
				selector.setItem(8, vidro);
				selector.setItem(49, new ItemCustom().type(Material.INK_SACK).name("&a ").build());
				
				List<KitType> kits = PlayerMySQL.getKits(p.getUniqueId());
				
				for(int i = 0; i < KitType.values().length; i++){
					KitType kit = KitType.values()[i];
					if(kits.contains(kit))
						selector.addItem(new ItemCustom(kit.getItem()).name(kit.getItem().getItemMeta().getDisplayName()).addLore("").addLore("§b» &7Clique para selecionar").build());
					else
						selector.addItem(new ItemCustom(kit.getItem()).type(Material.STAINED_GLASS_PANE).name(kit.getItem().getItemMeta().getDisplayName()).addLore("").addLore("&c» Bloqueado").build());
				}
				for(int i = 0; i < selector.getSize(); i++){
					if(selector.getItem(i) != null){
						if(selector.getItem(i).getType() == Material.THIN_GLASS){
							selector.setItem(i, new ItemStack(Material.AIR));
						}
					}
				}
				p.openInventory(selector);
			}else if(item.equals(new ItemCustom().type(Material.BED).name("&cRetornar ao lobby").build())){
				p.chat("/lobby");
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void selectKit(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();

		if(e.getInventory().getName().equalsIgnoreCase("Seletor de kits") && (e.getCurrentItem() != null)
				&& (e.getCurrentItem().getTypeId() != 0)){
			if(Main.getGame().getStatus() == StatusGame.CAGE){
				if(Main.getGame().getPlayer(p).getPlayerStage() == PlayerStage.INCAGE){
					e.setCancelled(true);
					
					KitType kit = KitType.getKit(e.getCurrentItem());
					PlayerGame pg = Main.getGame().getPlayer(p);
					
					if(e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE){
						pg.getPlayer().sendMessage(StringManager.ERROR + "§cVoce precisa desbloquear esse kit para usa-lo");
						p.closeInventory();
						return;
					}
					if(e.getCurrentItem().getType() == Material.INK_SACK){
						return;
					}
					switch (kit) {
					case ARMORER:
						pg.setKitPlayer(new Armorer());
						pg.getPlayer().sendMessage(StringManager.INFO + "Você selecionou o kit Armorer");
						break;
					case ARMORSWITH:
						pg.setKitPlayer(new Armorsmith());
						pg.getPlayer().sendMessage(StringManager.INFO + "Você selecionou o kit Armorswith");
						break;	
					case CANNONEER:
						pg.setKitPlayer(new Cannoneer());
						pg.getPlayer().sendMessage(StringManager.INFO + "Você selecionou o kit Cannoneer");
						break;
					case ECOLOGIST:
						pg.setKitPlayer(new Ecologist());
						pg.getPlayer().sendMessage(StringManager.INFO + "Você selecionou o kit Ecologist");
						break;
					case ENCHANTER:
						pg.setKitPlayer(new Enchanter());
						pg.getPlayer().sendMessage(StringManager.INFO + "Você selecionou o kit Enchanter");
						break;
					case FARMER:
						pg.setKitPlayer(new Farmer());
						pg.getPlayer().sendMessage(StringManager.INFO + "Você selecionou o kit Farmer");
						break;
					case FISHERMAN:
						pg.setKitPlayer(new Fisherman());
						pg.getPlayer().sendMessage(StringManager.INFO + "Você selecionou o kit Fisherman");
						break;
					case HUNTER:
						pg.setKitPlayer(new Hunter());
						pg.getPlayer().sendMessage(StringManager.INFO + "Você selecionou o kit Hunter");
						break;
					case KNIGHT:
						pg.setKitPlayer(new Knight());
						pg.getPlayer().sendMessage(StringManager.INFO + "Você selecionou o kit Knight");
						break;
					case PYRO:
						pg.setKitPlayer(new Pyro());
						pg.getPlayer().sendMessage(StringManager.INFO + "Você selecionou o kit Pyro");
						break;
					case SPELEOLOGIST:
						pg.setKitPlayer(new Speleologist());
						pg.getPlayer().sendMessage(StringManager.INFO + "Você selecionou o kit Speleologist");
						break;
					case SWNOMAN:
						pg.setKitPlayer(new Snowman());
						pg.getPlayer().sendMessage(StringManager.INFO + "Você selecionou o kit Snowman");
						break;
					case TROLL:
						pg.setKitPlayer(new Troll());
						pg.getPlayer().sendMessage(StringManager.INFO + "Você selecionou o kit Troll");
						break;
					default:
						break;
					}
					pg.getPlayer().closeInventory();
				}
			}
		}
	}
	
}
