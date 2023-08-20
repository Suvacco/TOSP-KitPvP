package oldschoolproject.warps.instances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oldschoolproject.users.UserGuard;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
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
import oldschoolproject.utils.builders.ItemBuilder;
import oldschoolproject.events.BaseListener;
import oldschoolproject.warps.BaseWarp;

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
		player.getInventory().setItem(1, new ItemBuilder(Material.BLAZE_ROD).setName("§eChallenge Player").toItemStack());
		
		player.getInventory().setItem(7, new ItemBuilder(Material.OAK_DOOR).setName("§cReturn to Spawn").toItemStack());
	}
	
	private void giveItems(Player player) {
		player.getInventory().clear();
		player.getInventory().setItem(0, new ItemBuilder(Material.DIAMOND_SWORD).setName("§bSword").toItemStack());
		player.getInventory().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).setName("§bChestplate").toItemStack());
		player.getInventory().setHelmet(new ItemBuilder(Material.IRON_HELMET).setName("§bHelmet").toItemStack());
		player.getInventory().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).setName("§bLeggins").toItemStack());
		player.getInventory().setBoots(new ItemBuilder(Material.IRON_BOOTS).setName("§bBoots").toItemStack());
		
		for (int i = 0; i < player.getInventory().getSize(); i++) {
			player.getInventory().addItem(new ItemBuilder(Material.MUSHROOM_STEW).setName("§6Soup").toItemStack());
		}
	}

	private void preparePlayer(Player player) {
		requestsMap.remove(player);

		cantMove.add(player);

		for (Player all : Bukkit.getOnlinePlayers()) {
			player.hidePlayer(Main.getInstance(), all);
		}

		player.sendMessage("§6Match starting in...");

		giveItems(player);
	}

	private void startMatchForPlayer(User user) {
		cantMove.remove(user.getPlayer());

		user.getPlayer().sendMessage("§a§lFIGHT!");

		user.getPlayer().sendTitle("§a§lFIGHT!", "", 0, 40, 20);

		user.getPlayer().playSound(user.getPlayer(), Sound.ITEM_GOAT_HORN_SOUND_0, 1.0F, 1.0F);

		user.setUserGuard(UserGuard.Playing);
	}

	private void startMatch(Player player, Player clicked) {
		// This exists cause' I'm creating a relationship between a Warp object and DuelsGame
		// In order to have access to the Warp's locations file.
		
		this.teleportToLocation(player, "loc1");
		this.teleportToLocation(clicked, "loc2");

		preparePlayer(player);
		preparePlayer(clicked);

		matchMap.put(clicked, player);
		matchMap.put(player, clicked);
		
		player.showPlayer(Main.getInstance(), clicked);
		clicked.showPlayer(Main.getInstance(), player);
		
		new BukkitRunnable() {
			int i = 3;
			
			User playerUser = UserManager.getUser(player);
			User clickedUser = UserManager.getUser(clicked);
			
			public void run() {
				if (i == 0) {

					startMatchForPlayer(playerUser);
					startMatchForPlayer(clickedUser);

					this.cancel();
				} else {
					clicked.playSound(clicked, Sound.BLOCK_NOTE_BLOCK_BIT, 1.0F, 1.0F);
					player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BIT, 1.0F, 1.0F);
					clicked.sendTitle("§e§l" + i, "", 0, 20, 0);
					clicked.sendTitle("§e§l" + i, "", 0, 20, 0);
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
						clicked.playSound(clicked, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.0F);
						player.playSound(player, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.0F);
						clicked.sendMessage("§cTime is running out! End the battle before it's too late!");
						player.sendMessage("§cTime is running out! End the battle before it's too late!");
					}
					if (i == 1) {
						clicked.playSound(clicked, Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0F, 1.0F);
						player.playSound(player, Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0F, 1.0F);
						clicked.sendMessage("§cTime's up!");
						player.sendMessage("§cTime's up!");
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

	public void handleWin(Player loser) {
		if (matchMap.containsKey(loser)) {

			User userWinner = UserManager.getUser(matchMap.get(loser));

			userWinner.getPlayer().sendMessage("§aYou won!");
			userWinner.getPlayer().sendTitle("§lYOU WON!", "§fYou gained §a+3 Coins", 0, 40, 20);

			if (loser.isOnline()) {
				loser.sendMessage("§cYou lost!");
				loser.sendTitle("§c§lYOU LOST!", "§fThe battle is over.", 0, 40, 20);
			}

			matchMap.remove(userWinner.getPlayer());
			matchMap.remove(loser);

			userWinner.reset();
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		
		handleWin(player);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player player = (Player)e.getEntity();
		
		handleWin(player);
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
					
						player.sendMessage("§eYou challenged §b" + clicked.getName() + " §eto a duel");

						player.playSound(player, Sound.BLOCK_NOTE_BLOCK_HAT, 1.0F, 1.0F);
						
						clicked.sendMessage("§b" + player.getName() + " §echallenged you to a duel");

						clicked.playSound(player, Sound.BLOCK_NOTE_BLOCK_HAT, 1.0F, 1.0F);
						 
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
		}
	}
}
