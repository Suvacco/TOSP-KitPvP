package oldschoolproject.managers;

import oldschoolproject.users.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;

public class TagManager {

    public static void setPrefix(User user, String prefix) {
        Team playerTeam = createOrUpdateTeam(user.getPlayer().getName());

        playerTeam.setPrefix(prefix);

        playerTeam.setColor(ChatColor.getByChar(ChatColor.getLastColors(prefix).substring(1)));

        if (!playerTeam.hasEntry(user.getPlayer().getName())) {
            playerTeam.addEntry(user.getPlayer().getName());
        }

        user.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

    public static void setSuffix(User user, String suffix) {
        Team playerTeam = createOrUpdateTeam(user.getPlayer().getName());

        playerTeam.setSuffix(suffix);

        if (!playerTeam.hasEntry(user.getPlayer().getName())) {
            playerTeam.addEntry(user.getPlayer().getName());
        }

        user.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

    private static Team createOrUpdateTeam(String teamName) {
        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(teamName);

        if (team != null) {
            team.unregister();
        }

        team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(teamName);

        return team;
    }
}
