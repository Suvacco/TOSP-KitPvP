package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdKill extends BaseCommand {
    public CmdKill() {
        super("kill");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player)sender;

        if (args.length == 0) {
            player.setHealth(0);
            return;
        }

        Player victim = Bukkit.getPlayer(args[0]);

        if (victim == null) {
            player.sendMessage("§cErro: Player inexistente");
            return;
        }

        victim.setHealth(0);
        player.sendMessage("§aPlayer assassinado!");
    }
}
