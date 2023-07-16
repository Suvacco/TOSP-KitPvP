package oldschoolproject.commands;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oldschoolproject.kits.managers.KitEnum;
import oldschoolproject.kits.managers.KitManager;
import oldschoolproject.users.User;
import oldschoolproject.users.managers.UserManager;
import oldschoolproject.utils.commands.BaseCommand;

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
			
			Arrays.stream(KitEnum.values()).forEach(kit -> sb.append(kit.getStaticInstance().getName() + ", "));
			
			p.sendMessage("§cErro: /kit [" + sb.toString().substring(0, sb.toString().length() - 2) + "]");
			return;
		}
		
		KitManager.setKit(user, args[0]);
	}

}
