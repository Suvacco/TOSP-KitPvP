package oldschoolproject.listeners.common;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import oldschoolproject.managers.UserManager;
import oldschoolproject.menus.ResoupMenu;
import oldschoolproject.users.User;
import oldschoolproject.utils.builders.ItemBuilder;
import oldschoolproject.utils.listeners.BaseListener;

public class LPlayerInteract implements BaseListener {
	
	@EventHandler
	public void clickSign(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign)e.getClickedBlock().getState();
				if (sign.getLine(1).equals("§6§l[§e§lOLD SCHOOL§6§l]")) {
					new ResoupMenu(UserManager.getUser(e.getPlayer())).open();
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void createSign(SignChangeEvent e) {
		if (e.getLine(0).equalsIgnoreCase("resoup")) {
			 if (!e.getPlayer().isOp()) {
				 return;
			 }
			 
			 Sign s = (Sign) e.getBlock().getState();
			
			 s.setLine(0, "");
			 s.setLine(1, "§6§l[§e§lOLD SCHOOL§6§l]");
			 s.setLine(2, "§a§lResoup");
			 s.setLine(3, "");
			 s.update(true);
			 
			 e.getPlayer().sendMessage("§aPlaca de resoup criada!");
			 e.setCancelled(true);
		}
	}

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
}
