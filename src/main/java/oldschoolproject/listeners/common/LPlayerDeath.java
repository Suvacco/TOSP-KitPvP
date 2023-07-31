package oldschoolproject.listeners.common;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.warps.Spawn;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import oldschoolproject.utils.listeners.BaseListener;

public class LPlayerDeath implements BaseListener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		User user = UserManager.getUser(e.getEntity());

		if (user.getWarp() instanceof Spawn) {
			user.setDeaths(user.getDeaths() + 1);

			user.getPlayer().sendMessage("§cVocê morreu");
		}

		if (e.getEntity().getKiller() != null) {

			User killer = UserManager.getUser(e.getEntity().getKiller());

			killer.setKills(killer.getKills() + 1);

			killer.getPlayer().sendMessage("§aVocê matou o jogador " + user.getPlayer().getName());
		}

		e.setDeathMessage(null);
	}
}
