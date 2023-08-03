package oldschoolproject.managers;

import oldschoolproject.users.UserStats;
import oldschoolproject.databases.DatabaseLoader;
import oldschoolproject.users.User;

public class DatabaseManager {

    public static void loadUser(User user) {
        DatabaseLoader.getDatabaseConnection().loadUser(user);
    }

    public static void saveUser(User user) {
        DatabaseLoader.getDatabaseConnection().saveUser(user);
    }

    public static void updateUser(User user, UserStats userStats, Object value) {
        DatabaseLoader.getDatabaseConnection().updateUser(user, userStats, value);
    }

    public static User findUserByName(String name) {
        return DatabaseLoader.getDatabaseConnection().findUserByName(name);
    }
}
