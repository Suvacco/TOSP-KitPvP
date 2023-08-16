package oldschoolproject.databases.connections;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import oldschoolproject.users.UserStats;
import oldschoolproject.databases.DatabaseConnection;
import oldschoolproject.users.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class MongoConnection implements DatabaseConnection {

    String uri, database;
    MongoCollection<Document> userCollection;

    public MongoConnection(String uri, String database) {
        this.uri = uri;
        this.database = database;
    }

    @Override
    public void connect() {
        Logger mongoDbLogger = LogManager.getLogger("org.mongodb.driver");
        Configurator.setLevel(mongoDbLogger.getName(), Level.OFF);

        try {
            MongoClient mongoClient = MongoClients.create(this.uri);
            MongoDatabase mongoDatabase = mongoClient.getDatabase(this.database);
            this.userCollection = mongoDatabase.getCollection("users");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadUser(User user) {
        Document playerDocument = new Document("_id", user.getUuid().toString());

        Document foundPlayer = this.userCollection.find(playerDocument).first();

        if (foundPlayer != null) {
            user.loadDatabaseDataIntoUser(foundPlayer);
        }
    }

    @Override
    public void saveUser(User user) {
        String userId = user.getUuid().toString();

        Set<String> userPermissions = user.getPermissionAttachment().getPermissions().keySet().stream()
                .filter(permission -> !permission.startsWith("rank."))
                .collect(Collectors.toSet());

        Document playerDocument = new Document("_id", userId)
                .append("name", user.getPlayer().getName())
                .append("rank", user.getUserRank())
                .append("permissions", userPermissions);

        for (Map.Entry<UserStats, Object> entry : user.getStatsMap().entrySet()) {
            playerDocument.append(entry.getKey().name().toLowerCase(), entry.getValue());
        }

        this.userCollection.updateOne(
                new Document("_id", userId),
                new Document("$set", playerDocument),
                new UpdateOptions().upsert(true));
    }

    @Override
    public void updateUser(User user, UserStats userStats, Object value) {
        this.userCollection.updateOne(
                new Document("_id", user.getUuid().toString()),
                new Document("$set", new Document(userStats.name().toLowerCase(), value)));
    }

    @Override
    public User findUserByName(String name) {
        Document document = this.userCollection.find(new Document("name", name)).first();

        if (document == null) {
            return null;
        }

        User user = new User(UUID.fromString((String)document.get("_id")), (String)document.get("name"));

        user.loadDatabaseDataIntoUser(document);

        return user;
    }
}
