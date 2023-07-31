package oldschoolproject;

import oldschoolproject.databases.DatabaseLoader;
import oldschoolproject.managers.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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

	// Debug Methods (Temporary)

	public void registerPlayers() {
		for (Player all : Bukkit.getOnlinePlayers()) {
			User user = UserManager.registerUser(all);

			WarpManager.setWarp(user, WarpManager.findWarp("Spawn"));

			all.damage(20D);
		}
	}
}
