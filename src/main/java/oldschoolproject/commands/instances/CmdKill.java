package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdKill extends BaseCommand {
    public CmdKill() {
        super("kill", false);
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
            sender.sendMessage("§cError: Player not found");
            return;
        }

        victim.setHealth(0);
        sender.sendMessage("§aPlayer \"" + args[0] + "\" murdered!");
    }
}
