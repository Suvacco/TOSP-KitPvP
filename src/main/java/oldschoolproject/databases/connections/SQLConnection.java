package oldschoolproject.databases.connections;

import oldschoolproject.users.data.DataType;
import oldschoolproject.databases.DatabaseConnection;
import oldschoolproject.users.User;

public class SQLConnection implements DatabaseConnection {


    public SQLConnection(String url, String user, String password) {

    }

    @Override
    public void connect() {

    }

    @Override
    public void authUser(User user) {

    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public void modifyData(User user, DataType dataType, Object value) {

    }

    @Override
    public Object getData(User user, DataType dataType) {
        return null;
    }
}
