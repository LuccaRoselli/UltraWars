package com.ultrawars.lucca.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.ultrawars.lucca.main.Main;
import com.ultrawars.lucca.manager.PlayerGame;
import com.ultrawars.lucca.manager.enun.PlayerStage;
import com.ultrawars.lucca.manager.enun.StatusGame;
import com.ultrawars.lucca.utils.ItemCustom;
import com.ultrawars.lucca.utils.StringManager;

public class SpecListeners implements Listener {
	
	@EventHandler
	public void blockP(BlockPlaceEvent e){
		if(Main.getGame().getStatus() == StatusGame.GAME)
			if(Main.getGame().getPlayer(e.getPlayer()).getPlayerStage() == PlayerStage.SPEC)
				e.setCancelled(true);
	}
	
	@EventHandler
	public void blockP(BlockBreakEvent e){
		if(Main.getGame().getStatus() == StatusGame.GAME)
			if(Main.getGame().getPlayer(e.getPlayer()).getPlayerStage() == PlayerStage.SPEC)
				e.setCancelled(true);
	}
	
	@EventHandler
	public void blockP(PlayerDropItemEvent e){
		if(Main.getGame().getStatus() == StatusGame.GAME)
			if(Main.getGame().getPlayer(e.getPlayer()).getPlayerStage() == PlayerStage.SPEC)
				e.setCancelled(true);
	}
	
