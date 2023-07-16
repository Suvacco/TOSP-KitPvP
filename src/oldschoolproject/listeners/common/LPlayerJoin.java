package oldschoolproject.listeners.common;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import oldschoolproject.users.managers.UserManager;
import oldschoolproject.utils.listeners.BaseListener;

public class LPlayerJoin extends BaseListener {
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		UserManager.registerUser(player).reset();
		
		player.teleport(Bukkit.getWorld("world").getSpawnLocation());
		
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 15F, 0F);
		
		player.sendMessage("§aBem-vindo, " + player.getName());
		
		e.setJoinMessage(null);
	}

}
