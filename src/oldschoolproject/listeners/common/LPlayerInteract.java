package oldschoolproject.listeners.common;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.builders.ItemBuilder;
import oldschoolproject.utils.listeners.BaseListener;

public class LPlayerInteract implements BaseListener {

	@EventHandler
	public void soupHeal(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		User user = UserManager.getUser(player);
		
		if (user.isProtected()) {
			return;
		}
		
		if (e.getItem() == null) {
			return;
		}
		
		if (!e.getItem().getType().equals(Material.MUSHROOM_STEW)) {
			return;
		}
		
		if (player.getHealth() == 20.0D) {
			return;
		}
	
		player.setHealth(player.getHealth() + 6.5D > 20D ?
					player.getHealth() + (20D - player.getHealth()) 
					: 
					player.getHealth() + 6.5D
			);
		
		player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 1.0F, 1.0F);
		player.getInventory().setItemInMainHand(new ItemBuilder(Material.BOWL).setName("§7Tigela").toItemStack());
	}
	
	@EventHandler
	public void openKitSelector(PlayerInteractEvent e) {
		User user = UserManager.getUser(e.getPlayer());
		
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
		
		e.setCancelled(true);
	}
}
