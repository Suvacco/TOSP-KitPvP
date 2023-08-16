package oldschoolproject.managers;

import java.util.*;

import oldschoolproject.exceptions.OperationFailException;
import oldschoolproject.users.UserRank;
import oldschoolproject.users.UserStats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import oldschoolproject.users.User;

@SuppressWarnings("unchecked")
public class UserManager {
	
	private static Map<Player, User> userMap = new HashMap<>();
	
	public static void registerUser(User user) {
		userMap.put(user.getPlayer(), user);
	}
	
	public static void unregisterUser(Player p) {
		userMap.remove(p);
	}
	
	public static User getUser(Player player) {
		return userMap.get(player);
	}

	public static void addPermission(String playerName, String permission) throws OperationFailException {
		if (hasPermission(playerName, permission)) {
			throw new OperationFailException("The player \"" + playerName + "\" already has the permission \"" + permission + "\"");
		}

		Player target = Bukkit.getPlayer(playerName);

		// Server
		if (target != null) {
			getUser(target).addPermission(permission);
			return;
		}

		User user = DatabaseManager.findUserByName(playerName);

		// Database
		if (user != null) {
			List<String> perm = (ArrayList<String>)user.getStat(UserStats.PERMISSIONS);

			perm.add(permission);

			DatabaseManager.updateUser(user, UserStats.PERMISSIONS, perm);
		}
	}

	public static void removePermission(String playerName, String permission) throws OperationFailException {
		if (!hasPermission(playerName, permission)) {
			throw new OperationFailException("The player \"" + playerName + "\" doesn't have the permission \"" + permission + "\"");
		}

		Player target = Bukkit.getPlayer(playerName);

		// Server
		if (target != null) {
			getUser(target).removePermission(permission);
			return;
		}

		User user = DatabaseManager.findUserByName(playerName);

		// Database
		if (user != null) {
			List<String> perms = (List<String>) user.getStat(UserStats.PERMISSIONS);

			perms.remove(permission);

			DatabaseManager.updateUser(user, UserStats.PERMISSIONS, perms);
		}
	}

	public static boolean hasPermission(String playerName, String permission) throws OperationFailException {
		Player target = Bukkit.getPlayer(playerName);

		// Server
		if (target != null) {
			return target.hasPermission(permission);
		}

		User user = DatabaseManager.findUserByName(playerName);

		// Database
		if (user != null) {
			List<String> perms = (ArrayList<String>)user.getStat(UserStats.PERMISSIONS);

			perms.addAll(user.getUserRank().getPermissions());

			return perms.contains(permission);
		}

		throw new OperationFailException("Player \"" + playerName + "\" not found neither in the server or the database");
	}

	public static List<String> getPermissions(String playerName) throws OperationFailException {
		Player target = Bukkit.getPlayer(playerName);

		if (target != null) {
			return new ArrayList<String>(getUser(target).getPermissionAttachment().getPermissions().keySet());
		}

		User user = DatabaseManager.findUserByName(playerName);

		if (user != null) {
			List<String> perms = (ArrayList<String>)user.getStat(UserStats.PERMISSIONS);

			perms.addAll(user.getUserRank().getPermissions());

			return perms;
		}

		throw new OperationFailException("Player \"" + playerName + "\" not found neither in the server or the database");
	}

	public static void setRank(String playerName, String rankName) throws OperationFailException {
		Player target = Bukkit.getPlayer(playerName);

		if (Arrays.stream(UserRank.values()).noneMatch(userRank -> { return userRank.name().equalsIgnoreCase(rankName); })) {
			throw new OperationFailException("Insert rank is invalid");
		}

		if (target != null) {
			getUser(target).setUserRank(rankName);

			getUser(target).refreshRankPermissions();

			TagManager.setPrefix(getUser(target), getUser(target).getUserRank().getTag());
			return;
		}

		User user = DatabaseManager.findUserByName(playerName);

		if (user != null) {
			DatabaseManager.updateUser(user, UserStats.RANK, UserRank.valueOf(rankName));
			return;
		}

		throw new OperationFailException("Player \"" + playerName + "\" not found neither in the server or the database");
	}

	public static void viewUserDetails(Player viewer, String userName) throws OperationFailException {
		Player target = Bukkit.getPlayer(userName);

		if (target != null) {
			User user = UserManager.getUser(target);

			viewer.sendMessage("");
			viewer.sendMessage("§7The player you're viewing is currently §aonline");

			printUserDetails(viewer, user);

			return;
		}

		User dbUser = DatabaseManager.findUserByName(userName);

		if (dbUser != null) {

			viewer.sendMessage("");
			viewer.sendMessage("§7The player you're viewing is currently §coffline");

			printUserDetails(viewer, dbUser);

			return;
		}

		throw new OperationFailException("Player \"" + userName + "\" not found neither in the server or the database");
	}

	private static void printUserDetails(Player viewer, User user) {
		viewer.sendMessage("");

		viewer.sendMessage("");
		viewer.sendMessage("§6§lUSER DATA >");
		viewer.sendMessage("");
		if (user.getPlayer() != null) {
			viewer.sendMessage("§6(Session Data)");
			viewer.sendMessage("§eKit: §7" + (user.hasKit() ? user.getKit().getName() : "None"));
			viewer.sendMessage("§eWarp: §7" + user.getWarp().getName());
			viewer.sendMessage("§eGuard: §7" + user.getUserGuard().toString());
			viewer.sendMessage("§eKillstreak: §7" + user.getStat(UserStats.KILLSTREAK));
			viewer.sendMessage("");
		}
		viewer.sendMessage("§eRank: §7" + user.getUserRank().name().toUpperCase());

		for (UserStats stat : UserStats.values()) {
			if (stat.isNotControllable()) {
				viewer.sendMessage("§e" + Character.toUpperCase(stat.name().toLowerCase().charAt(0)) + stat.name().toLowerCase().substring(1).replace("_", " ") + ": §7" + user.getStat(stat));
			}
		}
	}
}
