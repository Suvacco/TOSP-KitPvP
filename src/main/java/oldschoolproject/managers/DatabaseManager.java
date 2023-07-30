package oldschoolproject.managers;

import oldschoolproject.databases.DatabaseLoader;
import oldschoolproject.users.User;
import org.bukkit.entity.Player;

public class DatabaseManager {

    public static void authUser(User user) {
        DatabaseLoader.getDatabaseConnection().authUser(user);
    }

    public static void saveUser(User user) {
        DatabaseLoader.getDatabaseConnection().saveUser(user);
    }

}
