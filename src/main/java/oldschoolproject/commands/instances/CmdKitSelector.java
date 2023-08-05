package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oldschoolproject.managers.UserManager;
import oldschoolproject.menus.instances.KitSelectorMenu;

public class CmdKitSelector extends BaseCommand {

	public CmdKitSelector() {
		super("kitinv");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player p = (Player)sender;
		
		new KitSelectorMenu(UserManager.getUser(p)).open();
	}

}
