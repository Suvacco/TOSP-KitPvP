package oldschoolproject.managers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import oldschoolproject.users.User;

public class UserManager {
	
	private static Map<Player, User> userMap = new HashMap<>();
	
	public static User registerUser(Player p) {
		User user = new User(p);

		// Download all data from cloud to user object
		DatabaseManager.authUser(user);

		// Reset player warp
		WarpManager.setWarp(user, WarpManager.findWarp("Spawn"));

		// Set players tag on the server
		TagManager.setPrefix(user, user.getRank().getTag());

		userMap.put(p, user);

		return user;
	}
	
	public static void unregisterUser(Player p) {
		DatabaseManager.saveUser(getUser(p));

		userMap.remove(p);
	}
	
	public static User getUser(Player player) {
		return userMap.get(player);
	}
}
