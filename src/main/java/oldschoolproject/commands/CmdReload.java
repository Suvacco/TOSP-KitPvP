package oldschoolproject.commands;

import oldschoolproject.managers.DatabaseManager;
import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.commands.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdReload extends BaseCommand {

    public void unregisterPlayers() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            User user = UserManager.getUser(all);
            DatabaseManager.saveUser(user);
            user.reset();
        }
    }

    public CmdReload() {
        super("rl");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        unregisterPlayers();
        Bukkit.reload();
    }
}
