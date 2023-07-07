package oldschoolproject.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oldschoolproject.managers.InventoryManager;
import oldschoolproject.utils.loaders.command.BaseCommand;

public class CInventory extends BaseCommand {

	public CInventory() {
		super("kitinv");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player p = (Player)sender;
		
		InventoryManager.openInventory(p);
	}

}
