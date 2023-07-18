package oldschoolproject.listeners.common;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.listeners.BaseListener;

public class LPlayerRespawn implements BaseListener {
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		User user = UserManager.getUser(e.getPlayer());
		
		user.reset();

		e.setRespawnLocation(Bukkit.getWorld("world").getSpawnLocation());
	}

}
