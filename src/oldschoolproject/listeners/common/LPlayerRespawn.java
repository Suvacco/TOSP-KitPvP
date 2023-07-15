package oldschoolproject.listeners.common;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

import oldschoolproject.entities.User;
import oldschoolproject.managers.UserManager;
import oldschoolproject.utils.loaders.listener.BaseListener;

public class LPlayerRespawn extends BaseListener {
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		User user = UserManager.getUser(e.getPlayer());
		
		user.reset();

		e.setRespawnLocation(Bukkit.getWorld("world").getSpawnLocation());
	}

}
