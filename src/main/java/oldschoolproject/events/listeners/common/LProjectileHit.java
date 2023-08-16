package oldschoolproject.events.listeners.common;

import oldschoolproject.events.BaseListener;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;

public class LProjectileHit implements BaseListener {

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Fireball) {
            Fireball fireball = (Fireball) event.getEntity();

            fireball.getWorld().createExplosion(fireball.getLocation().getX(), fireball.getLocation().getY(), fireball.getLocation().getZ(), 4.0f, false, false, (Entity) fireball.getShooter());

            fireball.remove();
        }
    }
}
