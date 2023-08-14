package oldschoolproject.menus.instances;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.builders.ItemBuilder;
import oldschoolproject.kits.BaseKit;
import oldschoolproject.kits.KitLoader;
import oldschoolproject.menus.PaginatedMenu;

public class KitSelectorMenu extends PaginatedMenu {

	User holder;

	public KitSelectorMenu(User holder) {
		super(holder, 54, "Selecione seu kit:", 0, 8);
		this.holder = holder;
	}
	
	@Override
	public void setDefaultItems() {
		for (int i = 1; i < 8; i++) {
			this.getInventory().setItem(i, new ItemBuilder(Material.GLASS_PANE).toItemStack());
		}
	}

	@Override
	public void handleItemClick(InventoryClickEvent e) {
		e.setCancelled(true);
		
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
		BaseKit[] allKits = KitLoader.getKitInstances().toArray(new BaseKit[0]);
		
		for (int i = 0; i < getMaxItemsPerPage(); i++) {
			
			index = getMaxItemsPerPage() * page + i;
			
			if (index >= allKits.length) {
				break;
			}
			
			if (allKits[index] != null) {

				if (this.holder.getPlayer().hasPermission("perm.kit." + allKits[index].getName().toLowerCase()) ||
						this.holder.getPlayer().hasPermission("rank.kit." + allKits[index].getName().toLowerCase())) {

					ItemStack item = allKits[index].getMenuItem();

					ItemMeta meta = item.getItemMeta();

					meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

					meta.setDisplayName("§6" + allKits[index].getName());

					item.setItemMeta(meta);

					this.inventory.addItem(item);
				}
			}
		}
	}
}
