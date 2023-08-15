package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import oldschoolproject.exceptions.OperationFailException;
import oldschoolproject.managers.UserManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdPermission extends BaseCommand {

    // develop this

    public CmdPermission() {
        super("perm", true);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cError: /perm [add : remove : has : see]");
            return;
        }

        try {

            if (args[0].equalsIgnoreCase("add")) {

                if (args.length < 3) {
                    sender.sendMessage("§cError: /perm add <player> <permission>");
                    return;
                }

                UserManager.addPermission(args[1], args[2]);
                sender.sendMessage("§aPermission \"" + args[2] + "\" added successfully to the player \"" + args[1] + "\"");
                return;
            }

            if (args[0].equalsIgnoreCase("remove")) {

                if (args.length < 3) {
                    sender.sendMessage("§cError: /perm remove <player> <permission>");
                    return;
                }

                UserManager.removePermission(args[1], args[2]);
                sender.sendMessage("§aPermission \"" + args[2] + "\" removed successfully from the player \"" + args[1] + "\"");
                return;
            }

            if (args[0].equalsIgnoreCase("has")) {

                if (args.length < 3) {
                    sender.sendMessage("§cError: /perm has <player> <permission>");
                    return;
                }

                boolean hasPermission = UserManager.hasPermission(args[1], args[2]);

                if (!hasPermission) {
                    sender.sendMessage("§cThe player \"" + args[1] + "\" doesn't have the permission \"" + args[2] + "\"");
                    return;
                }

                sender.sendMessage("§aThe player \"" + args[1] + "\" has the permission \"" + args[2] + "\"");
                return;
            }

            if (args[0].equalsIgnoreCase("see")) {

                if (args.length < 2) {
                    sender.sendMessage("§cError: /perm see <player>");
                    return;
                }

                List<String> permissions = UserManager.getPermissions(args[1]);

                sender.sendMessage("§aThese are the permissions of the player \"" + args[1] + "\":");

                for (String permission : permissions) {
                    sender.sendMessage("§6- §e" + permission);
                }

                return;
            }
        } catch (OperationFailException e) {
            sender.sendMessage(e.getMessage());
            return;
        }

        sender.sendMessage("§cError: /perm [add : remove : has : see]");
    }
}
