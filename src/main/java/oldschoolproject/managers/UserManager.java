package oldschoolproject.managers;

import java.util.*;

import oldschoolproject.exceptions.OperationFailException;
import oldschoolproject.users.UserRank;
import oldschoolproject.users.UserStats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import oldschoolproject.users.User;

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
		Player target = Bukkit.getPlayer(playerName);

		if (target != null) {

			if (target.hasPermission(permission)) {
				throw new OperationFailException("The player \"" + playerName + "\" already has the permission \"" + permission + "\"");
			}

			getUser(target).getPermissionAttachment().setPermission(permission, true);
			return;
		}

		User user = DatabaseManager.findUserByName(playerName);

		if (user != null) {
			List<String> perm = (ArrayList<String>)user.getStat(UserStats.PERMISSIONS);

			if (perm.contains(permission)) {
				throw new OperationFailException("The player \"" + playerName + "\" already has the permission \"" + permission + "\"");
			}

			perm.add(permission);

			DatabaseManager.updateUser(user, UserStats.PERMISSIONS, perm);
			return;
		}

		throw new OperationFailException("Player \"" + playerName + "\" not found neither in the server or the database");
	}

	public static void removePermission(String playerName, String permission) throws OperationFailException {
		Player target = Bukkit.getPlayer(playerName);

		if (target != null) {

			if (!target.hasPermission(permission)) {
				throw new OperationFailException("The player \"" + playerName + "\" doesn't have the permission \"" + permission + "\"");
			}

			getUser(target).getPermissionAttachment().unsetPermission(permission);
			return;
		}

		User user = DatabaseManager.findUserByName(playerName);

		if (user != null) {
			List<String> perms = (ArrayList<String>)user.getStat(UserStats.PERMISSIONS);

			if (!perms.contains(permission)) {
				throw new OperationFailException("The player \"" + playerName + "\" doesn't have the permission \"" + permission + "\"");
			}

			perms.remove(permission);

			DatabaseManager.updateUser(user, UserStats.PERMISSIONS, perms);
			return;
		}

		throw new OperationFailException("Player \"" + playerName + "\" not found neither in the server or the database");
	}

	public static boolean hasPermission(String playerName, String permission) throws OperationFailException {
		Player target = Bukkit.getPlayer(playerName);

		if (target != null) {
			return target.hasPermission(permission);
		}

		User user = DatabaseManager.findUserByName(playerName);

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
			List<String> perms = (ArrayList)user.getStat(UserStats.PERMISSIONS);

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

	public static void printUser(Player viewer, String userName) throws OperationFailException {
		Player target = Bukkit.getPlayer(userName);

		User dbUser = DatabaseManager.findUserByName(userName);

		if (dbUser == null && target == null) {
			throw new OperationFailException("Player \"" + userName + "\" not found neither in the server or the database");
		}

		User sessionUser = getUser(target);

		viewer.sendMessage("");
		viewer.sendMessage("§7Viewing the user: §a" + userName);

		if (target != null) {

			viewer.sendMessage("");
			viewer.sendMessage("§c§lSESSION DATA >");
			viewer.sendMessage("§cRank: §e" + sessionUser.getUserRank().name().toUpperCase());
			viewer.sendMessage("§cKit: §e" + (sessionUser.hasKit() ? sessionUser.getKit().getName() : "None"));
			viewer.sendMessage("§cWarp: §e" + sessionUser.getWarp().getName());
			viewer.sendMessage("§cGuard: §e" + sessionUser.getUserGuard().toString());
			viewer.sendMessage("§cKillstreak: §e" + sessionUser.getStat(UserStats.KILLSTREAK));
			viewer.sendMessage("");
		}

		if (dbUser != null) {
			viewer.sendMessage("");
			viewer.sendMessage("§b§lDB DATA >");
			viewer.sendMessage("§bRank: §e" + dbUser.getUserRank().name().toUpperCase());
			viewer.sendMessage("");

			viewer.sendMessage("§a§lSTATS >");
		}

		for (UserStats stat : UserStats.values()) {
			if (stat.isNotControllable()) {
				viewer.sendMessage("§a" + Character.toUpperCase(stat.name().toLowerCase().charAt(0)) + stat.name().toLowerCase().substring(1).replace("_", " ") + ":" +
						" §c" + (target == null ? "-" : sessionUser.getStat(stat) + " §b" + (dbUser == null ? "-" : dbUser.getStat(stat))));
			}
		}
		viewer.sendMessage("");
	}
}
