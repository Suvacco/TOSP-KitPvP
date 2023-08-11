package oldschoolproject.events.listeners.common;

import oldschoolproject.events.BaseListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class LExtra implements BaseListener {
	
	@EventHandler
	public void preventHealthRegen(EntityRegainHealthEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void preventHunger(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void preventDurabilityLoss(PlayerItemDamageEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void preventFireFromSpreading(BlockSpreadEvent e){//prevents fire from spreading
		if (e.getNewState().getType() == Material.FIRE) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void preventMobsFromSpawningNaturally(CreatureSpawnEvent e) {
		if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void motd(ServerListPingEvent e) {
		e.setMotd("§6§lTHE §e§lOLD SCHOOL §6§lPROJECT §7- §aKitPvP\n"
				+ "§bServidor ligado e funcionando!");
	}
}
