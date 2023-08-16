package oldschoolproject.commands.instances;

import net.md_5.bungee.api.ChatMessageType;
import oldschoolproject.Main;
import oldschoolproject.commands.BaseCommand;
import oldschoolproject.exceptions.OperationFailException;
import oldschoolproject.kits.KitLoader;
import oldschoolproject.managers.KitManager;
import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.builders.FireworkBuilder;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

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

            p.sendMessage("§aKit \"" + args[0].substring(0, 1).toUpperCase() + args[0].substring(1, args[0].length()) + "\" purchased successfully!");
            p.sendTitle("§a§lPURCHASED", "You purchased kit §a" + args[0].substring(0, 1).toUpperCase() + args[0].substring(1, args[0].length()) + "§f. Enjoy your new kit!", 0, 100, 40);

        } catch (OperationFailException e) {
            p.sendMessage(e.getMessage());
        }
    }
}
