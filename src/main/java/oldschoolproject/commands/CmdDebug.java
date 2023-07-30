package oldschoolproject.commands;

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
		player.sendMessage("§bState: §e" + user.getState().toString());
		player.sendMessage("§bRank: §e" + user.getRank());
		player.sendMessage("§bKills: §e" + user.getKills());
		player.sendMessage("§bDeaths: §e" + user.getDeaths());
		player.sendMessage("§bCoins: §e" + user.getCoins());
		player.sendMessage("§bDuels Count: §e" + user.getDuelsCount());
		player.sendMessage("§bDuels Wins: §e" + user.getDuelsWins());
		player.sendMessage("§bDuels Losses: §e" + user.getDuelsLosses());
		player.sendMessage("");
	}
}
