package oldschoolproject.utils.tags;

import oldschoolproject.users.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TagBoard {

    private Scoreboard serverScoreboard;

    public TagBoard() {
        serverScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    }

    public void setPrefix(User user, String prefix) {
        Team playerTeam = createOrUpdateTeam(user.getPlayer().getName());

        playerTeam.setPrefix(prefix);

        playerTeam.setColor(ChatColor.getByChar(ChatColor.getLastColors(prefix).substring(1)));

        if (!playerTeam.hasEntry(user.getPlayer().getName())) {
            playerTeam.addEntry(user.getPlayer().getName());
        }

        user.getPlayer().setScoreboard(serverScoreboard);
    }

    public void setSuffix(User user, String suffix) {
        Team playerTeam = createOrUpdateTeam(user.getPlayer().getName());

        playerTeam.setSuffix(suffix);

        if (!playerTeam.hasEntry(user.getPlayer().getName())) {
            playerTeam.addEntry(user.getPlayer().getName());
        }

        user.getPlayer().setScoreboard(serverScoreboard);
    }

    private Team createOrUpdateTeam(String teamName) {
        Team team = this.serverScoreboard.getTeam(teamName);

        if (team != null) {
            team.unregister();
        }

        team = serverScoreboard.registerNewTeam(teamName);

        return team;
    }
}
