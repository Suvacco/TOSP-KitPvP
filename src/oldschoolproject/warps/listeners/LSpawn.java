package oldschoolproject.warps.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.listeners.BaseListener;
import oldschoolproject.warps.Spawn;

public class LSpawn implements BaseListener {
	
	@EventHandler
	public void spawnInteract(PlayerInteractEvent e) {
		User user = UserManager.getUser(e.getPlayer());
		
		if (!(user.getWarp() instanceof Spawn)) {
			return;
		}
		
		if (!user.isProtected()) {
			return;
		}
		
		if (e.getItem() == null) {
			return;
		}
		
		if (e.getItem().getType().equals(Material.CHEST)) {
			e.getPlayer().performCommand("kitinv");
			return;
		}
		
		if (e.getItem().getType().equals(Material.NETHER_STAR)) {
			e.getPlayer().performCommand("warpinv");
			return;
		}
		
		e.setCancelled(true);
	}

}
