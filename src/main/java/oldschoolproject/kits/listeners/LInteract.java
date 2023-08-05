package oldschoolproject.kits.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.kits.BaseKit;
import oldschoolproject.events.BaseListener;

public class LInteract implements BaseListener {
	
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
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§cSeu kit está em cooldown!"));
			return;
		}
		
		e.setCancelled(true);
		
		kit.useSkill(e);
	}

}
