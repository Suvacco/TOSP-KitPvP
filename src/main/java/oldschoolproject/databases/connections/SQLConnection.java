package oldschoolproject.databases.connections;

import oldschoolproject.users.UserStats;
import oldschoolproject.databases.DatabaseConnection;
import oldschoolproject.users.User;

public class SQLConnection implements DatabaseConnection {


    public SQLConnection(String url, String user, String password) {

    }

    @Override
    public void connect() {

    }

    @Override
    public void loadUser(User user) {

    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public void updateUser(User user, UserStats userStats, Object value) {

    }

    @Override
    public User findUserByName(String name) {
        return null;
    }
}
