package oldschoolproject.events.listeners.common.player;

import oldschoolproject.events.BaseListener;
import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.users.UserRank;
import oldschoolproject.utils.formatters.ChatFormatter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class LPlayerChat implements BaseListener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        User user = UserManager.getUser(e.getPlayer());

        String rank = user.getUserRank().getTag();

        e.setMessage(ChatFormatter.format(e.getMessage()));
        e.setFormat(rank + ChatColor.getByChar(ChatColor.getLastColors(rank).substring(1)) + p.getName() + " §e§l» " + (user.getUserRank() == UserRank.MEMBER ? "§7" : "§f") + e.getMessage());
    }
}
