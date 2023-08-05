package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;

public class CmdSetLocation extends BaseCommand {

	public CmdSetLocation() {
		super("setloc");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player)sender;
		
		if (args.length == 0) {
			player.sendMessage("§cErro: /setloc [locname]");
			return;
		}
		
		User user = UserManager.getUser((Player)sender);
		
		user.getWarp().setLocation(args[0], player.getLocation());
		
		player.sendMessage("§aWarp: " + user.getWarp().getName());
		player.sendMessage("§aLocalização: " + args[0]);
	}
	
	

}
