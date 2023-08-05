package oldschoolproject.holograms;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class Hologram {

    Location location;
    List<String> lines;
    List<ArmorStand> armorStands;

    public Hologram(Location location, List<String> lines) {
        this.location = location;
        this.lines = lines;
        this.armorStands = new ArrayList<>();
    }

    public void spawn() {
        for (String line : lines) {
            armorStands.add(spawnArmorStand(line));
        }
        fixHologramsPositions();
    }

    public void destroy() {
        for (Entity e : armorStands) {
            e.remove();
        }
    }

    public void addLine(String text) {
        this.lines.add(text);

        this.armorStands.add(spawnArmorStand(text));

        fixHologramsPositions();
    }

    private ArmorStand spawnArmorStand(String title) {
        ArmorStand armorStand = (ArmorStand)this.location.getWorld().spawnEntity(this.location, EntityType.ARMOR_STAND);

        armorStand.setCustomName(title);
        armorStand.setCustomNameVisible(true);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setMarker(true);

        return armorStand;
    }

    public void fixHologramsPositions() {
        for (int i = 0; i < this.armorStands.size(); i++) {
            this.armorStands.get(i).teleport(this.location.clone().add(0, (this.armorStands.size() - 1 - i) * 0.28, 0));
        }
    }

    public List<ArmorStand> getArmorStands() {
        return this.armorStands;
    }
}
