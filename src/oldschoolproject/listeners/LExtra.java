package oldschoolproject.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import oldschoolproject.utils.listeners.BaseListener;

public class LExtra implements BaseListener {
	
	@EventHandler
	public void preventHealthRegen(EntityRegainHealthEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void preventHunger(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}
}
