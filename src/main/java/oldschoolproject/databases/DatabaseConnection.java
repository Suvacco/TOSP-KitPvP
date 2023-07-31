package oldschoolproject.databases;

import oldschoolproject.users.User;

public interface DatabaseConnection {

    public void connect();

    public void loadUser(User user);

    public void saveUser(User user);

    public void modifyData(User user, DataType userData, Object value);

    public Object getData(User user, DataType userData);
}
