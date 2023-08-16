package oldschoolproject.events.listeners.common.player;

import oldschoolproject.events.BaseListener;
import oldschoolproject.managers.ChatManager;
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

        if (!ChatManager.isEnabled() && !e.getPlayer().isOp()) {
            e.setCancelled(true);
            p.sendMessage("§cChatting is disabled");
            return;
        }

        String rankTag = user.getUserRank().getTag();

        e.setMessage(ChatFormatter.format(e.getMessage()));

        e.setFormat(rankTag + ChatColor.getByChar(ChatColor.getLastColors(rankTag).substring(1)) + p.getName() + " §6§l» " + (user.getUserRank() == UserRank.MEMBER ? "§7" : "§f") + e.getMessage());
    }
}
