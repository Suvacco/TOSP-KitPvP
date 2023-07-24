package oldschoolproject.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import oldschoolproject.managers.UserManager;
import oldschoolproject.managers.WarpManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.menus.BaseMenu;
import oldschoolproject.utils.warps.BaseWarp;
import oldschoolproject.utils.warps.WarpLoader;

public class WarpsMenu extends BaseMenu {
	
	public WarpsMenu(User holder) {
		super(holder, 27, "Clique na warp que deseja ir:");
	}

	@Override
	public void setDefaultItems() {
		BaseWarp[] warps = WarpLoader.getWarpInstances().toArray(new BaseWarp[WarpLoader.getWarpInstances().size()]);
		
		for (int j = 10; j < 16 && j - 10 < warps.length; j++) {
			ItemStack item = warps[j - 10].getMenuItem();
			
			ItemMeta meta = item.getItemMeta();
			
			meta.setDisplayName("§b" + warps[j - 10].getName());
			
			item.setItemMeta(meta);
			
		    this.getInventory().setItem(j, item);
		}
	}

	@Override
	public void handleInteraction(InventoryClickEvent e) {
		e.setCancelled(true);
		
		User user = UserManager.getUser((Player)e.getWhoClicked());
		
		WarpManager.changeWarp(user, e.getCurrentItem().getItemMeta().getDisplayName().substring(2));
		
		user.getPlayer().closeInventory();
	}

}
