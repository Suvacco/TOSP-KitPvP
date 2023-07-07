package oldschoolproject.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import oldschoolproject.entities.User;
import oldschoolproject.managers.InventoryManager;
import oldschoolproject.managers.KitManager;
import oldschoolproject.managers.UserManager;
import oldschoolproject.utils.loaders.listener.BaseListener;

public class LInventory extends BaseListener {
	
	@EventHandler
	public void onSelectKit(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		User user = UserManager.getUser(p);
		
		if (e.getClickedInventory() == InventoryManager.kitInventory) {
			
			KitManager.setKit(user, e.getCurrentItem().getItemMeta().getDisplayName());
			
			e.setCancelled(true);
		}
	}

}
