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
        super("rank", true);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cError: /rank [set]");
            return;
        }

        if (args[0].equalsIgnoreCase("set")) {
            StringBuilder sb = new StringBuilder();

            Arrays.stream(UserRank.values()).forEach(userRank -> sb.append(userRank.name()).append(" : "));

            if (args.length < 2) {
                sender.sendMessage("§cError: /rank set <player> [" + sb.substring(0, sb.length() - 3) + "]");
                return;
            }

            try {

                UserManager.setRank(args[1], args[2]);
                sender.sendMessage("§aPlayer \"" + args[1] + "\" was successfully updated to \"" + args[2].toUpperCase() + "\"");

            } catch (OperationFailException e) {
                sender.sendMessage(e.getMessage());
            }
        }
    }
}
