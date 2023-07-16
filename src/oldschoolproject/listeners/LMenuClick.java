package oldschoolproject.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import oldschoolproject.users.User;
import oldschoolproject.users.managers.UserManager;
import oldschoolproject.utils.listeners.BaseListener;
import oldschoolproject.utils.menus.Menu;

public class LMenuClick extends BaseListener {
	
	@EventHandler
	public void menuClick(InventoryClickEvent e) {
		User user = UserManager.getUser((Player)e.getWhoClicked());
		
		if (!user.isProtected()) {
			return;
		}
		
		InventoryHolder holder = e.getInventory().getHolder();
		
		// Holder is not the actual holder, but the object inventory
		if (holder instanceof Menu) {
			
			Menu menu = (Menu) holder;
		
			if (e.getCurrentItem() == null) {
				return;
			}
			
			menu.handleInteraction(e);
			
			e.setCancelled(true);
		}
	}
}
