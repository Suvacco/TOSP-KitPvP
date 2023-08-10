package oldschoolproject.events.listeners.common;

import oldschoolproject.Main;
import oldschoolproject.events.BaseListener;
import oldschoolproject.feast.Feast;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
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
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Fireball) {
            Fireball fireball = (Fireball) event.getEntity();

            fireball.getWorld().createExplosion(fireball.getLocation().getX(), fireball.getLocation().getY(), fireball.getLocation().getZ(), 4.0f, false, false, (Entity) fireball.getShooter());

            fireball.remove();
        }
    }

    @EventHandler
    public void tntClickSpawn(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (Arrays.stream(Feast.getDefaultLoot()).noneMatch(item -> item.isSimilar(p.getInventory().getItemInMainHand()))) {
            return;
        }

        if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.FIRE_CHARGE) {

            if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                return;
            }

            Fireball fireball = e.getPlayer().launchProjectile(Fireball.class);
            fireball.setShooter(e.getPlayer());
            fireball.setVelocity(e.getPlayer().getEyeLocation().getDirection().multiply(1.5));
        }

        if (p.getInventory().getItemInMainHand().getType() == Material.TNT) {

            if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                return;
            }

            TNTPrimed tntPrimed = e.getClickedBlock().getWorld().spawn(e.getClickedBlock().getLocation().clone().add(0, 1, 0), TNTPrimed.class);
            tntPrimed.setFuseTicks(20 * 2);
            tntPrimed.setSource(p);

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

        ItemStack item = p.getInventory().getItemInMainHand();

        if (item.getAmount() == 1) {
            p.getInventory().setItemInMainHand(null);
        } else {
            item.setAmount(item.getAmount() - 1);
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void preventTntDestruction(EntityExplodeEvent e) {
        e.blockList().clear();
    }
}
