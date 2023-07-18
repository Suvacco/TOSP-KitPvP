package oldschoolproject.utils.commands;

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

	public void loadCommandsAndRegister() {
		int i = 0;
		
		String srcPackage = CommandLoader.class.getPackage().getName().substring(0, CommandLoader.class.getPackage().getName().indexOf('.'));
		
		try {
			Field commandmapfield = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			commandmapfield.setAccessible(true);
			commandMap = (SimpleCommandMap) commandmapfield.get(Bukkit.getServer());
		} catch (Exception e) {
			Bukkit.getLogger().warning("[CommandLoader] Error when trying to access the Command Map");
		}
		
		for (Class<?> commandClass : (Iterable<Class<?>>) ClassGetter.getClassesForPackage(Main.getInstance(), srcPackage)) {
			if (BaseCommandWithTab.class.isAssignableFrom(commandClass) && !commandClass.equals(BaseCommand.class) && !commandClass.equals(BaseCommandWithTab.class)) {
				try {
					BaseCommandWithTab command = (BaseCommandWithTab) commandClass.getConstructor().newInstance();
					
					registerCommand((CommandExecutor) command, command.getName(), command.getDescription(), command.getAliases()).setTabCompleter((TabCompleter) command);
					
					i++;
				} catch (Exception e) {
					Main.getInstance().getLogger().warning("[CommandLoader] Error when registering the command " + commandClass.getName() + " (TabCompleter)");
				}
				continue;
			}
			if (BaseCommand.class.isAssignableFrom(commandClass) && !commandClass.equals(BaseCommand.class) && !commandClass.equals(BaseCommandWithTab.class)) {
				try {
					BaseCommand command = (BaseCommand) commandClass.getConstructor().newInstance();
					
					registerCommand((CommandExecutor) command, command.getName(), command.getDescription(), command.getAliases());
					
					i++;
				} catch (Exception e) {
					e.printStackTrace();
					Main.getInstance().getLogger().warning("[CommandLoader] Error when registering the command " + commandClass.getName());
				}
			}
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
