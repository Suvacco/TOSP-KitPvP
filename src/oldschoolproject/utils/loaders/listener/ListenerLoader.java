package oldschoolproject.utils.loaders.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import oldschoolproject.Main;
import oldschoolproject.utils.ClassGetter;

public class ListenerLoader {
	
	public ListenerLoader() {
		loadListenersAndRegister();
	}
	
	@SuppressWarnings("deprecation")
	public static void loadListenersAndRegister() {
		int i = 0;
		for (Class<?> baseListener : (Iterable<Class<?>>) ClassGetter.getClassesForPackage(Main.getInstance(), "oldschoolproject.listeners")) {
			if (BaseListener.class.isAssignableFrom(baseListener)) {
				try {
					BaseListener listener;
					try {
						listener = (BaseListener) baseListener.getConstructor(new Class[] { Main.class }).newInstance(new Object[] { Main.getInstance() });
					} catch (Exception e) {
						listener = (BaseListener) baseListener.newInstance();
					}
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
