package oldschoolproject.managers;

import oldschoolproject.databases.DatabaseLoader;
import oldschoolproject.users.User;

public class DatabaseManager {

    public static void authUser(User user) {
        DatabaseLoader.getDatabaseConnection().loadUser(user);
    }

    public static void saveUser(User user) { DatabaseLoader.getDatabaseConnection().saveUser(user); }

}
