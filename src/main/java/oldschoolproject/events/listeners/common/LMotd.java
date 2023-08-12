package oldschoolproject.events.listeners.common;

import oldschoolproject.events.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

public class LMotd implements BaseListener {

    @EventHandler
    public void motd(ServerListPingEvent e) {
        e.setMotd("§6§lTHE §e§lOLD SCHOOL §6§lPROJECT §7- §aKitPvP\n"
                + "§bServidor ligado e funcionando!");
    }
}
