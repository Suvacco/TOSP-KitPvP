package oldschoolproject.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oldschoolproject.managers.UserManager;
import oldschoolproject.menus.KitSelectorMenu;
import oldschoolproject.utils.commands.BaseCommand;

public class CKitSelector extends BaseCommand {

	public CKitSelector() {
		super("kitinv");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player p = (Player)sender;
		
		new KitSelectorMenu(UserManager.getUser(p)).open();
	}

}
