package oldschoolproject.listeners.common;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.listeners.BaseListener;

public class LPlayerDeath implements BaseListener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		e.getDrops().clear();
	}
}
