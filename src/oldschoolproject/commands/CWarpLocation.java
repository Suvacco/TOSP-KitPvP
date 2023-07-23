package oldschoolproject.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.commands.BaseCommand;

public class CWarpLocation extends BaseCommand {

	public CWarpLocation() {
		super("warploc");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player)sender;
		
		if (args.length == 0) {
			player.sendMessage("§cErro: /warploc [locname]");
			return;
		}
		
		User user = UserManager.getUser((Player)sender);
		
		user.getWarp().setLocation(args[0], player.getLocation());
		
		player.sendMessage("§aWarp: " + user.getWarp().getName());
		player.sendMessage("§aLocalização: " + args[0]);
	}
	
	

}
