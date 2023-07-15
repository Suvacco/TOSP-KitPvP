package oldschoolproject.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import oldschoolproject.entities.User;
import oldschoolproject.managers.UserManager;
import oldschoolproject.utils.loaders.listener.BaseListener;
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
		
		if (e.getCurrentItem() == null) {
			return;
		}
		
		Menu menu = (Menu) holder;
		
		menu.handleInteraction(e);
		
		e.setCancelled(true);
		
		}}
}
