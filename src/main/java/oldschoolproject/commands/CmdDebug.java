package oldschoolproject.commands;

import oldschoolproject.users.UserStats;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.commands.BaseCommand;

public class CmdDebug extends BaseCommand {

	public CmdDebug() {
		super("debug");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		User user = UserManager.getUser(player);

		player.sendMessage("§7User: §a" + player.getName());
		player.sendMessage("");
		player.sendMessage("§bKit: §e" + (user.hasKit() ? user.getKit().getName() : "Nenhum"));
		player.sendMessage("§bWarp: §e" + user.getWarp().getName());
		player.sendMessage("§bGuard: §e" + user.getUserGuard().toString());
		player.sendMessage("§bRank: §e" + user.getUserRank());

		for (UserStats stat : UserStats.values()) {
			if (stat.isModifiable()) {
				player.sendMessage("§b" + Character.toUpperCase(stat.name().toLowerCase().charAt(0)) + stat.name().toLowerCase().substring(1).replace("_", " ") + ": §e" + user.getStat(stat));
			}
		}
	}
}
