package oldschoolproject.events.listeners.common.player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import oldschoolproject.Main;
import oldschoolproject.events.custom.PlayerKillstreakEvent;
import oldschoolproject.kits.instances.Avatar;
import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.users.UserStats;
import oldschoolproject.warps.instances.Spawn;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import oldschoolproject.events.BaseListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class LPlayerDeath implements BaseListener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setDeathMessage(null);

		Player victimPlayer = (Player) e.getEntity();

		User victim = UserManager.getUser(victimPlayer);

		if (victim.getWarp() instanceof Spawn) {

			victim.setStat(UserStats.DEATHS, (Integer) victim.getStat(UserStats.DEATHS) + 1);

			victim.dieEffect();

			new BukkitRunnable() {
				@Override
				public void run() {
					victimPlayer.spigot().respawn();

					Bukkit.getPluginManager().callEvent(new PlayerKillstreakEvent(victim, PlayerKillstreakEvent.StreakAction.LOSE));

					victim.getPlayer().playSound(victim.getPlayer(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 15.0F, 1.5F);
				}
			}.runTask(Main.getInstance());

			Player killerPlayer = (Player) e.getEntity().getKiller();

			if (killerPlayer == null) {

				// Killer null BUT indirectly killed by a player
				if (victimPlayer.getLastDamageCause() instanceof EntityDamageByEntityEvent) {

					EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) victimPlayer.getLastDamageCause();

					if (damageEvent.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {

						if (damageEvent.getDamager() instanceof TNTPrimed) {

							TNTPrimed tntPrimed = (TNTPrimed) damageEvent.getDamager();

							killerPlayer = (Player) tntPrimed.getSource();

						} else {

							// Explosion damage
							killerPlayer = (Player) damageEvent.getDamager();
						}
					}
				}
			}

			if (killerPlayer != victimPlayer && killerPlayer != null) {

				victim.getPlayer().sendMessage("§cYou died to \"" + killerPlayer.getName() + "\"");

				victim.getPlayer().sendTitle("§c§lYOU DIED!", "§fYou died to §c" + killerPlayer.getName(), 0, 45, 20);

				User killer = UserManager.getUser(killerPlayer);

				killer.setStat(UserStats.KILLS, (Integer) killer.getStat(UserStats.KILLS) + 1);

				killer.setStat(UserStats.COINS, (Integer) killer.getStat(UserStats.COINS) + 3);

				killer.getPlayer().sendMessage("§aYou killed \"" + victim.getPlayer().getName() + "\"");

				killer.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+3 Coins"));

				killer.getPlayer().playSound(killer.getPlayer(), Sound.BLOCK_NOTE_BLOCK_PLING, 15.0F, 1.5F);

				Bukkit.getPluginManager().callEvent(new PlayerKillstreakEvent(killer, PlayerKillstreakEvent.StreakAction.GAIN));

				return;
			}

			victim.getPlayer().sendMessage("§cYou died!");

			victim.getPlayer().sendTitle("§c§lYOU DIED!", "You killed yourself...", 0, 45, 20);
		}
	}
}
