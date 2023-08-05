package oldschoolproject.menus.instances;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import oldschoolproject.users.User;
import oldschoolproject.utils.builders.ItemBuilder;
import oldschoolproject.menus.BaseMenu;

public class ResoupMenu extends BaseMenu {

	public ResoupMenu(User holder) {
		super(holder, 36, "Resoup");
	}

	@Override
	public void setDefaultItems() {
		ItemStack[] pattern = {
				new ItemBuilder(Material.BROWN_MUSHROOM, 64).setName("§eCogumelo Marrom").toItemStack(),
				new ItemBuilder(Material.RED_MUSHROOM, 64).setName("§eCogumelo Vermelho").toItemStack(),
				new ItemBuilder(Material.BOWL, 64).setName("§eTigela").toItemStack()
		};
		
		for (int slot = 0, index = 0; slot < this.getInventory().getSize(); slot++) {
			if (index >= pattern.length) {
				index = 0;
			}
			
			this.getInventory().setItem(slot, pattern[index]);
			index++;
		}
	}

	@Override
	public void handleInteraction(InventoryClickEvent e) { }

}
