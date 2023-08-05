package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oldschoolproject.managers.UserManager;
import oldschoolproject.menus.instances.WarpsMenu;

public class CmdWarpSelector extends BaseCommand {

	public CmdWarpSelector() {
		super("warpinv");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player p = (Player)sender;
		
		new WarpsMenu(UserManager.getUser(p)).open();
	}
	
	

}
