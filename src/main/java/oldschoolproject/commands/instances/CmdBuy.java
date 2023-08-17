package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import oldschoolproject.exceptions.OperationFailException;
import oldschoolproject.kits.KitLoader;
import oldschoolproject.managers.KitManager;
import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdBuy extends BaseCommand {

    public CmdBuy() {
        super("buy", false);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player p = (Player)sender;
        User user = UserManager.getUser(p);

        StringBuilder sb = new StringBuilder();

        KitLoader.getKitInstances().forEach(kit -> {
            if (!user.getPermissionStorage().hasPermission("rank.kit." + kit.getName().toLowerCase()) && !user.getPermissionStorage().hasPermission("perm.kit." + kit.getName().toLowerCase())) {
                sb.append(kit.getName()).append(", ");
            }
        });

        if (sb.length() == 0) {
            p.sendMessage("§cYou already have all the kits");
            return;
        }

        String message = "[" + sb.toString().substring(0, sb.toString().length() - 2) + "]";

        if (args.length == 0) {
            p.sendMessage("§cError: /buy " + message);
            return;
        }

        try {

            KitManager.buyKit(user, args[0]);

            p.sendMessage("§aKit \"" + args[0].substring(0, 1).toUpperCase() + args[0].substring(1, args[0].length()) + "\" purchased successfully!");
            p.sendTitle("§a§lPURCHASED", "You purchased kit §a" + args[0].substring(0, 1).toUpperCase() + args[0].substring(1, args[0].length()) + "§f. Enjoy your new kit!", 0, 100, 40);

        } catch (OperationFailException e) {
            p.sendMessage(e.getMessage());
        }
    }
}
