package oldschoolproject.events.listeners.common.player;

import oldschoolproject.Main;
import oldschoolproject.events.BaseListener;
import oldschoolproject.feast.Feast;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class LPlayerIgnite implements BaseListener {

    @EventHandler
    public void playerIgniteInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (Arrays.stream(Feast.getDefaultLoot()).noneMatch(item -> item.isSimilar(p.getInventory().getItemInMainHand()))) {
            return;
        }

        // Ignite fireball
        if (p.getInventory().getItemInMainHand().getType() == Material.FIRE_CHARGE) {

            if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                return;
            }

            Fireball fireball = e.getPlayer().launchProjectile(Fireball.class);
            fireball.setShooter(e.getPlayer());
            fireball.setVelocity(e.getPlayer().getEyeLocation().getDirection().multiply(1.5));

            new BukkitRunnable() {
                int ticksPassed = 0;

                public void run() {

                    if (fireball.isValid()) {

                        if (ticksPassed >= 12) { // 1.5s
                            fireball.getWorld().createExplosion(fireball.getLocation().getX(), fireball.getLocation().getY(), fireball.getLocation().getZ(), 4.0f, false, false, (Entity) fireball.getShooter());
                            fireball.remove();
                            cancel();
                        }

                        fireball.getWorld().playSound(fireball.getLocation(), Sound.ENTITY_GENERIC_BURN, 2.0F, 1.5F);
                        fireball.getWorld().spawnParticle(Particle.FLAME, fireball.getLocation().clone(), 30);
                        ticksPassed++;
                    }
                }
            }.runTaskTimer(Main.getInstance(), 0, (long) 3);
        }

        // Ignite TNT
        if (p.getInventory().getItemInMainHand().getType() == Material.TNT) {

            if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                return;
            }

            TNTPrimed tntPrimed = e.getClickedBlock().getWorld().spawn(e.getClickedBlock().getRelative(e.getBlockFace()).getLocation().clone().add(0.5, 0.0, 0.5), TNTPrimed.class);
            tntPrimed.setFuseTicks(20 * 2);
            tntPrimed.setSource(p);

            new BukkitRunnable() {
                float soundPitch = 0.5F;

                public void run() {

                    if (soundPitch > 0.9F) {
                        cancel();
                    }

                    tntPrimed.getWorld().playSound(tntPrimed.getLocation(), Sound.ENTITY_TNT_PRIMED, 1.5F, soundPitch);
                    tntPrimed.getWorld().spawnParticle(Particle.LAVA, tntPrimed.getLocation().clone().add(0, 1, 0), 15);

                    soundPitch += 0.1F;
                }
            }.runTaskTimer(Main.getInstance(), 0, (long)6.5);
        }

        ItemStack item = p.getInventory().getItemInMainHand();
        item.setAmount(item.getAmount() - 1);
        e.setCancelled(true);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Fireball) {
            Fireball fireball = (Fireball) event.getEntity();

            fireball.getWorld().createExplosion(fireball.getLocation().getX(), fireball.getLocation().getY(), fireball.getLocation().getZ(), 4.0f, false, false, (Entity) fireball.getShooter());

            fireball.remove();
        }
    }
}
