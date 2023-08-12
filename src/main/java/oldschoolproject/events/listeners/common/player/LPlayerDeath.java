package oldschoolproject.events.listeners.common.player;

import oldschoolproject.Main;
import oldschoolproject.events.custom.PlayerKillstreakEvent;
import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.users.UserStats;
import oldschoolproject.warps.instances.Spawn;
import org.bukkit.Bukkit;
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
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class LPlayerDeath implements BaseListener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player victimPlayer = (Player) e.getEntity();

		User victim = UserManager.getUser(victimPlayer);

		if (victim.getWarp() instanceof Spawn) {

			victim.setStat(UserStats.DEATHS, (Integer) victim.getStat(UserStats.DEATHS) + 1);

			victim.getPlayer().sendMessage("§cVocê morreu");

			Bukkit.getPluginManager().callEvent(new PlayerKillstreakEvent(victim, PlayerKillstreakEvent.StreakAction.LOSE));

			Player killerPlayer = (Player) e.getEntity().getKiller();

			if (killerPlayer == null) {

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

				User killer = UserManager.getUser(killerPlayer);

				killer.setStat(UserStats.KILLS, (Integer) killer.getStat(UserStats.KILLS) + 1);

				killer.getPlayer().sendMessage("§aVocê matou o jogador " + victim.getPlayer().getName());

				Bukkit.getPluginManager().callEvent(new PlayerKillstreakEvent(killer, PlayerKillstreakEvent.StreakAction.GAIN));
			}

			e.setDeathMessage(null);
		}

		new BukkitRunnable() {
			@Override
			public void run() {
				victimPlayer.spigot().respawn();
			}
		}.runTask(Main.getInstance());
	}
}
