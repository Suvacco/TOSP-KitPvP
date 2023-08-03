package oldschoolproject.commands;

import oldschoolproject.managers.DatabaseManager;
import oldschoolproject.users.UserStats;
import org.bukkit.Bukkit;
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

		User targetedUser = UserManager.getUser(player);

		if (args.length > 0) {
			Player onlinePlayer = Bukkit.getPlayer(args[0]);

			User databaseUser = DatabaseManager.findUserByName(args[0]);

			// Player doesn't exist
			if (onlinePlayer == null && databaseUser == null) {
				player.sendMessage("§cErro: Player não encontrado");
				return;
			}

			// Player isn't online
			if (onlinePlayer == null) {
				sendPlayerInfo(player, databaseUser);
				return;
			}

			// Player is online
			sendPlayerInfo(player, UserManager.getUser(onlinePlayer));
			return;
		}

		// Player is self
		sendPlayerInfo(player, targetedUser);
	}

	public void sendPlayerInfo(Player player, User targetedUser) {
		Player targetedPlayer = Bukkit.getPlayer(targetedUser.getUserName());

		player.sendMessage("");
		player.sendMessage("§7Visualizando usuário: §a" + targetedUser.getUserName());
		player.sendMessage("");

		if (targetedPlayer != null) {
			player.sendMessage("§c> Session Data:");
			player.sendMessage("§cKit: §e" + (targetedUser.hasKit() ? targetedUser.getKit().getName() : "Nenhum"));
			player.sendMessage("§cWarp: §e" + targetedUser.getWarp().getName());
			player.sendMessage("§cGuard: §e" + targetedUser.getUserGuard().toString());
			player.sendMessage("§cKillstreak: §e" + targetedUser.getStat(UserStats.KILLSTREAK));
			player.sendMessage("");
		}

		player.sendMessage("§aRank: §e" + targetedUser.getUserRank());
		player.sendMessage("");

		player.sendMessage("§b> Automatic Data:");
		for (UserStats stat : UserStats.values()) {
			if (stat.isAutoManageable()) {
				player.sendMessage("§b" + Character.toUpperCase(stat.name().toLowerCase().charAt(0)) + stat.name().toLowerCase().substring(1).replace("_", " ") + ": §e" + targetedUser.getStat(stat));
			}
		}
	}
}
