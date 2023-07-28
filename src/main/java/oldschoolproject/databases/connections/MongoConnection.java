package oldschoolproject.databases.connections;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import oldschoolproject.Main;
import oldschoolproject.databases.DatabaseConnection;
import org.bson.Document;
import org.bukkit.entity.Player;

public class MongoConnection implements DatabaseConnection {

    String uri, database;

    public MongoConnection(String uri, String database) {
        this.uri = uri;
        this.database = database;
    }

    @Override
    public void connect() {
        MongoClient mongoClient = MongoClients.create(this.uri);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(this.database);
        MongoCollection<Document> defaultCollection = mongoDatabase.getCollection("users");

        Main.getInstance().getLogger().info("[DatabaseLoader] Connected to the MongoDB database");
    }

    @Override
    public void registerPlayer(Player player) {

    }
}
