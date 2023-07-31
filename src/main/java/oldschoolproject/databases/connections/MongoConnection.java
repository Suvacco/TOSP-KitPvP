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
        Document playerDocument = new Document("_id", user.getPlayer().getUniqueId().toString());

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
        String userId = user.getPlayer().getUniqueId().toString();

        Document playerDocument = new Document("_id", userId)
                .append("rank", user.getRank())
                .append("kills", user.getKills())
                .append("deaths", user.getDeaths())
                .append("coins", user.getCoins())
                .append("duels_count", user.getDuelsCount())
                .append("duels_wins", user.getDuelsWins())
                .append("duels_losses", user.getDuelsLosses());

        Document updateQuery = new Document("$set", playerDocument);

        Document filterQuery = new Document("_id", userId);

        this.userCollection.updateOne(filterQuery, updateQuery, new UpdateOptions().upsert(true));
    }

    @Override
    public void modifyData(User user, DataType userData, Object value) {
//        Document filter = new Document("uuid", user.getPlayer().getUniqueId().toString());
//
//        Document playerDocument = this.userCollection.find(filter).first();

        this.userCollection.updateOne(
                new Document("_id", user.getPlayer().getUniqueId().toString()),
                new Document("$set", new Document(userData.name().toLowerCase(), value)));
    }

    @Override
    public Object getData(User user, DataType userData) {
//        Document playerDocument = new Document("uuid", user.getPlayer().getUniqueId().toString());
//
//        Document foundPlayer = this.userCollection.find(playerDocument).first();
//
//        if (foundPlayer != null) {
//            return foundPlayer.get(dataType.name().toLowerCase());
//        }

        return this.userCollection.find(
                new Document("_id", user.getPlayer().getUniqueId().toString())).first()
                .get(userData.name().toLowerCase());
    }
}
