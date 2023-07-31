package oldschoolproject.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import oldschoolproject.utils.commands.BaseCommand;
import oldschoolproject.utils.listeners.BaseListener;

public class CmdChat extends BaseCommand implements BaseListener {
	
	private static boolean chatStatus = true;

	public CmdChat() {
		super("chat");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§cErro: /chat [Stop : Resume : Clear]");
			return;
		}
		
		if (args[0].equalsIgnoreCase("stop")) {
			if (!chatStatus) {
				sender.sendMessage("§cErro: O chat já foi desativado");
				return;
			}
			
			sender.sendMessage("§aChat desativado!");
			chatStatus = false;
			return;
		}
		
		if (args[0].equalsIgnoreCase("resume")) {
			if (chatStatus) {
				sender.sendMessage("§cErro: O chat já foi ativado");
				return;
			}
			
			sender.sendMessage("§aChat ativado!");
			chatStatus = true;
			return;
		}
		
		if (args[0].equalsIgnoreCase("clear")) {
			for (int i = 0; i < 100; i++) {
				Bukkit.broadcastMessage("");
			}
			sender.sendMessage("§aChat limpo!");
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if (!chatStatus && !e.getPlayer().isOp()) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("§cO chat foi desativado");
		}
	}
}
