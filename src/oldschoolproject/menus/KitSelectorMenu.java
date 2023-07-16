package oldschoolproject.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import oldschoolproject.kits.managers.Kit;
import oldschoolproject.kits.managers.KitEnum;
import oldschoolproject.kits.managers.KitManager;
import oldschoolproject.users.User;
import oldschoolproject.users.managers.UserManager;
import oldschoolproject.utils.builders.ItemBuilder;
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
		if (e.getCurrentItem().equals(nextPageBtn)) {
			if (!((index + 1) >= KitEnum.values().length)) {
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
		
		KitManager.setKit(user, e.getCurrentItem().getItemMeta().getDisplayName().substring(2));
		
		user.getPlayer().closeInventory();
	}

	@Override
	public void fillMenu() {
		KitEnum[] kits = KitEnum.values();
		
		for (int i = 0; i < getMaxItemsPerPage(); i++) {
			index = getMaxItemsPerPage() * page + i;
			
			if (index >= kits.length) {
				break;
			}
			
			if (kits[index] != null) {
			
				ItemStack item = kits[index].getStaticInstance().getKitSelectorItem();
				
				ItemMeta meta = item.getItemMeta();
				
				meta.setDisplayName("§6" + kits[index].getStaticInstance().getName());
				
				item.setItemMeta(meta);
			
				this.inventory.addItem(item);
			}
		}
	}
}
