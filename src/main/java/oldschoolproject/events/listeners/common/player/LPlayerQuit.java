package oldschoolproject.events.listeners.common.player;

import oldschoolproject.events.custom.PlayerKillstreakEvent;
import oldschoolproject.managers.DatabaseManager;
import oldschoolproject.users.User;
import oldschoolproject.users.UserStats;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import oldschoolproject.managers.UserManager;
import oldschoolproject.events.BaseListener;

public class LPlayerQuit implements BaseListener {
	
	@EventHandler
	public void quit(PlayerQuitEvent e) {
		User user = UserManager.getUser(e.getPlayer());

		new PlayerKillstreakEvent(user, PlayerKillstreakEvent.StreakAction.LOSE);

		DatabaseManager.saveUser(user);

		UserManager.unregisterUser(e.getPlayer());
		
		e.setQuitMessage(null);
	}
}
