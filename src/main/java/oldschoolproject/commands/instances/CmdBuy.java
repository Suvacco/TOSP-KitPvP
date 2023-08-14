package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import oldschoolproject.kits.KitLoader;
import oldschoolproject.managers.KitManager;
import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdBuy extends BaseCommand {

    public CmdBuy() {
        super("buy");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player p = (Player)sender;
        User user = UserManager.getUser(p);

        if (args.length == 0) {

            StringBuilder sb = new StringBuilder();

            KitLoader.getKitInstances().forEach(kit -> {
                if (!user.getPlayer().hasPermission("rank.kit." + kit.getName().toLowerCase()) &&
                        !user.getPlayer().hasPermission("perm.kit." + kit.getName().toLowerCase())) {
                    sb.append(kit.getName()).append(", ");
                }
            });

            p.sendMessage("§cErro: /buy [" + sb.toString().substring(0, sb.toString().length() - 2) + "]");
            return;
        }

        KitManager.buyKit(user, args[0]);
    }
}
