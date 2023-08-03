package oldschoolproject.listeners.custom;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import oldschoolproject.events.PlayerKillstreakEvent;
import oldschoolproject.users.User;
import oldschoolproject.users.UserStats;
import oldschoolproject.utils.listeners.BaseListener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

public class LPlayerKillstreak implements BaseListener {

    @EventHandler
    public void onGainStreak(PlayerKillstreakEvent e) {
        User user = e.getUser();

        if (e.getStreakAction() == PlayerKillstreakEvent.StreakAction.LOSE) {

            int killstreak = (Integer)e.getUser().getStat(UserStats.KILLSTREAK);

            if (killstreak >= 8) {
                Bukkit.broadcastMessage("§4§lEXECUTADO §c" + user.getPlayer().getName() + "§e perdeu uma killstreak de §c" + killstreak);
            }

            if (killstreak > (Integer)e.getUser().getStat(UserStats.KILLSTREAK_RECORD)) {
                user.setStat(UserStats.KILLSTREAK_RECORD, killstreak);
                user.getPlayer().sendMessage("§a§lNOVO RECORDE! §7Você atingiu um novo recorde de killstreak: §a" + killstreak);
            }

            user.setStat(UserStats.KILLSTREAK, 0);
        } else {
            user.setStat(UserStats.KILLSTREAK, (Integer) e.getUser().getStat(UserStats.KILLSTREAK) + 1);

            int killstreak = (Integer)user.getStat(UserStats.KILLSTREAK);

            if (killstreak >= 3 && killstreak <= 8 || (killstreak >= 10 && killstreak % 5 == 0)) {
                user.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(getStreakState((Integer)user.getStat(UserStats.KILLSTREAK))));
            }

            if (killstreak >= 10 && killstreak % 5 == 0) {
                Bukkit.broadcastMessage("§3§lKILLSTREAK §b" + user.getPlayer().getName() + " §eestá em uma killstreak de §b" + (Integer)user.getStat(UserStats.KILLSTREAK));
            }
        }
    }

    public String getStreakState(int killstreak) {
        switch (killstreak) {
            case 3:
                return "§a§lKILLING SPREE";
            case 4:
                return "§b§lENFURECIDO";
            case 5:
                return "§e§lIMPLACAVEL";
            case 6:
                return "§c§l! DOMINANDO !";
            case 7:
                return "§4§l!! INVENCIVEL !!";
            case 8:
                return "§6§l!!! LENDARIO !!!";
        }
        return "§0§l-= MULTIKILL =-";
    }
}
