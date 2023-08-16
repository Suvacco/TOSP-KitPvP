package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSound extends BaseCommand {
    public CmdSound() {
        super("sound", true);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("§cError: /sound <sound>");
            return;
        }

        player.playSound(player, Sound.valueOf(args[0].toUpperCase()), 15.0F, 1.0F);
        player.sendMessage("§aPlaying sound: " + args[0]);
    }
}
