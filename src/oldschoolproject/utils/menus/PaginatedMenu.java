package oldschoolproject.utils.menus;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import oldschoolproject.users.User;
import oldschoolproject.utils.builders.ItemBuilder;

public abstract class PaginatedMenu extends BaseMenu {
	
	protected int page = 0;
	protected int index = 0;
	protected int maxItemsPerPage;
	protected int previousPageSlot, nextPageSlot;
	
	protected ItemStack nextPageBtn = new ItemBuilder(Material.LIME_CARPET).setName("§aProxima Página").toItemStack();
	protected ItemStack previousPageBtn = new ItemBuilder(Material.RED_CARPET).setName("§cPágina Anterior").toItemStack();

	public PaginatedMenu(User holder, Integer slots, String title, Integer previousPageSlot, Integer nextPageSlot) {
		super(holder, slots, title);
		this.previousPageSlot = previousPageSlot;
		this.nextPageSlot = nextPageSlot;
	}
	
	@Override
	public void open() {
		this.inventory = Bukkit.createInventory(this, this.slots, this.title);
		
		this.setDefaultItems();
		
		this.setPageItems();
		
		this.maxItemsPerPage = (int) Arrays.stream(this.inventory.getContents()).filter(item -> item == null).count();
		
		this.fillMenu();
		
		this.holder.getPlayer().openInventory(this.inventory);
	}
	
	public abstract void fillMenu();

	public void setPageItems() {
		this.inventory.setItem(previousPageSlot, previousPageBtn);
		this.inventory.setItem(nextPageSlot, nextPageBtn);
	}
	
	public int getMaxItemsPerPage() {
		return maxItemsPerPage;
	}
}
