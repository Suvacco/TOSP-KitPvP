package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdPermission extends BaseCommand {

    public CmdPermission() {
        super("perm");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player)sender;

        if (args.length == 0) {
            player.sendMessage("§cErro: /permission [add : see] <perm>");
            return;
        }

        if (args[0].equalsIgnoreCase("add")) {

            User user = UserManager.getUser(player);

            user.getPermissionAttachment().setPermission(args[1], true);

            player.sendMessage("§aPermissão adicionada");

            return;
        }

        if (args[0].equalsIgnoreCase("has")) {

            if (player.hasPermission(args[1])) {
                player.sendMessage("§atem permissao " + args[1]);
                return;
            }

            player.sendMessage("§cnao tem");
            return;
        }

        if (args[0].equalsIgnoreCase("see")) {

            player.sendMessage("§aSuas permissões: ");

            User user = UserManager.getUser(player);

            for (String s : user.getPermissionAttachment().getPermissions().keySet()) {
                player.sendMessage(s);
            }
        }
    }
}
