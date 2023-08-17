package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import oldschoolproject.exceptions.OperationFailException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oldschoolproject.managers.KitManager;
import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.kits.KitLoader;

public class CmdKit extends BaseCommand {

	public CmdKit() {
		super("kit", false);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player p = (Player)sender;
		User user = UserManager.getUser(p);

		StringBuilder sb = new StringBuilder();

		KitLoader.getKitInstances().forEach(kit -> {
			if (user.getPermissionStorage().hasPermission("perm.kit." + kit.getName().toLowerCase()) || user.getPermissionStorage().hasPermission("rank.kit." + kit.getName().toLowerCase())) {
				sb.append(kit.getName()).append(", ");
			}
		});

		if (sb.length() == 0) {
			p.sendMessage("§cError: You don't have any kit");
			return;
		}

		String message = "[" + sb.toString().substring(0, sb.toString().length() - 2) + "]";

		if (args.length == 0) {
			p.sendMessage("§cError: /kit " + message);
			return;
		}

		try {

			KitManager.setKit(user, args[0]);
		} catch (OperationFailException e) {
			p.sendMessage(e.getMessage());
		}
	}
}
