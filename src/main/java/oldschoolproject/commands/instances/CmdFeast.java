package oldschoolproject.commands.instances;

import oldschoolproject.exceptions.OperationFailException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oldschoolproject.commands.BaseCommand;
import oldschoolproject.managers.FeastManager;

public class CmdFeast extends BaseCommand {

	public CmdFeast() {
		super("feast", true);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player p = (Player)sender;
		
		if (args.length == 0) {
			p.sendMessage("§cError: /feast [create : delete : list : movehere : goto]");
			return;
		}
		
		try {
		
			if (args[0].equalsIgnoreCase("create")) {

				if (args.length < 2) {
					p.sendMessage("§cError: /feast create <id>");
					return;
				}

				FeastManager.createFeast(args[1], p.getLocation());
				p.sendMessage("§aFeast \"" + args[1] + "\" created successfully!");
				return;
			}
			
			if (args[0].equalsIgnoreCase("delete")) {
				
				if (args.length < 2) {
					p.sendMessage("§cError: /feast delete <id>");
					return;
				}
				
				FeastManager.deleteFeast(args[1]);
				p.sendMessage("§aFeast \"" + args[1] + "\" deleted successfully!");
				return;
			}

			if (args[0].equalsIgnoreCase("list")) {
				p.sendMessage("§aFeast ID List: " + FeastManager.getFeastsList());
				return;
			}

			if (args[0].equalsIgnoreCase("movehere")) {

				if (args.length < 2) {
					p.sendMessage("§cError: /feast movehere <id>");
					return;
				}

				FeastManager.moveFeast(args[1], p.getLocation());
				p.sendMessage("§aFeast \"" + args[1] + "\" location updated successfully!");
				return;
			}

			if (args[0].equalsIgnoreCase("goto")) {

				if (args.length < 2) {
					p.sendMessage("§cError: /feast goto <id>");
					return;
				}

				p.teleport(FeastManager.findFeast(args[1]).getLocation());
				p.sendMessage("§aTeleported to hologram \"" + args[1] + "\" successfully!");
				return;
			}
		
		} catch (OperationFailException e) {
			p.sendMessage(e.getMessage());
			return;
		}

		p.sendMessage("§cError: /feast [set : delete : list : movehere : goto]");
	}
}
