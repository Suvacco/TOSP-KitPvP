package oldschoolproject.events.listeners.common.player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import oldschoolproject.Main;
import oldschoolproject.feast.Feast;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import oldschoolproject.managers.UserManager;
import oldschoolproject.menus.instances.ResoupMenu;
import oldschoolproject.users.User;
import oldschoolproject.utils.builders.ItemBuilder;
import oldschoolproject.events.BaseListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class LPlayerInteract implements BaseListener {
	
	@EventHandler
	public void clickSign(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign)e.getClickedBlock().getState();
				if (sign.getLine(1).equals("§6§l[§e§lOLD SCHOOL§6§l]")) {
					new ResoupMenu(UserManager.getUser(e.getPlayer())).open();
					e.getPlayer().playSound(e.getPlayer(), Sound.BLOCK_CHEST_OPEN, 1.0F, 1.0F);
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void createSign(SignChangeEvent e) {
		if (e.getLine(0).equalsIgnoreCase("resoup")) {
			 if (!e.getPlayer().isOp()) {
				 return;
			 }
			 
			 Sign s = (Sign) e.getBlock().getState();
			
			 s.setLine(0, "");
			 s.setLine(1, "§6§l[§e§lOLD SCHOOL§6§l]");
			 s.setLine(2, "§a§lResoup");
			 s.setLine(3, "");
			 s.update(true);
			 
			 e.getPlayer().sendMessage("§aResoup sign created!");
			 e.setCancelled(true);
		}
	}

	@EventHandler
	public void soupHeal(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		User user = UserManager.getUser(player);
		
		if (user.isProtected()) {
			return;
		}
		
		if (e.getItem() == null) {
			return;
		}
		
		if (!e.getItem().getType().equals(Material.MUSHROOM_STEW)) {
			return;
		}
		
		if (player.getHealth() == 20.0D) {
			return;
		}

		double heal = player.getHealth() + 7D > 20D ? player.getHealth() + (20D - player.getHealth()) : player.getHealth() + 7D;

		double playerHealthBefore = player.getHealth();
	
		player.setHealth(heal);

		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§4+" + ((player.getHealth() - playerHealthBefore) / 2)  + " §4❤"));
		player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 1.0F, 1.0F);
		player.getInventory().setItemInMainHand(new ItemBuilder(Material.BOWL).setName("§7Bowl").toItemStack());
	}

	@EventHandler
	public void playerIgniteInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		ItemStack itemInHand = p.getInventory().getItemInMainHand();

		if (Arrays.stream(Feast.getDefaultLoot()).noneMatch(item -> item.isSimilar(itemInHand))) {
			return;
		}

		// Ignite fireball
		if (p.getInventory().getItemInMainHand().getType() == Material.FIRE_CHARGE) {

			if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				return;
			}

			Fireball fireball = e.getPlayer().launchProjectile(Fireball.class);
			fireball.setShooter(e.getPlayer());
			fireball.setVelocity(e.getPlayer().getEyeLocation().getDirection().multiply(1.5));

			new BukkitRunnable() {
				int ticksPassed = 0;

				public void run() {

					if (fireball.isValid()) {

						if (ticksPassed >= 12) { // 1.5s
							fireball.getWorld().createExplosion(fireball.getLocation().getX(), fireball.getLocation().getY(), fireball.getLocation().getZ(), 4.0f, false, false, (Entity) fireball.getShooter());
							fireball.remove();
							cancel();
						}

						fireball.getWorld().playSound(fireball.getLocation(), Sound.ENTITY_GENERIC_BURN, 2.0F, 1.5F);
						fireball.getWorld().spawnParticle(Particle.FLAME, fireball.getLocation().clone(), 30);
						ticksPassed++;
					}
				}
			}.runTaskTimer(Main.getInstance(), 0, (long) 3);

			itemInHand.setAmount(itemInHand.getAmount() - 1);

			e.setCancelled(true);
		}

		// Ignite TNT
		if (p.getInventory().getItemInMainHand().getType() == Material.TNT) {

			if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				return;
			}

			TNTPrimed tntPrimed = e.getClickedBlock().getWorld().spawn(e.getClickedBlock().getRelative(e.getBlockFace()).getLocation().clone().add(0.5, 0.0, 0.5), TNTPrimed.class);
			tntPrimed.setFuseTicks(20 * 2);
			tntPrimed.setSource(p);

			new BukkitRunnable() {
				float soundPitch = 0.5F;

				public void run() {

					if (soundPitch > 0.9F) {
						cancel();
					}

					tntPrimed.getWorld().playSound(tntPrimed.getLocation(), Sound.ENTITY_TNT_PRIMED, 1.5F, soundPitch);
					tntPrimed.getWorld().spawnParticle(Particle.LAVA, tntPrimed.getLocation().clone().add(0, 1, 0), 15);

					soundPitch += 0.1F;
				}
			}.runTaskTimer(Main.getInstance(), 0, (long)6.5);

			itemInHand.setAmount(itemInHand.getAmount() - 1);

			e.setCancelled(true);
		}
	}
}
