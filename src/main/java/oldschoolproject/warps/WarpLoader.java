package oldschoolproject.warps;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import oldschoolproject.Main;
import oldschoolproject.utils.ClassGetter;

public class WarpLoader {

	private static Map<String, BaseWarp> warpsMap = new HashMap<>();
	
	public WarpLoader() {
		loadWarps();
	}

	public static Collection<BaseWarp> getWarpInstances() {
		return warpsMap.values();
	}

	private void registerWarp(String warpName, BaseWarp warp) {
		warpsMap.put(warpName, warp);
	}

	private void loadWarps() {
		int i = 0;

		// Gets the source package of the project
		String srcPackage = BaseWarp.class.getPackage().getName().substring(0, BaseWarp.class.getPackage().getName().indexOf('.'));

		// Loops through all classes in that package
		for (Class<?> warpClass : (Iterable<Class<?>>) ClassGetter.getClassesForPackage(Main.getInstance(), srcPackage)) {
			// Test if the class "Kit" is assignable from the current verified class and if it's not the "Kit" class
			if (BaseWarp.class.isAssignableFrom(warpClass) && !warpClass.equals(BaseWarp.class)) {
				// Instance a new Kit based on the current class
				try {
					BaseWarp warpInstance = (BaseWarp) warpClass.getConstructor().newInstance();
					String warpName = (String) warpClass.getSuperclass().getMethod("getName").invoke(warpInstance);

					registerWarp(warpName, warpInstance);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException | IllegalArgumentException e) {
					e.printStackTrace();
					System.out.print("[WarpLoader] Error when registering the warp " + warpClass.getSimpleName());
				}
				i++;
			}
		}
		Main.getInstance().getLogger().info("[WarpLoader] " + i + " warp instances loaded");
	}
	
}
