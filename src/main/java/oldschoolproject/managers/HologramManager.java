package oldschoolproject.managers;

import oldschoolproject.exceptions.HologramManagementException;
import oldschoolproject.holograms.Hologram;
import oldschoolproject.holograms.HologramLoader;
import oldschoolproject.utils.formatters.ChatFormatter;
import org.bukkit.Location;

import java.util.Collections;

public class HologramManager {

    public static void setlineHologram(String id, int line, String text) throws HologramManagementException {
        if (!HologramLoader.getHologramInstances().containsKey(id)) {
            throw new HologramManagementException("ID não encontrado", id);
        }

        Hologram hologram = HologramLoader.getHologramInstances().get(id);

        hologram.setLine(line, ChatFormatter.format(text));

        HologramLoader.saveHologram(id, hologram);
    }

    public static void createHologram(String id, String firstLine, Location location) throws HologramManagementException {
        if (HologramLoader.getHologramInstances().containsKey(id)) {
            throw new HologramManagementException("ID não registrado", id);
        }

        Hologram holo = new Hologram(location, Collections.singletonList(ChatFormatter.format(firstLine)));

        holo.spawn();

        HologramLoader.getHologramInstances().put(id, holo);

        HologramLoader.saveHologram(id, holo);
    }

    public static void deleteHologram(String id) throws HologramManagementException {
        if (!HologramLoader.getHologramInstances().containsKey(id)) {
            throw new HologramManagementException("ID não encontrado", id);
        }

        Hologram hologram = HologramLoader.getHologramInstances().get(id);

        hologram.destroy();

        HologramLoader.getHologramInstances().remove(id);

        HologramLoader.deleteHologram(id);
    }

    public static void addLine(String id, String line) throws HologramManagementException {
        if (!HologramLoader.getHologramInstances().containsKey(id)) {
            throw new HologramManagementException("ID não encontrado", id);
        }

        Hologram hologram = HologramLoader.getHologramInstances().get(id);

        hologram.addLine(ChatFormatter.format(line));

        HologramLoader.saveHologram(id, hologram);
    }

    public static void removeLine(String id, int line) throws HologramManagementException {
        if (!HologramLoader.getHologramInstances().containsKey(id)) {
            throw new HologramManagementException("ID não encontrado", id);
        }

        Hologram hologram = HologramLoader.getHologramInstances().get(id);

        hologram.removeLine(line);

        HologramLoader.saveHologram(id, hologram);
    }

    public static Hologram findHologram(String id) throws HologramManagementException {
        if (!HologramLoader.getHologramInstances().containsKey(id)) {
            throw new HologramManagementException("ID não encontrado", id);
        }

        return HologramLoader.getHologramInstances().get(id);
    }

    public static void moveHologram(String id, Location location) throws HologramManagementException {
        if (!HologramLoader.getHologramInstances().containsKey(id)) {
            throw new HologramManagementException("ID não encontrado", id);
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
