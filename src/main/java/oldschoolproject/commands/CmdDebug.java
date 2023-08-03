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
		player.sendMessage("§bKills: §e" + user.getStat(UserStats.KILLS));
		player.sendMessage("§bDeaths: §e" + user.getStat(UserStats.DEATHS));
		player.sendMessage("§bCoins: §e" + user.getStat(UserStats.COINS));
		player.sendMessage("§bDuels Count: §e" + user.getStat(UserStats.DUELS_COUNT));
		player.sendMessage("§bDuels Wins: §e" + user.getStat(UserStats.DUELS_WINS));
		player.sendMessage("§bDuels Losses: §e" + user.getStat(UserStats.DUELS_LOSSES));
		player.sendMessage("");
	}
}
