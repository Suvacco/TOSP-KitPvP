package oldschoolproject;

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
	
	/*
	 * To-Do List
	 * 
	 * - 1v1 (Duels)
	 * - SQL / MongoDB
	 * - Scoreboard
	 * - Warps 
	 * - Ranks
	 * - Elos
	 * - Economy
	 * - Kill Streak
	 * - Kits Skills
	 * - Soup Signs
	 * - Boss Bar
	 * - Holograms
	 * - Tags
	 * - Tab
	 * - Title Messages
	 * 
	 */
	
	public static Main getInstance() {
		return getPlugin(Main.class);
	}
	
	public void onEnable() {
		
		new CommandLoader();
		
		new ListenerLoader();
		
		new KitLoader();
		
		new WarpLoader();
		
		new AutoReloader();
		
		// temp
		for (Player all : Bukkit.getOnlinePlayers()) {
			User user = UserManager.registerUser(all);
			
			WarpManager.setWarp(user, WarpManager.findWarp("Spawn"));
			
			all.damage(20D);
		}
	}

}
