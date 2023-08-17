package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import oldschoolproject.exceptions.OperationFailException;
import oldschoolproject.managers.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import oldschoolproject.events.BaseListener;

public class CmdChat extends BaseCommand {
	
	public CmdChat() {
		super("chat", true);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§cError: /chat [disable : enable : wipe]");
			return;
		}

		if (args[0].equalsIgnoreCase("disable")) {

			if (!ChatManager.isEnabled()) {
				sender.sendMessage("§cError: Chat is already disabled");
				return;
			}

			ChatManager.chatEnabled(false);
			sender.sendMessage("§aChat disabled!");
			return;
		}

		if (args[0].equalsIgnoreCase("enable")) {

			if (ChatManager.isEnabled()) {
				sender.sendMessage("§cError: Chat is already enabled");
				return;
			}

			ChatManager.chatEnabled(true);
			sender.sendMessage("§aChat enabled!");
			return;
		}

		if (args[0].equalsIgnoreCase("wipe")) {
			ChatManager.wipeChat();
			sender.sendMessage("§aChat wiped!");
			return;
		}

		sender.sendMessage("§cError: /chat [disable : enable : wipe]");
	}
}
