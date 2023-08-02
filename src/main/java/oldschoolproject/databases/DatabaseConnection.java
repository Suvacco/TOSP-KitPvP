package oldschoolproject.databases;

import oldschoolproject.users.User;

public interface DatabaseConnection {

    public void connect();

    public void loadUser(User user);

    public void saveUser(User user);

    public void updateUser(User user, DataType dataType, Object value);

//    public Object getUserData(User user, DataType dataType);

    public User findUserByName(String name);

}
