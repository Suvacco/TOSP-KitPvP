package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import oldschoolproject.managers.UserManager;
import oldschoolproject.menus.instances.ShopMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdShopInv extends BaseCommand {
    public CmdShopInv() {
        super("shopinv");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player)sender;

        new ShopMenu(UserManager.getUser(player)).open();
    }
}
