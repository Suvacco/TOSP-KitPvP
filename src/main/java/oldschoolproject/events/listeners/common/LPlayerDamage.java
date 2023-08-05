package oldschoolproject.events.listeners.common;

import oldschoolproject.users.UserGuard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.events.BaseListener;

import java.util.Arrays;

public class LPlayerDamage implements BaseListener {
	
	@EventHandler
	public void playerDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player & e.getDamager() instanceof Player) {
			User victim = UserManager.getUser((Player) e.getEntity());
			User attacker = UserManager.getUser((Player) e.getDamager());

			if (victim.getUserGuard() == UserGuard.Playing && attacker.getUserGuard() == UserGuard.Playing
					&& victim.getWarp().equals(attacker.getWarp())) {
				return;
			}

			e.setCancelled(true);
		}
	}

	@EventHandler
	public void playerDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			User victim = UserManager.getUser((Player) e.getEntity());

			if (e.getFinalDamage() >= victim.getPlayer().getHealth()) {

				e.setCancelled(true);

				victim.reset();

				Bukkit.getServer().getPluginManager().callEvent(new PlayerDeathEvent(
						victim.getPlayer(),
						Arrays.asList(victim.getPlayer().getInventory().getContents()),
						0,
						null));
				return;
			}

			if (victim.getUserGuard() == UserGuard.Playing) {
				return;
			}

			e.setCancelled(true);
		}
	}

}
