package oldschoolproject.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import oldschoolproject.managers.User;
import oldschoolproject.managers.UserManager;
import oldschoolproject.utils.loaders.listener.BaseListener;

public class LJoin extends BaseListener {
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		UserManager.registerUser(e.getPlayer());
		
	}

}
