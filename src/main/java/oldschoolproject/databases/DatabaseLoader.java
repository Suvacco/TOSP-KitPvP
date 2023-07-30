package oldschoolproject.databases;

import oldschoolproject.Main;
import oldschoolproject.databases.connections.MongoConnection;
import oldschoolproject.databases.connections.SQLConnection;
import oldschoolproject.utils.builders.FileBuilder;

public class DatabaseLoader {

    private static DatabaseConnection databaseConnection;

    public DatabaseLoader() {
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

        Main.getInstance().getLogger().info("[DatabaseLoader] Successfully connected to MongoDB database");
    }

    public static DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }
}
