package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import oldschoolproject.exceptions.OperationFailException;
import oldschoolproject.managers.DatabaseManager;
import oldschoolproject.users.UserStats;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;

public class CmdInspect extends BaseCommand {

	public CmdInspect() {
		super("inspect", true);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if (args.length == 0) {
			player.sendMessage("§cError: /inspect <playername>");
			return;
		}

		try {
			UserManager.viewUserDetails(player, args[0]);
		} catch (OperationFailException e) {
			player.sendMessage(e.getMessage());
		}
	}
}
