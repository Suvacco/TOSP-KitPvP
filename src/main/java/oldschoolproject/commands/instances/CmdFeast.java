package oldschoolproject.commands.instances;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oldschoolproject.commands.BaseCommand;
import oldschoolproject.exceptions.FeastManagementException;
import oldschoolproject.managers.FeastManager;

public class CmdFeast extends BaseCommand {

	public CmdFeast() {
		super("feast");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player p = (Player)sender;
		
		if (args.length == 0) {
			p.sendMessage("§cErro: /feast [set : delete]");
			return;
		}
		
		try {
		
			if (args[0].equalsIgnoreCase("set")) {
				
				if (args.length < 1) {
					p.sendMessage("§cErro: /feast set [id]");
					return;
				}
				
				FeastManager.createFeast(args[1], p.getLocation());
				return;
			}
			
			if (args[0].equalsIgnoreCase("delete")) {
				
				if (args.length < 1) {
					p.sendMessage("§cErro: /feast delete [id]");
					return;
				}
				
				FeastManager.deleteFeast(args[1]);
				return;
			}
		
		} catch (FeastManagementException e) {
			p.sendMessage("§cErro: " + e.getMessage());
		}
		
		p.sendMessage("§cErro: /feast [set : delete]");
	}

}
