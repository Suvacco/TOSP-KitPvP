package oldschoolproject.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import oldschoolproject.managers.KitManager;
import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.builders.ItemBuilder;
import oldschoolproject.utils.kits.BaseKit;
import oldschoolproject.utils.listeners.BaseListener;

public class LKitInteraction implements BaseListener {
	
	@EventHandler
	public void onMoveThroughSponge(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		User user = UserManager.getUser(p);
		
		if (p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE) {
			KitManager.giveKit(user);
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
	
	@EventHandler
	public void onSkill(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		User user = UserManager.getUser(player);
		
		// Doesn't have kit
		if (!user.hasKit()) {
			return;
		}
		
		BaseKit kit = user.getKit();
		
		// Isn't holding ability item
		if (!player.getInventory().getItemInMainHand().isSimilar(kit.getSkillItem())) {
			return;
		}
		
		// Isn't on cooldown
		if (kit.isOnCooldown()) {
			player.sendMessage("§cCooldown: " + kit.getCooldownTimeFormatted());
			return;
		}
		
		e.setCancelled(true);
		
		kit.useSkill(e);
	}

}
