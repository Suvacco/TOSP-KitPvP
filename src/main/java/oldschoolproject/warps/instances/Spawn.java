package oldschoolproject.warps.instances;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.builders.ItemBuilder;
import oldschoolproject.events.BaseListener;
import oldschoolproject.warps.BaseWarp;

public class Spawn extends BaseWarp implements BaseListener {

	public Spawn() {
		super("Spawn", new ItemBuilder(Material.COMPASS).toItemStack());
	}

	@Override
	public void handlePlayerJoin(Player player) {

	}

	@Override
	public void handlePlayerQuit(Player player) { }

	@Override
	public void setDefaultItems(Player player) {
		player.getInventory().setItem(2, new ItemBuilder(Material.EMERALD).setName("§aShop").toItemStack());
		
		player.getInventory().setItem(4, new ItemBuilder(Material.CHEST).setName("§6Kits").toItemStack());
		
		player.getInventory().setItem(6, new ItemBuilder(Material.NETHER_STAR).setName("§bWarps").toItemStack());
	}
	
	@EventHandler
	public void spawnInteract(PlayerInteractEvent e) {
		User user = UserManager.getUser(e.getPlayer());
		
		if (!(user.getWarp() instanceof Spawn)) {
			return;
		}
		
		if (!user.isProtected()) {
			return;
		}
		
		if (e.getItem() == null) {
			return;
		}
		
		if (e.getItem().getType().equals(Material.CHEST)) {
			e.getPlayer().performCommand("kitinv");
			return;
		}
		
		if (e.getItem().getType().equals(Material.NETHER_STAR)) {
			e.getPlayer().performCommand("warpinv");
			return;
		}
	}
	
}
