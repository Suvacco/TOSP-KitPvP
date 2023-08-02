package oldschoolproject.managers;

import oldschoolproject.databases.DataType;
import oldschoolproject.databases.DatabaseLoader;
import oldschoolproject.users.User;

public class DatabaseManager {

    public static void loadUser(User user) {
        DatabaseLoader.getDatabaseConnection().loadUser(user);
    }

    public static void saveUser(User user) {
        DatabaseLoader.getDatabaseConnection().saveUser(user);
    }

    public static void updateUser(User user, DataType dataType, Object value) {
        DatabaseLoader.getDatabaseConnection().updateUser(user, dataType, value);
    }

    public static User findUserByName(String name) {
        return DatabaseLoader.getDatabaseConnection().findUserByName(name);
    }
}
