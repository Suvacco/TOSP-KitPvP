package oldschoolproject.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;

import oldschoolproject.entities.Kit;
import oldschoolproject.utils.loaders.listener.BaseListener;

public class InventoryManager extends BaseListener {
	
	public static Inventory kitInventory;
	
	public static void openInventory(Player p) {
		kitInventory = Bukkit.createInventory(null, 54, "Kits");
		
		for (Kit kit : Kit.values()) {
			if (p.hasPermission("kit." + kit.name().toLowerCase())) {
				kitInventory.addItem(kit.getSkillItem());
			}
		}
		
		p.openInventory(kitInventory);
	}

}
