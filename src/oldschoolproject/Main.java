package oldschoolproject;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import oldschoolproject.managers.UserManager;
import oldschoolproject.utils.AutoReloader;
import oldschoolproject.utils.commands.CommandLoader;
import oldschoolproject.utils.kits.KitLoader;
import oldschoolproject.utils.listeners.ListenerLoader;

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
	
	/*
	 * Ideas
	 * 
	 * Skill could be an interface,
	 *  Kit and abstract class instead of Skill and Kits the enum.
	 *  But for now everythings good
	 *  
	 * Listener registration could be through Reflections to better coupling
	 */
	
	public static Main getInstance() {
		return getPlugin(Main.class);
	}
	
	public void onEnable() {
		
		new CommandLoader();
		
		new ListenerLoader();
		
		new KitLoader();
		
		new AutoReloader();
		
		// temp
		for (Player all : Bukkit.getOnlinePlayers()) {
			UserManager.registerUser(all);
			
			all.damage(20D);
		}
	}

}
