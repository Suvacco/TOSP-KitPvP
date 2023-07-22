package oldschoolproject.listeners.common;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import oldschoolproject.managers.UserManager;
import oldschoolproject.managers.WarpManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.listeners.BaseListener;

public class LPlayerJoin implements BaseListener {
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		User user = UserManager.registerUser(player);
		
		WarpManager.setWarp(user, WarpManager.findWarp("Spawn"));
		
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 15F, 0F);
		
		player.sendMessage("§aBem-vindo, " + player.getName());
		
		e.setJoinMessage(null);
	}

}
