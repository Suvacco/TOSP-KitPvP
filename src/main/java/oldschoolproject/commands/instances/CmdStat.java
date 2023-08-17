package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import oldschoolproject.exceptions.OperationFailException;
import oldschoolproject.managers.UserManager;
import oldschoolproject.users.UserRank;
import oldschoolproject.users.UserStats;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class CmdStat extends BaseCommand {
    public CmdStat() {
        super("setstat", true);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(UserStats.values()).forEach(userStat -> {
            if (userStat.isServerControlled()) {
                sb.append(userStat.name()).append(", ");
            }
        });

        if (args.length < 3) {
            sender.sendMessage("§cError: /setstat <player> [" + sb.substring(0, sb.length() - 2) + "] <value>");
            return;
        }

        try {
            UserManager.setStat(args[0], args[1], args[2]);
            sender.sendMessage("§aValue \"" + args[2] + "\" set successfully to the stat \"" + args[1] + "\" of the user \"" + args[0] + "\"");
        } catch (OperationFailException e) {
            sender.sendMessage(e.getMessage());
        } catch (NumberFormatException e) {
            sender.sendMessage("§cError: Invalid number format");
        }
    }
}
