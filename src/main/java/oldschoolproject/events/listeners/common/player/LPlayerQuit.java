package oldschoolproject.events.listeners.common.player;

import oldschoolproject.managers.DatabaseManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import oldschoolproject.managers.UserManager;
import oldschoolproject.events.BaseListener;

public class LPlayerQuit implements BaseListener {
	
	@EventHandler
	public void quit(PlayerQuitEvent e) {
		DatabaseManager.saveUser(UserManager.getUser(e.getPlayer()));

		UserManager.unregisterUser(e.getPlayer());
		
		e.setQuitMessage(null);
	}

}
