package oldschoolproject.utils.kits;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import oldschoolproject.Main;
import oldschoolproject.utils.ClassGetter;

public class KitLoader {
	
	private static Map<String, BaseKit> kitsMap = new HashMap<>();
	
	public KitLoader() {
		loadKits();
	}
	
	public void loadKits() {
		int i = 0;
		
		// Gets the source package of the project
		String srcPackage = BaseKit.class.getPackage().getName().substring(0, BaseKit.class.getPackage().getName().indexOf('.'));
		
		// Loops through all classes in that package
		for (Class<?> kitClass : (Iterable<Class<?>>) ClassGetter.getClassesForPackage(Main.getInstance(), srcPackage)) {
			// Test if the class "Kit" is assignable from the current verified class and if it's not the "Kit" class
			if (BaseKit.class.isAssignableFrom(kitClass) && !kitClass.equals(BaseKit.class)) {
					// Instance a new Kit based on the current class
					try {
						BaseKit kitInstance = (BaseKit) kitClass.getConstructor().newInstance();
						String kitName = (String) kitClass.getSuperclass().getMethod("getName").invoke(kitInstance);
						
						registerKit(kitName, kitInstance);
					} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException | IllegalArgumentException e) {
						e.printStackTrace();
						System.out.print("[KitLoader] Error when registering the kit " + kitClass.getSimpleName());
					}
				i++;
			}
		}
		Main.getInstance().getLogger().info("[KitLoader] " + i + " kit instances loaded");
	}

	public static void registerKit(String kitName, BaseKit kit) {
		kitsMap.put(kitName, kit);
	}
	
	public static Collection<BaseKit> getKitInstances() {
		return kitsMap.values();
	}
	
}
