package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import oldschoolproject.exceptions.OperationFailException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oldschoolproject.managers.UserManager;
import oldschoolproject.managers.WarpManager;
import oldschoolproject.users.User;
import oldschoolproject.warps.WarpLoader;

public class CmdWarp extends BaseCommand {

	public CmdWarp() {
		super("warp", false);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player p = (Player)sender;
		User user = UserManager.getUser(p);

		StringBuilder sb = new StringBuilder();
		WarpLoader.getWarpInstances().forEach(warp -> sb.append(warp.getName()).append(", "));

		String warpsMessage = "[" + (sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 2) : "") + "]";

		if (args.length == 0) {
			p.sendMessage("§cError: /warp [" + warpsMessage + "]");
			return;
		}

		try {

			WarpManager.changeWarp(user, args[0]);
			p.sendMessage("§aYou got teleported to warp \"" + args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase() + "\"");

		} catch (OperationFailException e) {
			p.sendMessage(e.getMessage());
			return;
		}

		p.sendMessage("§cError: /warp [" + warpsMessage + "]");
	}
}
