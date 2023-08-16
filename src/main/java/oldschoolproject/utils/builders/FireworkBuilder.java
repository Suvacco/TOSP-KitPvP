package oldschoolproject.utils.builders;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkBuilder {

    private Firework firework;

    private FireworkMeta fireworkMeta;

    private FireworkEffect.Builder builder;

    public FireworkBuilder(Location location, Color... colors) {
        this.firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);

        this.fireworkMeta = this.firework.getFireworkMeta();

        this.builder = FireworkEffect.builder();

        this.fireworkMeta.addEffect(builder.withColor(colors).build());

        updateFireworkMeta();
    }

    private FireworkBuilder updateFireworkMeta() {
        this.firework.setFireworkMeta(this.fireworkMeta);
        return this;
    }

    public FireworkBuilder setType(FireworkEffect.Type type) {
        this.fireworkMeta.addEffect(this.builder.with(type).build());
        return updateFireworkMeta();
    }

    public FireworkBuilder setExplosionSeconds(int seconds) {
        this.fireworkMeta.setPower(seconds * 2);
        return updateFireworkMeta();
    }

    public FireworkBuilder setFlicker(boolean flicker) {
        this.fireworkMeta.addEffect(this.builder.flicker(flicker).build());
        return updateFireworkMeta();
    }

    public FireworkBuilder setTrail(boolean trail) {
        this.fireworkMeta.addEffect(this.builder.trail(trail).build());
        return updateFireworkMeta();
    }

    public void detonate() {
        this.firework.detonate();
    }
}
