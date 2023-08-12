package oldschoolproject.events.listeners.common;

import oldschoolproject.events.BaseListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class LPrevent implements BaseListener {
	
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
	public void preventExplosionDestruction(EntityExplodeEvent e) {
		e.blockList().clear();
	}

	@EventHandler
	public void preventMobsFromSpawningNaturally(CreatureSpawnEvent e) {
		if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void preventItemSpawn(ItemSpawnEvent e) {
		e.setCancelled(true);
		e.getEntity().remove();
	}

	@EventHandler
	public void preventDrop(PlayerDropItemEvent e) {
		e.getItemDrop().remove();
	}

}
