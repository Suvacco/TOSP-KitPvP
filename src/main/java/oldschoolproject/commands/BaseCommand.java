package oldschoolproject.commands;

import oldschoolproject.managers.UserManager;
import oldschoolproject.permissions.PermissionStorage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import lombok.Getter;
import org.bukkit.entity.Player;

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
    if (sender instanceof Player) {
      PermissionStorage ps = UserManager.getUser((Player) sender).getPermissionStorage();
      if (this.permission && !ps.hasPermission("rank.cmd." + this.name) && !ps.hasPermission("perm.cmd." + this.name) && !ps.hasPermission("cmd.*")) {
        sender.sendMessage("§cError: You don't have permission to use this command");
        return true;
      }
    }

    onCommand(sender, args);
    return false;
  }
}
