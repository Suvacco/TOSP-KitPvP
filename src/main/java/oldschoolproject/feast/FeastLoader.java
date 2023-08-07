package oldschoolproject.feast;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import oldschoolproject.Main;
import oldschoolproject.utils.builders.FileBuilder;

public class FeastLoader {
	
	private static Map<String, Feast> feastInstances = new HashMap<>();
	
	private static FileBuilder fileBuilder = new FileBuilder("feasts.yml");
	
	public FeastLoader() {
		loadFeasts();
	}
	
	private void loadFeasts() {
        int i = 0;

        FileConfiguration fileConfig = fileBuilder.getFileConfiguration();

        if (fileConfig.getConfigurationSection("feasts") != null) {

            for (String key : fileConfig.getConfigurationSection("feasts").getKeys(false)) {

                Location loc = new Location(Bukkit.getWorld("world"),
                        (double) fileConfig.get("feasts." + key + ".location.x"),
                        (double) fileConfig.get("feasts." + key + ".location.y"),
                        (double) fileConfig.get("feasts." + key + ".location.z"));

                Feast feast = new Feast(loc);

                feast.spawn();

                feastInstances.put(key, feast);

                i++;
            }
        }

        Main.getInstance().getLogger().info("[FeastsLoader] " + i + " hologram instances loaded");
	}
	
	public static Map<String, Feast> getFeastInstances() {
		return feastInstances;
	}
	
	public static void deleteFeast(String id) {
        fileBuilder.set("feasts." + id, null);
	}

	public static void saveFeast(String id, Feast feast) {
        fileBuilder.set("feasts." + id + ".location.x", feast.getLocation().getX());
        fileBuilder.set("feasts." + id + ".location.y", feast.getLocation().getY());
        fileBuilder.set("feasts." + id + ".location.z", feast.getLocation().getZ());

        fileBuilder.save();
	}

}
