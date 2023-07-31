package oldschoolproject.listeners.common;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.users.User.UserState;
import oldschoolproject.utils.listeners.BaseListener;

public class LPlayerDamage implements BaseListener {
	
	@EventHandler
	public void playerDamageByEntity(EntityDamageByEntityEvent e) {
		User victim = UserManager.getUser((Player)e.getEntity());
		User attacker = UserManager.getUser((Player)e.getDamager());
		
		if (victim.getState() == UserState.Playing && attacker.getState() == UserState.Playing) {
			return;
		}
		
		if (victim.getWarp().equals(attacker.getWarp())) {
			return;
		}
		
		e.setCancelled(true);
	}

	@EventHandler
	public void playerDamage(EntityDamageEvent e) {
		User victim = UserManager.getUser((Player)e.getEntity());
		
		if (e.getFinalDamage() >= victim.getPlayer().getHealth()) {

			e.setCancelled(true);

			victim.reset();

			Bukkit.getServer().getPluginManager().callEvent(new PlayerDeathEvent(victim.getPlayer(), null, 0, null));
			return;
		}
		
		if (victim.getState() == UserState.Playing) {
			return;
		}
		
		e.setCancelled(true);
	}

}
