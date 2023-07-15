package oldschoolproject.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;

import oldschoolproject.entities.User;
import oldschoolproject.managers.UserManager;
import oldschoolproject.utils.loaders.listener.BaseListener;

public class LDrop extends BaseListener {
	
	@EventHandler
	public void onDropProtected(PlayerDropItemEvent e) {
		User user = UserManager.getUser(e.getPlayer());
		
		if (!user.isProtected()) {
			return;
		}
		
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDropSkillItem(PlayerDropItemEvent e) {
		User user = UserManager.getUser(e.getPlayer());
		
		if (!user.hasKit()) {
			return;
		}
		
		if (!(e.getItemDrop().getItemStack().equals(user.getKit().getSkillItem()) ||
				e.getItemDrop().getItemStack().getType().equals(Material.STONE_SWORD) ||
				e.getItemDrop().getItemStack().getType().equals(Material.WOODEN_SWORD))) {
			return;
		}
		
		e.setCancelled(true);
	}
	
	@EventHandler
	public void itemDropRemover(PlayerDropItemEvent e) {
		e.getItemDrop().remove();
	}
}
