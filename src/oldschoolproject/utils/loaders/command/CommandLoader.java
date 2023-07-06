package oldschoolproject.utils.loaders.command;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import oldschoolproject.Main;
import oldschoolproject.utils.ClassGetter;

public class CommandLoader {

	private static SimpleCommandMap commandMap;
	
	public CommandLoader() {
		loadCommandsAndRegister();
	}

	@SuppressWarnings("deprecation")
	public static void loadCommandsAndRegister() {
		try {
			Field commandmapfield = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			commandmapfield.setAccessible(true);
			commandMap = (SimpleCommandMap) commandmapfield.get(Bukkit.getServer());
		} catch (Exception e) {
			Bukkit.getLogger().warning("[CommandLoader] Error when trying to access the Command Map");
		}
		int i = 0;
		for (Class<?> commandClass : (Iterable<Class<?>>) ClassGetter.getClassesForPackage(Main.getInstance(), "oldschoolproject.commands")) {
			if (BaseCommandWithTab.class.isAssignableFrom(commandClass)) {
				try {
					BaseCommandWithTab command = null;
					try {
						Constructor<?> con = commandClass.getConstructor(new Class[] { Main.class });
						command = (BaseCommandWithTab) con.newInstance(new Object[] { Main.getInstance() });
					} catch (Exception ex) {
						command = (BaseCommandWithTab) commandClass.newInstance();
					}
					registerCommand((CommandExecutor) command, command.getName(), command.getDescription(), command.getAliases()).setTabCompleter((TabCompleter) command);
				} catch (Exception e) {
					Main.getInstance().getLogger().warning("[CommandLoader] Error when registering the command " + commandClass.getName() + " (TabCompleter)");
				}
				continue;
			}
			if (BaseCommand.class.isAssignableFrom(commandClass)) {
				try {
					BaseCommand command = null;
					try {
						Constructor<?> con = commandClass.getConstructor(new Class[] { Main.class });
						command = (BaseCommand) con.newInstance(new Object[] { Main.getInstance() });
					} catch (Exception ex) {
						command = (BaseCommand) commandClass.newInstance();
					}
					registerCommand((CommandExecutor) command, command.getName(), command.getDescription(), command.getAliases());
				} catch (Exception e) {
					e.printStackTrace();
					Main.getInstance().getLogger().warning("[CommandLoader] Error when registering the command " + commandClass.getName());
				}
			}
			i++;
		}
		Main.getInstance().getLogger().info("[CommandLoader] " + i + " commands loaded");
	}

	private static PluginCommand registerCommand(CommandExecutor executor, String name, String description, String[] aliases) {
		try {
			PluginCommand command = Main.getInstance().getCommand(name.toLowerCase());
			if (command == null) {
				Constructor<?> constructor = PluginCommand.class.getDeclaredConstructor(new Class[] { String.class, Plugin.class });
				constructor.setAccessible(true);
				command = (PluginCommand) constructor.newInstance(new Object[] { name, Main.getInstance() });
			}
			command.setExecutor(executor);
			command.setAliases(Arrays.asList(aliases));
			command.setDescription(description);
			commandMap.register(name, (Command) command);
			return command;
		} catch (Exception e) {
			Main.getInstance().getLogger().warning("[CommandLoader] Error when registering the command " + name);
			return null;
		}
	}
}
