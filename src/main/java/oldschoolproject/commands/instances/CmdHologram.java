package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import oldschoolproject.holograms.HologramLoader;
import oldschoolproject.managers.HologramManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdHologram extends BaseCommand {

    public CmdHologram() {
        super("holo");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player p = (Player)sender;

        if (args.length == 0) {
            p.sendMessage("§cErro: /holo [create : delete : setline : addline : removeline : goto : movehere : list]");
            return;
        }

        if (args[0].equalsIgnoreCase("create")) {
            if (args.length < 3) {
                p.sendMessage("§cErro: /holo create <id> <firstline>");
                return;
            }

            StringBuilder sb = new StringBuilder();

            for (int i = 2; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }

            sb.deleteCharAt(sb.length() - 1);

            HologramManager.createHologram(args[1], sb.toString(), p.getLocation());
            return;
        }

        if (args[0].equalsIgnoreCase("addline")) {
            if (args.length < 3) {
                p.sendMessage("§cErro: /holo addline <id> <line>");
                return;
            }

            StringBuilder sb = new StringBuilder();

            for (int i = 2; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }

            sb.deleteCharAt(sb.length() - 1);

            HologramManager.addLine(args[1], sb.toString());
            return;
        }

        if (args[0].equalsIgnoreCase("delete")) {
            if (args.length < 2) {
                p.sendMessage("§cErro: /holo delete <id>");
                return;
            }

            HologramManager.deleteHologram(args[1]);
            return;
        }

        // /holo setline <id> <line> <text>
        if (args[0].equalsIgnoreCase("setline")) {
            if (args.length < 4) {
                p.sendMessage("§cErro: /holo setline <id> <line> <text>");
                return;
            }

            StringBuilder sb = new StringBuilder();

            for (int i = 3; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }

            sb.deleteCharAt(sb.length() - 1);

            HologramManager.setlineHologram(args[1], Integer.parseInt(args[2]), sb.toString());
            return;
        }

        if (args[0].equalsIgnoreCase("removeline")) {
            if (args.length < 3) {
                p.sendMessage("§cErro: /holo removeline <id> <line>");
                return;
            }

            HologramManager.removeLine(args[1], Integer.parseInt(args[2]));
            return;
        }

        if (args[0].equalsIgnoreCase("goto")) {
            if (args.length < 2) {
                p.sendMessage("§cErro: /holo goto <id>");
                return;
            }

            p.teleport(HologramManager.findHologram(args[1]).getLocation());
            return;
        }

        if (args[0].equalsIgnoreCase("movehere")) {
            if (args.length < 2) {
                p.sendMessage("§cErro: /holo movehere <id>");
                return;
            }

            HologramManager.moveHologram(args[1], p.getLocation());
            return;
        }

        if (args[0].equalsIgnoreCase("list")) {
            p.sendMessage("§bHologram list: " + HologramManager.getHologramList());
            return;
        }

        p.sendMessage("§cErro: /holo [create : delete : setline : addline : removeline : goto : movehere : list]");
    }
}
