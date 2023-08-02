package oldschoolproject.databases.connections;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import oldschoolproject.databases.DataType;
import oldschoolproject.databases.DatabaseConnection;
import oldschoolproject.users.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.bson.Document;

import java.util.UUID;

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
            user.setRank((String) foundPlayer.get("rank"));
            user.setKills((Integer) foundPlayer.get("kills"));
            user.setDeaths((Integer) foundPlayer.get("deaths"));
            user.setCoins((Integer) foundPlayer.get("coins"));
            user.setDuelsCount((Integer) foundPlayer.get("duels_count"));
            user.setDuelsWins((Integer) foundPlayer.get("duels_wins"));
            user.setDuelsLosses((Integer) foundPlayer.get("duels_losses"));
        }
    }

    @Override
    public void saveUser(User user) {
        String userId = user.getUuid().toString();

        Document playerDocument = new Document("_id", userId)
                .append("name", user.getPlayer().getName())
                .append("rank", user.getRank())
                .append("kills", user.getKills())
                .append("deaths", user.getDeaths())
                .append("coins", user.getCoins())
                .append("duels_count", user.getDuelsCount())
                .append("duels_wins", user.getDuelsWins())
                .append("duels_losses", user.getDuelsLosses());

        this.userCollection.updateOne(
                new Document("_id", userId),
                new Document("$set", playerDocument),
                new UpdateOptions().upsert(true));
    }

    @Override
    public void updateUser(User user, DataType dataType, Object value) {
        this.userCollection.updateOne(
                new Document("_id", user.getUuid().toString()),
                new Document("$set", new Document(dataType.name().toLowerCase(), value)));
    }

    @Override
    public User findUserByName(String name) {
        Document document = this.userCollection.find(new Document("name", name)).first();
        return document == null ? null : new User(UUID.fromString((String)document.get("_id")), (String)document.get("name"));
    }
}
