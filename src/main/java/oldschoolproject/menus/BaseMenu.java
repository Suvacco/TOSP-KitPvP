package oldschoolproject.menus;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import oldschoolproject.users.User;

public abstract class BaseMenu implements InventoryHolder {
	
	protected Inventory inventory;
	protected User holder;
	protected Integer slots;
	protected String title;
	
	public BaseMenu(User holder, Integer slots, String title) {
		this.holder = holder;
		this.slots = slots;
		this.title = title;
	}
	
	public void open() {
		this.inventory = Bukkit.createInventory(this, this.slots, this.title);
		
		this.setDefaultItems();
		
		this.holder.getPlayer().openInventory(this.inventory);
	}
	
	public abstract void setDefaultItems();
	
	public abstract void handleInteraction(InventoryClickEvent e);
			
	@Override
	public Inventory getInventory() {
		return this.inventory;
	}
}
