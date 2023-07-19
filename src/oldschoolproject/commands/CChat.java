package oldschoolproject.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import oldschoolproject.utils.commands.BaseCommand;

public class CChat extends BaseCommand {

	public CChat() {
		super("chat");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		for (int i = 0; i < 100; i++) {
			Bukkit.broadcastMessage("");
		}
	}

}
