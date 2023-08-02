package oldschoolproject.managers;

import java.util.HashMap;
import java.util.Map;

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
}
