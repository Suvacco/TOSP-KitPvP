package oldschoolproject.utils.loaders.command;

import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public abstract class BaseCommandWithTab extends BaseCommand implements TabExecutor {
  
	public BaseCommandWithTab(String command) {
		super(command);
	}

	public abstract List<String> onTabComplete(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString);
}
