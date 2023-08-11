package oldschoolproject.events.listeners.common;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.events.BaseListener;

public class LPlayerRespawn implements BaseListener {
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		User user = UserManager.getUser(e.getPlayer());
		
		e.setRespawnLocation(user.getWarp().getSpawnLocation());

		user.reset();
	}

}
