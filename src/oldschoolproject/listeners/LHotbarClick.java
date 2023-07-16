package oldschoolproject.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import oldschoolproject.users.User;
import oldschoolproject.users.managers.UserManager;
import oldschoolproject.utils.listeners.BaseListener;

public class LHotbarClick extends BaseListener {
	
	@EventHandler
	public void openKitSelector(PlayerInteractEvent e) {
		User user = UserManager.getUser(e.getPlayer());
		
		if (!user.isProtected()) {
			return;
		}
		
		if (e.getItem() == null) {
			return;
		}
		
		if (!e.getItem().getType().equals(Material.CHEST)) {
			return;
		}
		
		e.getPlayer().performCommand("kitinv");
		
		e.setCancelled(true);
	}
}
