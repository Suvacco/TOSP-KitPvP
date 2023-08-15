package oldschoolproject.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public abstract class BaseCommandWithTab extends BaseCommand implements TabExecutor  {
  
	public BaseCommandWithTab(String command, boolean permission) {
		super(command, permission);
	}

	public abstract List<String> onTabComplete(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString);
}
