package oldschoolproject.commands;

import oldschoolproject.databases.DataType;
import oldschoolproject.databases.DatabaseLoader;
import oldschoolproject.managers.DatabaseManager;
import oldschoolproject.managers.TagManager;
import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.users.ranks.Rank;
import oldschoolproject.utils.commands.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CmdRank extends BaseCommand {

    public CmdRank() {
        super("rank");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player)sender;

        if (args.length == 0) {
            player.sendMessage("§cErro: /rank [set]");
            return;
        }

        if (args[0].equalsIgnoreCase("set")) {
            StringBuilder sb = new StringBuilder();
            Arrays.stream(Rank.values()).forEach(rank -> sb.append(rank.name()).append(" : "));

            if (args.length == 1) {
                player.sendMessage("§cErro: /rank set <player> <rank>");
                return;
            }

            if (args.length == 2) {
                player.sendMessage("§cErro: /rank set " + args[1] + " [" + sb.substring(0, sb.length() - 3) + "]");
                return;
            }

            Player onlinePlayer = Bukkit.getPlayer(args[1]);

            User databasePlayer = DatabaseManager.findUserByName(args[1]);

            if (databasePlayer == null && onlinePlayer == null) {
                player.sendMessage("§cErro: Player não encontrado no banco de dados");
                return;
            }

            if (!sb.toString().contains(args[2].toUpperCase())) {
                player.sendMessage("§cErro: Rank inválido");
                return;
            }

            player.sendMessage("§aRank do jogador " + args[1] + " §aatualizado para: " + args[2].toUpperCase());

            // If player is offline
            if (onlinePlayer == null) {
                DatabaseManager.updateUser(databasePlayer, DataType.RANK, Rank.valueOf(args[2].toUpperCase()));
                return;
            }

            User onlineUser = UserManager.getUser(onlinePlayer);

            onlineUser.setRank(args[2].toUpperCase());

            TagManager.setPrefix(onlineUser, onlineUser.getRank().getTag());
            return;
        }

        player.sendMessage("§cErro: /rank [set]");
    }
}
