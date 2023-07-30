package oldschoolproject.databases;

import oldschoolproject.users.User;
import oldschoolproject.users.data.DataType;

public interface DatabaseConnection {

    public void connect();

    public void authUser(User user);

    public void saveUser(User user);

    public void modifyData(User user, DataType dataType, Object value);

    public Object getData(User user, DataType dataType);
}
