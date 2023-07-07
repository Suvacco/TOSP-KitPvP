package oldschoolproject.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import oldschoolproject.entities.Kits;
import oldschoolproject.entities.User;
import oldschoolproject.managers.UserManager;
import oldschoolproject.utils.loaders.listener.BaseListener;

public class LSkill extends BaseListener {
	
	@EventHandler
	public void onSkill(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		User user = UserManager.getUser(player);
		
		// Doesn't have kit
		if (!user.hasKit()) {
			return;
		}
		
		Kits kit = user.getKit();
		
		// Isn't holding ability item
		if (!player.getInventory().getItemInMainHand().isSimilar(kit.getSkillItem())) {
			return;
		}
		
		// Isn't right clicking
		if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		
		// Isn't on cooldown
		if (kit.isOnCooldown()) {
			player.sendMessage("§cCooldown: " + kit.getCooldownTime());
			return;
		}
		
		e.setCancelled(true);
		
		kit.activateSkill(player);
	}

}
