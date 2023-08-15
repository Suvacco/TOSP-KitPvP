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
		KitLoader.getKitInstances().forEach(kit -> sb.append(kit.getName()).append(", "));

		String message = "[" + (sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 2) : "") + "]";

		try {

			if (args.length == 0) {
				p.sendMessage("§cError: /kit " + message);
				return;
			}

			KitManager.setKit(user, args[0]);

		} catch (OperationFailException e) {
			p.sendMessage(e.getMessage());
			return;
		}
		p.sendMessage("§cError: /kit " + message);
	}
}
