package oldschoolproject.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oldschoolproject.managers.UserManager;
import oldschoolproject.managers.WarpManager;
import oldschoolproject.users.User;
import oldschoolproject.utils.commands.BaseCommand;
import oldschoolproject.utils.warps.WarpLoader;

public class CWarp extends BaseCommand {

	public CWarp() {
		super("warp");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player p = (Player)sender;
		User user = UserManager.getUser(p);
		
		if (args.length == 0) {
			
			StringBuilder sb = new StringBuilder();
			
			WarpLoader.getWarpInstances().stream().forEach(warp -> sb.append(warp.getName() + ", "));
			
			p.sendMessage("§cErro: /warp [" + sb.toString().substring(0, sb.toString().length() - 2) + "]");
			return;
		}
		
		WarpManager.changeWarp(user, args[0]);
	}
}