	@EventHandler
	public void blockP(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player)
			if(Main.getGame().getStatus() == StatusGame.GAME)
				if(Main.getGame().getPlayer((Player) e.getDamager()).getPlayerStage() == PlayerStage.SPEC)
					e.setCancelled(true);
	}
	
	@EventHandler
	public void blockP(FoodLevelChangeEvent e){
		if(e.getEntity() instanceof Player)
			if(Main.getGame().getStatus() == StatusGame.GAME)
				if(Main.getGame().getPlayer((Player) e.getEntity()).getPlayerStage() == PlayerStage.SPEC)
					e.setCancelled(true);
	}
	
	@EventHandler
	public void getItemDroped(PlayerPickupItemEvent e){
		if(Main.getGame().getStatus() == StatusGame.GAME)
			if(Main.getGame().getPlayer(e.getPlayer()).getPlayerStage() == PlayerStage.SPEC)
				e.setCancelled(true);
	}
	
	
	@EventHandler
	public void interact(PlayerInteractEvent e){
		if(Main.getGame().getStatus() == StatusGame.GAME){
			if(Main.getGame().getPlayer(e.getPlayer()).getPlayerStage() == PlayerStage.SPEC){
				ItemStack item = e.getPlayer().getItemInHand();
				if(item.equals(new ItemCustom().type(Material.COMPASS).name("&a&lTeleporte").addLore("&7Clique para espectar algum jogador").build())){
					if(Main.getGame().filterPlayerGame(PlayerStage.INGAME).size() <= 9){
						Inventory teleporter = Bukkit.createInventory(null, 9, "Teleporte");
						
						for(PlayerGame pg : Main.getGame().filterPlayerGame(PlayerStage.INGAME))
							teleporter.addItem(new ItemCustom().ownerSkull(pg.getPlayer().getName(), "§7" + pg.getPlayer().getName()).addLore(("§7Vida: " + ((Damageable)pg.getPlayer()).getHealth() + "")).addLore("§7Fome: " + pg.getPlayer().getFoodLevel()).build());
						
						e.getPlayer().openInventory(teleporter);
					}else if(Main.getGame().filterPlayerGame(PlayerStage.INGAME).size() >= 10 && Main.getGame().filterPlayerGame(PlayerStage.INGAME).size() <= 18){
						Inventory teleporter = Bukkit.createInventory(null, 18, "Teleporte");
						
						for(PlayerGame pg : Main.getGame().filterPlayerGame(PlayerStage.INGAME))
							teleporter.addItem(new ItemCustom().ownerSkull(pg.getPlayer().getName(), "§7" + pg.getPlayer().getName()).addLore(("§7Vida: " + ((Damageable)pg.getPlayer()).getHealth() + "")).addLore("§7Fome: " + pg.getPlayer().getFoodLevel()).build());
						
						e.getPlayer().openInventory(teleporter);
					}else if(Main.getGame().filterPlayerGame(PlayerStage.INGAME).size() >= 18 && Main.getGame().filterPlayerGame(PlayerStage.INGAME).size() <= 27){
						
						Inventory teleporter = Bukkit.createInventory(null, 27, "Teleporte");
						
						for(PlayerGame pg : Main.getGame().filterPlayerGame(PlayerStage.INGAME))
							teleporter.addItem(new ItemCustom().ownerSkull(pg.getPlayer().getName(), "§7" + pg.getPlayer().getName()).addLore(("§7Vida: " + ((Damageable)pg.getPlayer()).getHealth() + "")).addLore("§7Fome: " + pg.getPlayer().getFoodLevel()).build());
						
						e.getPlayer().openInventory(teleporter);
					}
				}
				else if(item.equals(new ItemCustom().type(Material.REDSTONE_COMPARATOR).name("&b&lConfiguração dos espectator").build())){
					
					Inventory panel = Bukkit.createInventory(null, 36, "Configuração de espectador");
					
					panel.setItem(11, new ItemCustom().type(Material.LEATHER_BOOTS).name("&aSem velocidade").build());
					panel.setItem(12, new ItemCustom().type(Material.CHAINMAIL_BOOTS).name("&aVelocidade I").build());
					panel.setItem(13, new ItemCustom().type(Material.IRON_BOOTS).name("&aVelocidade II").build());
					panel.setItem(14, new ItemCustom().type(Material.GOLD_BOOTS).name("&aVelocidade III").build());
					panel.setItem(15, new ItemCustom().type(Material.DIAMOND_BOOTS).name("&aVelocidade IV").build());
					panel.setItem(24, new ItemCustom().type(Material.FEATHER).name("&aFly").build());
					e.getPlayer().openInventory(panel);
					
				}
				else if(item.equals(new ItemCustom().type(Material.BED).name("&aRetornar ao lobby").build())){
					e.getPlayer().chat("/lobby");
				}
			}
		}
	}
	
	@EventHandler
	public void clickTeleporter(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		if(e.getCurrentItem() == null && !e.getCurrentItem().hasItemMeta())
			return;
		
		if(Main.getGame().getStatus() == StatusGame.GAME){
			if(Main.getGame().getPlayer(p).getPlayerStage() == PlayerStage.SPEC){
				if(e.getInventory().getName().equals("Teleporte")){
					e.setCancelled(true);
					try{
						Player player = Bukkit.getPlayer(e.getCurrentItem().getItemMeta().getDisplayName().replace("§7", ""));
						if(Main.getGame().getPlayer(player).getPlayerStage() == PlayerStage.INGAME){
							p.teleport(player);
						}else{
							p.closeInventory();
						}
					}catch(Exception ee){
						p.closeInventory();
					}
				}
			}
		}
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void clickInventoryConfig(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		if(e.getCurrentItem() == null && !e.getCurrentItem().hasItemMeta())
			return;
		
		if(Main.getGame().getStatus() == StatusGame.GAME){
			if(Main.getGame().getPlayer(p).getPlayerStage() == PlayerStage.SPEC){
				if(e.getInventory().getName().equals("Configuração de espectador") && (e.getCurrentItem() != null)
						&& (e.getCurrentItem().getTypeId() != 0)){
					e.setCancelled(true);
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§aSem velocidade")){
						p.setWalkSpeed((float) 0.2);
						p.setFlySpeed((float) 0.2);
						p.closeInventory();
						p.sendMessage(StringManager.INFO + "§aVoce desativou a velocidade");
					}
					else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§aVelocidade I")){
						p.setWalkSpeed((float) 0.3);
						p.setFlySpeed((float) 0.3);
						p.closeInventory();
						p.sendMessage(StringManager.INFO + "§aVoce ativou a velocidade I");
					}	
					else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§aVelocidade II")){
						p.setWalkSpeed((float) 0.4);
						p.setFlySpeed((float) 0.4);
						p.closeInventory();
						p.sendMessage(StringManager.INFO + "§aVoce ativou a velocidade II");
					}	
					else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§aVelocidade III")){
						p.setWalkSpeed((float) 0.5);
						p.setFlySpeed((float) 0.5);
						p.closeInventory();
						p.sendMessage(StringManager.INFO + "§aVoce ativou a velocidade III");
					}
					else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("&aVelocidade IV")){
						p.setWalkSpeed((float) 0.6);
						p.setFlySpeed((float) 0.6);
						p.closeInventory();
						p.sendMessage(StringManager.INFO + "§aVoce ativou a velocidade IV");
					}
					else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("&aFly")){
						if(p.isFlying()){
							p.setAllowFlight(false);
							p.setFlying(false);
							p.sendMessage(StringManager.INFO + "§aVoce desativou o fly");
						}else{
							p.setAllowFlight(true);
							p.setFlying(true);
							p.sendMessage(StringManager.INFO + "§aVoce ativou o fly");
						}
						p.closeInventory();
					}
				}
			}
		}
	}
	
}
