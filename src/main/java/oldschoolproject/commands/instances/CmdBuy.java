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
            if (!user.getPlayer().hasPermission("rank.kit." + kit.getName().toLowerCase()) && !user.getPlayer().hasPermission("perm.kit." + kit.getName().toLowerCase())) {
                sb.append(kit.getName()).append(", ");
            }
        });

        String message = "[" + (sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 2) : "") + "]";

        if (args.length == 0) {
            p.sendMessage("§cError: /buy " + message);
            return;
        }

        try {
            KitManager.buyKit(user, args[0]);
        } catch (OperationFailException e) {
            p.sendMessage(e.getMessage());
            return;
        }

        p.sendMessage("§cError: /buy " + message);
    }
}
