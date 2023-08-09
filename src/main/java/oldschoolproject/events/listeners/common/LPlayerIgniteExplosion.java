package oldschoolproject.events.listeners.common;

import oldschoolproject.Main;
import oldschoolproject.events.BaseListener;
import oldschoolproject.feast.Feast;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class LPlayerIgniteExplosion implements BaseListener {

    @EventHandler
    public void tntClickSpawn(PlayerInteractEvent e) {

        if (Arrays.stream(Feast.getDefaultLoot()).noneMatch(item -> item.isSimilar(e.getPlayer().getInventory().getItemInMainHand()))) {
            return;
        }

        e.setCancelled(true);

        if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.TNT) {

            if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                return;
            }

            TNTPrimed tntPrimed = (TNTPrimed) e.getPlayer().getWorld().spawnEntity(e.getClickedBlock().getLocation().clone().add(0, 1, 0), EntityType.PRIMED_TNT);
            tntPrimed.setSource(e.getPlayer());
            tntPrimed.setFuseTicks(20 * 2);

            new BukkitRunnable() {
                float i = 0.5F;
                @Override
                public void run() {
                    if (i > 0.9F) {
                        cancel();
                    }

                    tntPrimed.getWorld().playSound(tntPrimed.getLocation(), Sound.ENTITY_TNT_PRIMED, 1.5F, i);
                    tntPrimed.getWorld().spawnParticle(Particle.FLAME, tntPrimed.getLocation().clone().add(0, 1, 0), 100);

                    i+= 0.1F;
                }
            }.runTaskTimer(Main.getInstance(), 0, (long)6.5);
        }

        if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.FIRE_CHARGE) {

            if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                return;
            }

            Fireball fireball = e.getPlayer().launchProjectile(Fireball.class);
            fireball.setShooter(e.getPlayer());
            fireball.setVelocity(e.getPlayer().getEyeLocation().getDirection().multiply(1.5));
        }

        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();

        if (item.getAmount() == 1) {
            e.getPlayer().getInventory().setItemInMainHand(null);
        } else {
            item.setAmount(item.getAmount() - 1);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Fireball) {
            Fireball fireball = (Fireball) event.getEntity();

            fireball.getWorld().createExplosion(fireball.getLocation().getX(), fireball.getLocation().getY(), fireball.getLocation().getZ(), 4.0f, false, false, fireball);

            fireball.remove();
        }
    }

    @EventHandler
    public void preventTntDestruction(EntityExplodeEvent e) {
        e.blockList().clear();
    }
}
