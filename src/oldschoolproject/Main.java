package oldschoolproject;

import org.bukkit.plugin.java.JavaPlugin;

import oldschoolproject.utils.loaders.AutoReloader;
import oldschoolproject.utils.loaders.command.CommandLoader;
import oldschoolproject.utils.loaders.listener.ListenerLoader;

public class Main extends JavaPlugin {
	
	public static Main getInstance() {
		return getPlugin(Main.class);
	}
	
	public void onEnable() {
		
		new CommandLoader();
		
		new ListenerLoader();
		
		new AutoReloader();
		
	}

}
