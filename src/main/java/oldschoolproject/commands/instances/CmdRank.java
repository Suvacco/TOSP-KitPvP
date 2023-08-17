package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import oldschoolproject.exceptions.OperationFailException;
import oldschoolproject.users.UserStats;
import oldschoolproject.managers.DatabaseManager;
import oldschoolproject.managers.TagManager;
import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.users.UserRank;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CmdRank extends BaseCommand {

    public CmdRank() {
        super("setrank", true);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(UserRank.values()).forEach(userRank -> sb.append(userRank.name()).append(", "));

        if (args.length < 2) {
            sender.sendMessage("§cError: /setrank <player> [" + sb.substring(0, sb.length() - 2) + "]");
            return;
        }

        try {

            UserManager.setRank(args[0], args[1].toUpperCase());
            sender.sendMessage("§aPlayer \"" + args[0] + "\" was successfully updated to \"" + args[1].toUpperCase() + "\"");

        } catch (OperationFailException e) {
            sender.sendMessage(e.getMessage());
        }
    }
}
