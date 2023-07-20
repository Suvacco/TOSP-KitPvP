package oldschoolproject.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.commands.BaseCommand;

public class CUser extends BaseCommand {

	public CUser() {
		super("user");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player)sender;
		
		User user = UserManager.getUser(player);
		
		player.sendMessage("§aUser: " + player.getName());
//		player.sendMessage("§aKit: " + user.ki == null ? "Nada" : user.getKit().getName());
		player.sendMessage("§aWarp: " + user.getWarp().getName());
		player.sendMessage("§aState: " + user.getState().toString());
	}

}
