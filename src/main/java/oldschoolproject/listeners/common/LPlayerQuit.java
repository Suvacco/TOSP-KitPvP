package oldschoolproject.listeners.common;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import oldschoolproject.managers.UserManager;
import oldschoolproject.utils.listeners.BaseListener;

public class LPlayerQuit implements BaseListener {
	
	@EventHandler
	public void quit(PlayerQuitEvent e) {
		UserManager.unregisterUser(e.getPlayer());
		
		e.setQuitMessage(null);
	}

}
