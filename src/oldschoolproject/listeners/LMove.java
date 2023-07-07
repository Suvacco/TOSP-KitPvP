package oldschoolproject.listeners;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import oldschoolproject.entities.User;
import oldschoolproject.managers.KitManager;
import oldschoolproject.managers.UserManager;
import oldschoolproject.utils.loaders.listener.BaseListener;

public class LMove extends BaseListener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		User user = UserManager.getUser(p);
		
		if (p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE) {
			KitManager.giveKit(user);
		}
	}

}
