package oldschoolproject.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import oldschoolproject.managers.KitManager;
import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.builders.ItemBuilder;
import oldschoolproject.utils.kits.BaseKit;
import oldschoolproject.utils.kits.KitLoader;
import oldschoolproject.utils.menus.PaginatedMenu;

public class KitSelectorMenu extends PaginatedMenu {

	public KitSelectorMenu(User holder) {
		super(holder, 54, "Selecione seu kit:", 0, 8);
	}
	
	@Override
	public void setDefaultItems() {
		for (int i = 1; i < 8; i++) {
			this.getInventory().setItem(i, new ItemBuilder(Material.GLASS_PANE).toItemStack());
		}
	}

	@Override
	public void handleInteraction(InventoryClickEvent e) {
		e.setCancelled(true);
		
		if (e.getCurrentItem().equals(nextPageBtn)) {
			if (!((index + 1) >= KitLoader.getKitInstances().size())) {
				page++;
				this.open();
			}
			return;
		}
		
		if (e.getCurrentItem().equals(previousPageBtn)) {
			if (page != 0) {
				page--;
				this.open();
			}
			return;
		}
		
		if (e.getCurrentItem().getType().equals(Material.GLASS_PANE)) {
			return;
		}
		
		User user = UserManager.getUser((Player)e.getWhoClicked());
		
//		KitManager.setKit(user, e.getCurrentItem().getItemMeta().getDisplayName().substring(2));
		
		user.getPlayer().performCommand("kit " + e.getCurrentItem().getItemMeta().getDisplayName().substring(2));
		
		user.getPlayer().closeInventory();
	}

	@Override
	public void fillMenu() {
		BaseKit[] allKits = KitLoader.getKitInstances().toArray(new BaseKit[KitLoader.getKitInstances().size()]);
		
		for (int i = 0; i < getMaxItemsPerPage(); i++) {
			
			index = getMaxItemsPerPage() * page + i;
			
			if (index >= allKits.length) {
				break;
			}
			
			if (allKits[index] != null) {
			
				ItemStack item = allKits[index].getMenuItem();
				
				ItemMeta meta = item.getItemMeta();
				
				meta.setDisplayName("§6" + allKits[index].getName());
				
				item.setItemMeta(meta);
			
				this.inventory.addItem(item);
			}
		}
	}
}
