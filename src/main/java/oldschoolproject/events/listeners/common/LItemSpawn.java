package oldschoolproject.events.listeners.common;

import oldschoolproject.events.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ItemSpawnEvent;

public class LItemSpawn implements BaseListener {

    @EventHandler
    public void itemSpawn(ItemSpawnEvent e) {
        e.setCancelled(true);
        e.getEntity().remove();
    }
}
