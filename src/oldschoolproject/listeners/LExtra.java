package oldschoolproject.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import oldschoolproject.utils.loaders.listener.BaseListener;

public class LExtra extends BaseListener {
	
	@EventHandler
	public void preventHealthRegen(EntityRegainHealthEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void preventHunger(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}
}
