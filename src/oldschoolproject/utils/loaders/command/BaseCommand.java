package oldschoolproject.utils.loaders.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand implements CommandExecutor {
  
  public String description;
  
  public String[] aliases;
  
  private String name;
  
  public BaseCommand(String command) {
    this.name = command;
    this.aliases = new String[0];
    this.description = "";
  }
  
  public String[] getAliases() {
    return this.aliases;
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public String getName() {
    return this.name;
  }
  
  public abstract void onCommand(CommandSender sender, String[] args);
  
  public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
	  onCommand(sender, args);
	  return false;
	  }
}
