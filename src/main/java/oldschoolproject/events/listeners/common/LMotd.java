package oldschoolproject.events.listeners.common;

import oldschoolproject.events.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

public class LMotd implements BaseListener {

    @EventHandler
    public void motd(ServerListPingEvent e) {
        e.setMotd("§9§lTHE §e§lOLD SCHOOL §9§lPROJECT §7- §aKitPvP\n"
                + "§b§lGitHub: §6github.com/Suvacco/TOSP-KitPvP");
    }
}
