package oldschoolproject.listeners.kits;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.kits.BaseKit;
import oldschoolproject.utils.listeners.BaseListener;

public class LKitInteract implements BaseListener {
	
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
