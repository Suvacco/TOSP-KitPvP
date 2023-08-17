package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;

public class CmdSetWarpLocation extends BaseCommand {

	public CmdSetWarpLocation() {
		super("setwarploc", true);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player)sender;
		
		if (args.length == 0) {
			player.sendMessage("§cError: /setwarploc [locname]");
			return;
		}
		
		User user = UserManager.getUser((Player)sender);
		
		user.getWarp().setLocation(args[0], player.getLocation());

		player.sendMessage("");
		player.sendMessage("§aCustom warp location set successfully!");
		player.sendMessage("§bCurrent Warp Affected: §e" + user.getWarp().getName());
		player.sendMessage("§bLocation Name Saved: §e" + args[0]);
	}
}
