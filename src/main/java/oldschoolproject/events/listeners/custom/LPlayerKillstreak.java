package oldschoolproject.events.listeners.custom;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import oldschoolproject.events.custom.PlayerKillstreakEvent;
import oldschoolproject.users.User;
import oldschoolproject.users.UserStats;
import oldschoolproject.events.BaseListener;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;

public class LPlayerKillstreak implements BaseListener {

    @EventHandler
    public void onGainStreak(PlayerKillstreakEvent e) {
        User user = e.getUser();

        if (e.getStreakAction() == PlayerKillstreakEvent.StreakAction.LOSE) {

            int killstreak = (Integer)e.getUser().getStat(UserStats.KILLSTREAK);

            if (killstreak >= 8) {
                Bukkit.broadcastMessage("§4§lEXECUTED §c" + user.getPlayer().getName() + "§e lost a killstreak of §c" + killstreak);
            }

            if (killstreak > (Integer)e.getUser().getStat(UserStats.KILLSTREAK_RECORD)) {
                user.setStat(UserStats.KILLSTREAK_RECORD, killstreak);
                user.getPlayer().sendMessage("§a§lNEW RECORD! §7You broke a new record of killstreak: §a" + killstreak);
                user.getPlayer().playSound(user.getPlayer(), Sound.ITEM_GOAT_HORN_SOUND_1, 15.0F, 1.0F);
            }

            user.setStat(UserStats.KILLSTREAK, 0);
        } else {
            user.setStat(UserStats.KILLSTREAK, (Integer) e.getUser().getStat(UserStats.KILLSTREAK) + 1);

            int killstreak = (Integer)user.getStat(UserStats.KILLSTREAK);

            if (killstreak >= 3 && killstreak <= 8 || (killstreak >= 10 && killstreak % 5 == 0)) {
                user.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(getStreakState((Integer)user.getStat(UserStats.KILLSTREAK))));

                if (killstreak <= 5) {
                    user.getPlayer().playSound(user.getPlayer(), Sound.ENTITY_CAT_AMBIENT, 15.0F, 1.5F);
                }

                if (killstreak >= 6 && killstreak <= 8) {
                    user.getPlayer().playSound(user.getPlayer(), Sound.ENTITY_ENDER_DRAGON_GROWL, 15.0F, 1.0F);
                }

                if (killstreak >= 10) {
                    user.getPlayer().playSound(user.getPlayer(), Sound.ENTITY_WITHER_AMBIENT, 15.0F, 1.0F);
                    Bukkit.broadcastMessage("§3§lKILLSTREAK §b" + user.getPlayer().getName() + " §eis on a killstreak of §b" + (Integer)user.getStat(UserStats.KILLSTREAK));
                }
            }
        }
    }

    public String getStreakState(int killstreak) {
        switch (killstreak) {
            case 3:
                return "§a§lKILLING SPREE";
            case 4:
                return "§b§lRAMPAGE";
            case 5:
                return "§e§lUNSTOPPABLE";
            case 6:
                return "§c§l⚔ DOMINATING ⚔";
            case 7:
                return "§4§l★ GODLIKE ★";
            case 8:
                return "§6§l♕ LEGENDARY ♕";
        }
        return "§0§l☠ MULTIKILL ☠";
    }
}
