package oldschoolproject.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oldschoolproject.utils.loaders.command.BaseCommand;

public class CKit extends BaseCommand {

	public CKit() {
		super("kit");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player p = (Player)sender;
		
		if (args.length == 0) {
			p.sendMessage("§cErro: /kit <nome_do_kit>");
			return;
		}
		
		
	}

}
