package oldschoolproject.holograms;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Hologram {

    Location location;
    List<ArmorStand> armorStands;
    final List<String> lines;

    public Hologram(Location location, List<String> lines) {
        this.location = location;
        this.lines = lines;
        this.armorStands = new ArrayList<>();
    }

    public void spawn() {
        for (String line : lines) {
            addLine(line);
        }
    }

    public void setLine(int line, String text) {
        if (getLine(line) != null) {
            this.armorStands.get(line).setCustomName(text);
        } else {
            this.armorStands.set(line, spawnArmorStand(text));
        }

        fixHologramsPositions();
    }

    public void addLine(String text) {
        armorStands.add(spawnArmorStand(text));

        fixHologramsPositions();
    }

    public void removeLine(int line) {
        armorStands.remove(line).remove();

        fixHologramsPositions();
    }

    public String getLine(int line) {
        return this.armorStands.get(line).getCustomName();
    }

    private ArmorStand spawnArmorStand(String title) {
        ArmorStand armorStand = (ArmorStand)this.location.getWorld().spawnEntity(this.location, EntityType.ARMOR_STAND);

        armorStand.setCustomName(title);
        armorStand.setCustomNameVisible(true);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setInvulnerable(true);
        armorStand.setMarker(true);

        return armorStand;
    }

    public void destroy() {
        for (Entity e : armorStands) {
            e.remove();
        }
    }

    public void fixHologramsPositions() {
        for (int i = 0; i < this.armorStands.size(); i++) {
            this.armorStands.get(i).teleport(this.location.clone().add(0, (this.armorStands.size() - 1 - i) * 0.28, 0));
        }
    }

    public List<String> getLines() {
        return armorStands.stream().map(ArmorStand::getCustomName).collect(Collectors.toList());
    }

    public Location getLocation() {
        return this.location;
    }

    public void updateLocation(Location location) {
        this.location = location;

        fixHologramsPositions();
    }
}
