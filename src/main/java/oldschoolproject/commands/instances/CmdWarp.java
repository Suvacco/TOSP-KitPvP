package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oldschoolproject.managers.UserManager;
import oldschoolproject.managers.WarpManager;
import oldschoolproject.users.User;
import oldschoolproject.warps.WarpLoader;

public class CmdWarp extends BaseCommand {

	public CmdWarp() {
		super("warp");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player p = (Player)sender;
		User user = UserManager.getUser(p);
		
		if (args.length == 0) {
			
			StringBuilder sb = new StringBuilder();
			
			WarpLoader.getWarpInstances().forEach(warp -> sb.append(warp.getName()).append(", "));
			
			p.sendMessage("§cErro: /warp [" + sb.toString().substring(0, sb.toString().length() - 2) + "]");
			return;
		}
		
		WarpManager.changeWarp(user, args[0]);
	}
}
