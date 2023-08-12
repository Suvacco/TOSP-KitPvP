package oldschoolproject.events.listeners.common.player;

import oldschoolproject.events.BaseListener;
import oldschoolproject.managers.KitManager;
import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class LPlayerLaunch implements BaseListener {

    private static List<Player> fallProtectionList = new ArrayList<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block blockUnderPlayer = player.getLocation().getBlock().getRelative(0, -1, 0);

        if (blockUnderPlayer.getType() != Material.AIR &&
                blockUnderPlayer.getType() != Material.PUMPKIN &&
                blockUnderPlayer.getType() != Material.MAGENTA_GLAZED_TERRACOTTA &&
            fallProtectionList.contains(player)) {
            fallProtectionList.remove(player);
        }

        if (blockUnderPlayer.getType() == Material.MAGENTA_GLAZED_TERRACOTTA || blockUnderPlayer.getType() == Material.PUMPKIN) {

            if (blockUnderPlayer.getType() == Material.MAGENTA_GLAZED_TERRACOTTA) {

                if (blockUnderPlayer.getBlockData() instanceof Directional) {

                    Directional directional = (Directional) blockUnderPlayer.getBlockData();

                    player.setVelocity(directional.getFacing().getDirection().multiply(-2).add(new Vector(0, 1, 0)));

                    KitManager.giveKit(UserManager.getUser(player));
                }
            }

            if (blockUnderPlayer.getType() == Material.PUMPKIN) {
                player.setVelocity(new Vector(0, 4, 0));
            }

            player.getPlayer().playSound(player, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 15.0F, 2.0F);
            player.getPlayer().getWorld().playEffect(player.getPlayer().getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
            fallProtectionList.add(player);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Player player = (Player) event.getEntity();

            if (fallProtectionList.contains(player)) {
                event.setCancelled(true);
            }
        }
    }
}
