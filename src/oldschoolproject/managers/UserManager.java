package oldschoolproject.managers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import oldschoolproject.entities.User;

public class UserManager {
	
	private static Map<Player, User> userMap = new HashMap<>();
	
	public static void registerUser(Player p) {
		userMap.put(p, new User(p));
	}
	
	public static void unregisterUser(Player p) {
		userMap.remove(p);
	}
	
	public static User getUser(Player player) {
		return userMap.get(player);
	}

}
