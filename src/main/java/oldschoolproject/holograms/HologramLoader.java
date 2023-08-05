package oldschoolproject.holograms;

import oldschoolproject.Main;
import oldschoolproject.holograms.instances.ServerHolograms;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.persistence.PersistentDataType;

public class HologramLoader {

    final NamespacedKey hologramKey = new NamespacedKey(Main.getInstance(), getClass().getSimpleName());

    public HologramLoader() {
        for (ArmorStand armorStand : Bukkit.getWorld("world").getEntitiesByClass(ArmorStand.class)) {
            if (armorStand.getPersistentDataContainer().has(hologramKey, PersistentDataType.STRING)) {
                armorStand.remove();
            }
        }

        loadHolograms();
    }

    private void loadHolograms() {
        int i = 0;

        for (ServerHolograms hologram : ServerHolograms.values()) {

            hologram.getInstance().spawn();

            for (ArmorStand armorStand : hologram.getInstance().getArmorStands()) {
                armorStand.getPersistentDataContainer().set(hologramKey, PersistentDataType.STRING, getClass().getSimpleName());
            }

            i++;
        }

        Main.getInstance().getLogger().info("[HologramLoader] " + i + " hologram instances loaded");
    }
}
