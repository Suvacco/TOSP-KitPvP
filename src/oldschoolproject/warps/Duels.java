package oldschoolproject.warps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;

import oldschoolproject.Main;
import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.users.User.UserState;
import oldschoolproject.utils.builders.ItemBuilder;
import oldschoolproject.utils.listeners.BaseListener;
import oldschoolproject.utils.warps.BaseWarp;

public class Duels extends BaseWarp implements BaseListener {
	
	private static Map<Player, Player> requestsMap = new HashMap<>();
	private static Map<Player, Player> matchMap = new HashMap<>();
	private static List<Player> cantMove = new ArrayList<>(); 
	private static List<Player> players = new ArrayList<>();
	
	public Duels() {
		super("Duels", new ItemBuilder(Material.BLAZE_ROD).toItemStack());
	}

	@Override
	public void handlePlayerJoin(Player player) {
		players.add(player);
	}

	@Override
	public void handlePlayerQuit(Player player) {
		players.remove(player);
	}

	@Override
	public void setDefaultItems(Player player) {
		player.getInventory().setItem(1, new ItemBuilder(Material.BLAZE_ROD).setName("§eConvidar Jogador").toItemStack());
		
		player.getInventory().setItem(7, new ItemBuilder(Material.OAK_DOOR).setName("§cRetornar ao Spawn").toItemStack());
	}
	
	private void giveItems(Player player) {
		player.getInventory().clear();
		player.getInventory().setItem(0, new ItemBuilder(Material.DIAMOND_SWORD).setName("§bEspada").toItemStack());
		player.getInventory().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).setName("§bPeitoral").toItemStack());
		player.getInventory().setHelmet(new ItemBuilder(Material.IRON_HELMET).setName("§bCapacete").toItemStack());
		player.getInventory().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).setName("§bCalças").toItemStack());
		player.getInventory().setBoots(new ItemBuilder(Material.IRON_BOOTS).setName("§bBotas").toItemStack());
		
		for (int i = 0; i < player.getInventory().getSize(); i++) {
			player.getInventory().addItem(new ItemBuilder(Material.MUSHROOM_STEW).setName("§6Sopa").toItemStack());
		}
	}
	
	private void startMatch(Player player, Player clicked) {
		// This exists cause' I'm creating a relationship between a Warp object and DuelsGame
		// In order to have access to the Warp's locations file.
		
		this.teleportToLocation(player, "loc1");
		this.teleportToLocation(clicked, "loc2");
		
		// Resets pendings requests
		requestsMap.remove(clicked);
		requestsMap.remove(player);
		
		// Add to match
		matchMap.put(clicked, player);
		matchMap.put(player, clicked);
		
		// Freeze
		cantMove.add(clicked);
		cantMove.add(player);
		
		// Hide all except who is against
		for (Player all : Bukkit.getOnlinePlayers()) {
			player.hidePlayer(Main.getInstance(), all);
			clicked.hidePlayer(Main.getInstance(), all);
		}
		
		player.showPlayer(Main.getInstance(), clicked);
		clicked.showPlayer(Main.getInstance(), player);
		
		clicked.sendMessage("§6Partida começando em...");
		player.sendMessage("§6Partida começando em...");
		
		giveItems(player);
		giveItems(clicked);
		
		new BukkitRunnable() {
			int i = 3;
			
			User playerUser = UserManager.getUser(player);
			User clickedUser = UserManager.getUser(clicked);
			
			public void run() {
				if (i == 0) {
					cantMove.remove(clicked);
					cantMove.remove(player);
					clicked.sendMessage("§aGO!");
					player.sendMessage("§aGO!");
					playerUser.setState(UserState.Playing);
					clickedUser.setState(UserState.Playing);
					this.cancel();
				} else {
				
				clicked.sendMessage("§e" + i + "...");
				player.sendMessage("§e" + i + "...");
				
				i--;
				}
			}
		}.runTaskTimer(Main.getInstance(), 0, 20);
		
		new BukkitRunnable() {
			int i = 300;
			public void run() {
				// If the match still exists
				if (matchMap.containsKey(clicked)) {
					if (i == 60) {
						clicked.sendMessage("§cO tempo está acabando! Encerrem a batalha rápido!");
						player.sendMessage("§cO tempo está acabando! Encerrem a batalha rápido!");
					}
					if (i == 1) {
						clicked.sendMessage("§cO tempo acabou!");
						player.sendMessage("§cO tempo acabou!");
					}
					if (i > 0) {
						i--;
					} else {
						clicked.damage(1.0D);
						player.damage(1.0D);
					}
				} else {
					cancel();
				}
			}
		}.runTaskTimer(Main.getInstance(), 0, 20);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		
		if (matchMap.containsKey(player)) {
			User winner = UserManager.getUser(matchMap.get(player));
			
			winner.getPlayer().sendMessage("§aVocê ganhou!");
			
			matchMap.remove(matchMap.get(player));
			matchMap.remove(player);
			
			winner.reset();
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player player = (Player)e.getEntity();
		
		if (matchMap.containsKey(player)) {
			
			User winner = UserManager.getUser(matchMap.get(player));
			
			winner.getPlayer().sendMessage("§aVocê ganhou!");
			player.sendMessage("§cVocê perdeu");
			
			matchMap.remove(matchMap.get(player));
			matchMap.remove(player);
			
			winner.reset();
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (cantMove.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onClick(PlayerInteractAtEntityEvent e) {
		Player player = e.getPlayer();
		
		if (e.getRightClicked() instanceof Player) {
			
			Player clicked = (Player) e.getRightClicked();
			
			if (e.getHand().equals(EquipmentSlot.HAND)) {
				
				if (player.getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD) {
					
					if (players.contains(player) && players.contains(clicked)) {
						// If player clicking was clicked before by the player he is clicking
						if (requestsMap.get(clicked) == player) {
							
							startMatch(player, clicked);
							
							return;
						}
					
						player.sendMessage("§eVocê convidou §b" + clicked.getName() + " §epara um duelo");
						
						clicked.sendMessage("§b" + player.getName() + " §ete convidou para um duelo");
						
						requestsMap.put(player, clicked);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void duelsInteract(PlayerInteractEvent e) {
		User user = UserManager.getUser(e.getPlayer());
		
		if (!(user.getWarp() instanceof Duels)) {
			return;
		}
		
		if (!user.isProtected()) {
			return;
		}
		
		if (e.getItem() == null) {
			return;
		}
		
		if (e.getItem().getType().equals(Material.OAK_DOOR)) {
			e.getPlayer().performCommand("warp spawn");
			return;
		}
	}
}
