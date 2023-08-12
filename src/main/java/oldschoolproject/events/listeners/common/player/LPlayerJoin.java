package oldschoolproject.events.listeners.common.player;

import oldschoolproject.managers.DatabaseManager;
import oldschoolproject.managers.TagManager;
import oldschoolproject.managers.WarpManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.events.BaseListener;

public class LPlayerJoin implements BaseListener {

	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player player = e.getPlayer();

		User user = new User(player);

		UserManager.registerUser(user);

		// Download all data from cloud to user object
		DatabaseManager.loadUser(user);

		// Reset player warp
		WarpManager.setWarp(user, WarpManager.findWarp("Spawn"));

		// Set players tag on the server
		TagManager.setPrefix(user, user.getUserRank().getTag());

		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 15F, 2F);

		player.setPlayerListHeaderFooter(
				"\n §6§lTHE §e§lOLD SCHOOL §6§lPROJECT \n \n §7- §a§lKITPVP §7- \n",
				"\n §e§lGitHub \n §bgithub.com/Suvacco/TOSP-KitPvP \n");
		
		player.sendTitle("§6§lTHE §e§lOS §6§lPROJECT", "§aBem-vindo " + player.getName() + "!", 1, 20 * 2, 10);

		e.setJoinMessage(null);
	}

}
