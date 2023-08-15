package oldschoolproject.commands;

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

  @Getter
  private boolean permission;
  
  public BaseCommand(String command, boolean permission) {
    this.name = command;
    this.aliases = new String[0];
    this.description = "";
    this.permission = permission;
  }

  public abstract void onCommand(CommandSender sender, String[] args);

  public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
    if (this.permission && !sender.hasPermission("rank.cmd." + this.name) && !sender.hasPermission("perm.cmd." + this.name) && !sender.hasPermission("cmd.*")) {
      sender.sendMessage("§cError: You don't have permission to use this command");
      return true;
    }

    onCommand(sender, args);
    return false;
  }
}
