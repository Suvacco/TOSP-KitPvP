package oldschoolproject.utils.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import oldschoolproject.Main;
import oldschoolproject.utils.ClassGetter;

public class ListenerLoader {
	
	public ListenerLoader() {
		loadListenersAndRegister();
	}
	
	public void loadListenersAndRegister() {
		int i = 0;
		
		String srcPackage = ListenerLoader.class.getPackage().getName().substring(0, ListenerLoader.class.getPackage().getName().indexOf('.'));
		
		for (Class<?> baseListener : (Iterable<Class<?>>) ClassGetter.getClassesForPackage(Main.getInstance(), srcPackage)) {
			if (BaseListener.class.isAssignableFrom(baseListener) && !baseListener.equals(BaseListener.class)) {
				try {
					BaseListener listener = (BaseListener) baseListener.getConstructor().newInstance();
					Bukkit.getPluginManager().registerEvents((Listener) listener, Main.getInstance());
				} catch (Exception e) {
					e.printStackTrace();
					System.out.print("[ListenerLoader] Error when registering the listener " + baseListener.getSimpleName());
				}
				i++;
			}
		}
		Main.getInstance().getLogger().info("[ListenerLoader] " + i + " listeners loaded");
	}

}
