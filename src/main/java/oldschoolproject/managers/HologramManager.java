package oldschoolproject.managers;

import oldschoolproject.holograms.Hologram;
import oldschoolproject.holograms.HologramLoader;
import oldschoolproject.utils.formatters.ChatFormatter;
import org.bukkit.Location;

import java.util.Collections;

public class HologramManager {

    public static void setlineHologram(String id, int line, String text) {
        if (!HologramLoader.getHologramInstances().containsKey(id)) {
            return;
        }

        Hologram hologram = HologramLoader.getHologramInstances().get(id);

        hologram.setLine(line, ChatFormatter.format(text));

        HologramLoader.saveHologram(id, hologram);
    }

    public static void createHologram(String id, String firstLine, Location location) {
        if (HologramLoader.getHologramInstances().containsKey(id)) {
            return;
        }

        Hologram holo = new Hologram(location, Collections.singletonList(ChatFormatter.format(firstLine)));

        holo.spawn();

        HologramLoader.getHologramInstances().put(id, holo);

        HologramLoader.saveHologram(id, holo);
    }

    public static void deleteHologram(String id) {
        if (!HologramLoader.getHologramInstances().containsKey(id)) {
            return;
        }

        Hologram hologram = HologramLoader.getHologramInstances().get(id);

        hologram.destroy();

        HologramLoader.getHologramInstances().remove(id);

        HologramLoader.deleteHologram(id);
    }

    public static void addLine(String id, String line) {
        if (!HologramLoader.getHologramInstances().containsKey(id)) {
            return;
        }

        Hologram hologram = HologramLoader.getHologramInstances().get(id);

        hologram.addLine(ChatFormatter.format(line));

        HologramLoader.saveHologram(id, hologram);
    }

    public static void removeLine(String id, int line) {
        if (!HologramLoader.getHologramInstances().containsKey(id)) {
            return;
        }

        Hologram hologram = HologramLoader.getHologramInstances().get(id);

        hologram.removeLine(line);

        HologramLoader.saveHologram(id, hologram);
    }

    public static Hologram findHologram(String id) {
        if (!HologramLoader.getHologramInstances().containsKey(id)) {
            return null;
        }

        return HologramLoader.getHologramInstances().get(id);
    }

    public static void moveHologram(String id, Location location) {
        if (!HologramLoader.getHologramInstances().containsKey(id)) {
            return;
        }

        Hologram hologram = HologramLoader.getHologramInstances().get(id);

        hologram.updateLocation(location);

        HologramLoader.saveHologram(id, hologram);
    }

    public static String getHologramList() {
        StringBuilder sb = new StringBuilder();

        HologramLoader.getHologramInstances().keySet().forEach(keys -> { sb.append(keys).append(", "); });

        sb.delete(sb.length() - 2, sb.length());

        return sb.toString();
    }
}
