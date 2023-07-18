package oldschoolproject.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oldschoolproject.managers.KitManager;
import oldschoolproject.managers.UserManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.commands.BaseCommand;
import oldschoolproject.utils.kits.KitLoader;

public class CKit extends BaseCommand {

	public CKit() {
		super("kit");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player p = (Player)sender;
		User user = UserManager.getUser(p);
		
		if (args.length == 0) {
			
			StringBuilder sb = new StringBuilder();
			
			KitLoader.getKitInstances().stream().forEach(kit -> sb.append(kit.getName() + ", "));
			
			p.sendMessage("§cErro: /kit [" + sb.toString().substring(0, sb.toString().length() - 2) + "]");
			return;
		}
		
		KitManager.setKit(user, args[0]);
	}

}
