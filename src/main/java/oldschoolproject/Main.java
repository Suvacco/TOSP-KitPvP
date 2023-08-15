package oldschoolproject;

import oldschoolproject.databases.DatabaseLoader;
import oldschoolproject.holograms.HologramLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import oldschoolproject.utils.AutoReloader;
import oldschoolproject.commands.CommandLoader;
import oldschoolproject.kits.KitLoader;
import oldschoolproject.events.ListenerLoader;
import oldschoolproject.feast.FeastLoader;
import oldschoolproject.warps.WarpLoader;

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

		new HologramLoader();
		
		new FeastLoader();

//		new ScoreboardLoader();

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
