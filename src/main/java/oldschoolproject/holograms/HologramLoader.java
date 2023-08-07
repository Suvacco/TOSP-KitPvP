package oldschoolproject.holograms;

import oldschoolproject.Main;
import oldschoolproject.holograms.instances.ServerHolograms;
import oldschoolproject.utils.builders.FileBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HologramLoader {

    private static Map<String, Hologram> hologramInstances = new HashMap<>();

    private static FileBuilder fileBuilder = new FileBuilder("holograms.yml");

    public HologramLoader() {
        unloadHolograms();
        loadHolograms();
    }

    private void unloadHolograms() {
        for (ArmorStand armorStand : Bukkit.getWorld("world").getEntitiesByClass(ArmorStand.class)) {
            if (armorStand.getCustomName() != null) {
                armorStand.remove();
            }
        }
    }

    private void loadHolograms() {
        int i = 0;

        FileConfiguration fileConfig = fileBuilder.getFileConfiguration();

        if (fileConfig.getConfigurationSection("holograms") != null) {

            for (String key : fileConfig.getConfigurationSection("holograms").getKeys(false)) {

                List<String> lines = fileConfig.getStringList("holograms." + key + ".content");

                Location loc = new Location(Bukkit.getWorld("world"),
                        (double) fileConfig.get("holograms." + key + ".location.x"),
                        (double) fileConfig.get("holograms." + key + ".location.y"),
                        (double) fileConfig.get("holograms." + key + ".location.z"));

                Hologram holo = new Hologram(loc, lines);

                holo.spawn();

                hologramInstances.put(key, holo);

                i++;
            }
        }

        Main.getInstance().getLogger().info("[HologramLoader] " + i + " hologram instances loaded");
    }

    public static void deleteHologram(String id) {
        fileBuilder.set("holograms." + id, null);
    }

    public static void saveHologram(String id, Hologram hologram) {
        fileBuilder.set("holograms." + id + ".location.x", hologram.getLocation().getX());
        fileBuilder.set("holograms." + id + ".location.y", hologram.getLocation().getY());
        fileBuilder.set("holograms." + id + ".location.z", hologram.getLocation().getZ());

        fileBuilder.set("holograms." + id + ".content", hologram.getLines());
    }

    public static Map<String, Hologram> getHologramInstances() {
        return hologramInstances;
    }

    public static FileBuilder getFileBuilder() {
        return fileBuilder;
    }
}
