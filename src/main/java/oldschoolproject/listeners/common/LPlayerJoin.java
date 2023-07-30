package oldschoolproject.listeners.common;

import oldschoolproject.managers.DatabaseManager;
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
		
		player.setPlayerListHeaderFooter(
				"\n §6§lTHE §e§lOLD SCHOOL §6§lPROJECT \n \n §7- §a§lKITPVP §7- \n",
				"\n §e§lGitHub \n §bgithub.com/Suvacco/TOSP-KitPvP \n");
		
		player.sendTitle("§6§lTHE §e§lOS §6§lPROJECT", "§aBem-vindo " + player.getName() + "!", 1, 20 * 2, 10);
		
		e.setJoinMessage(null);
	}

}
