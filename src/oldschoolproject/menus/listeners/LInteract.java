package oldschoolproject.menus.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.listeners.BaseListener;
import oldschoolproject.utils.menus.BaseMenu;

public class LInteract implements BaseListener {
	
	@EventHandler
	public void menuClick(InventoryClickEvent e) {
		User user = UserManager.getUser((Player)e.getWhoClicked());
		
		if (!user.isProtected()) {
			return;
		}
		
		InventoryHolder holder = e.getInventory().getHolder();
		
		// Holder is not the actual holder, but the object inventory
		if (holder instanceof BaseMenu) {
			
			BaseMenu menu = (BaseMenu) holder;
		
			if (e.getCurrentItem() == null) {
				return;
			}
			
			menu.handleInteraction(e);
		}
	}
}
