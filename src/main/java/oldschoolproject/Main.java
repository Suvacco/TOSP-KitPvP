package oldschoolproject;

import oldschoolproject.databases.DatabaseLoader;
import oldschoolproject.managers.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import oldschoolproject.managers.UserManager;
import oldschoolproject.managers.WarpManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.AutoReloader;
import oldschoolproject.utils.commands.CommandLoader;
import oldschoolproject.utils.kits.KitLoader;
import oldschoolproject.utils.listeners.ListenerLoader;
import oldschoolproject.utils.warps.WarpLoader;

public class Main extends JavaPlugin {

	public static Main getInstance() {
		return getPlugin(Main.class);
	}

	@Override
	public void onEnable() {

		new CommandLoader();

		new ListenerLoader();

		new KitLoader();

		new WarpLoader();

		new DatabaseLoader();

		new AutoReloader();

		registerPlayers();
	}

	@Override
	public void onDisable() {
		unregisterPlayers();
	}

	// Debug Methods (Temporary)

	public void unregisterPlayers() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			Bukkit.getPluginManager().callEvent(new PlayerQuitEvent(player, ""));
		}
	}

	public void registerPlayers() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			Bukkit.getPluginManager().callEvent(new PlayerJoinEvent(player, ""));
		}
	}
}
