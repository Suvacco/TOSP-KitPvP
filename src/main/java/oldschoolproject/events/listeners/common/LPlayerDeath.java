package oldschoolproject.events.listeners.common;

import oldschoolproject.events.custom.PlayerKillstreakEvent;
import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.users.UserStats;
import oldschoolproject.warps.instances.Spawn;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import oldschoolproject.events.BaseListener;

public class LPlayerDeath implements BaseListener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		User victim = UserManager.getUser(e.getEntity());

		// Bug when killing with tnt and fire charge

		if (victim.getWarp() instanceof Spawn) {

			victim.setStat(UserStats.DEATHS, (Integer)victim.getStat(UserStats.DEATHS) + 1);

			victim.getPlayer().sendMessage("§cVocê morreu");

			Bukkit.getPluginManager().callEvent(new PlayerKillstreakEvent(victim, PlayerKillstreakEvent.StreakAction.LOSE));
		}

		if (e.getEntity().getKiller() != null) {
			User killer = UserManager.getUser(e.getEntity().getKiller());

			killer.setStat(UserStats.KILLS, (Integer)killer.getStat(UserStats.KILLS) + 1);

			killer.getPlayer().sendMessage("§aVocê matou o jogador " + victim.getPlayer().getName());

			Bukkit.getPluginManager().callEvent(new PlayerKillstreakEvent(killer, PlayerKillstreakEvent.StreakAction.GAIN));
		}

		e.setDeathMessage(null);
	}
}
