package oldschoolproject.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import oldschoolproject.managers.InventoryManager;
import oldschoolproject.utils.loaders.listener.BaseListener;

public class LInventory extends BaseListener {
	
	@EventHandler
	public void onSelectKit(InventoryClickEvent e) {
		if (e.getClickedInventory() == InventoryManager.kitInventory) {
			e.setCancelled(true);
		}
	}

}
