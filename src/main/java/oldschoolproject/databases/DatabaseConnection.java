package oldschoolproject.databases;

import oldschoolproject.users.UserStats;
import oldschoolproject.users.User;

public interface DatabaseConnection {

    public void connect();

    public void loadUser(User user);

    public void saveUser(User user);

    public void updateUser(User user, UserStats userStats, Object value);

//    public Object getUserData(User user, DataType dataType);

    public User findUserByName(String name);

}
