package oldschoolproject.utils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import lombok.Getter;

public abstract class BaseCommand implements CommandExecutor {
  
  @Getter
  public String description;
  
  @Getter
  public String[] aliases;
  
  @Getter
  private String name;
  
  public BaseCommand(String command) {
    this.name = command;
    this.aliases = new String[0];
    this.description = "";
  }
  
  public abstract void onCommand(CommandSender sender, String[] args);
  
  public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
	  onCommand(sender, args);
	  return false;
	  }
}
