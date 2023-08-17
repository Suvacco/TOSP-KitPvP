package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import oldschoolproject.managers.DatabaseManager;
import oldschoolproject.managers.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdReload extends BaseCommand {

    public CmdReload() {
        super("rl", true);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            DatabaseManager.saveUser(UserManager.getUser(player));
        }

        sender.sendMessage("§aPlayers saved");

        Bukkit.reload();
    }
}
