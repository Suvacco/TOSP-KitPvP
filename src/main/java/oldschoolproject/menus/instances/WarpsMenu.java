package oldschoolproject.menus.instances;

import oldschoolproject.exceptions.OperationFailException;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import oldschoolproject.managers.UserManager;
import oldschoolproject.managers.WarpManager;
import oldschoolproject.users.User;
import oldschoolproject.menus.BaseMenu;
import oldschoolproject.warps.BaseWarp;
import oldschoolproject.warps.WarpLoader;

public class WarpsMenu extends BaseMenu {
	
	public WarpsMenu(User holder) {
		super(holder, 27, "Choose the warp to teleport:");
	}

	@Override
	public void setDefaultItems() {
		BaseWarp[] warps = WarpLoader.getWarpInstances().toArray(new BaseWarp[0]);
		
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

		try {
			WarpManager.changeWarp(user, e.getCurrentItem().getItemMeta().getDisplayName().substring(2));
		} catch (OperationFailException ex) {
			user.getPlayer().sendMessage(ex.getMessage());
		}

		user.getPlayer().closeInventory();
	}

}
