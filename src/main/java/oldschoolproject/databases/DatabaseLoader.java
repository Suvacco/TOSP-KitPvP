package oldschoolproject.databases;

import oldschoolproject.databases.connections.MongoConnection;
import oldschoolproject.databases.connections.SQLConnection;
import oldschoolproject.utils.builders.FileBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class DatabaseLoader {

    private static DatabaseConnection databaseConnection;

    public DatabaseLoader() {
        Logger mongoDbLogger = LogManager.getLogger("org.mongodb.driver");
		Configurator.setLevel(mongoDbLogger.getName(), Level.OFF);

        loadDatabase();
    }

    public void loadDatabase() {
        FileBuilder fileBuilder = new FileBuilder("database.yml");

        String dbOption = ((String) fileBuilder.get("DB_OPTION")).toLowerCase();

        switch (dbOption) {
            case "mongodb":
                databaseConnection = new MongoConnection((String)fileBuilder.get("MONGO_URI"), (String)fileBuilder.get("MONGO_DATABASE"));
                break;
            case "sql":
                databaseConnection = new SQLConnection((String)fileBuilder.get("SQL_URL"), (String)fileBuilder.get("SQL_USERNAME"), (String)fileBuilder.get("SQL_PASSWORD"));
                break;
        }

        databaseConnection.connect();
    }

    public static DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }
}
