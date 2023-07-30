package oldschoolproject.databases.connections;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import oldschoolproject.Main;
import oldschoolproject.users.data.DataType;
import oldschoolproject.databases.DatabaseConnection;
import oldschoolproject.users.User;
import oldschoolproject.users.data.RankType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.bson.Document;

import java.util.Arrays;

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
    public void authUser(User user) {
        Document playerDocument = new Document("_id", user.getPlayer().getUniqueId().toString());

        Document foundPlayer = this.userCollection.find(playerDocument).first();

        // If players exists in database, download his info to the object
        if (foundPlayer != null) {
            user.setRank(RankType.valueOf((String) foundPlayer.get("rank")));

            user.setKills((Integer) foundPlayer.get("kills"));
            user.setDeaths((Integer) foundPlayer.get("deaths"));
            user.setCoins((Integer) foundPlayer.get("coins"));
            user.setDuelsCount((Integer) foundPlayer.get("duels_count"));
            user.setDuelsWins((Integer) foundPlayer.get("duels_wins"));
            user.setDuelsLosses((Integer) foundPlayer.get("duels_losses"));

            Main.getInstance().getLogger().info("[DatabaseLoader] Player " + user.getPlayer().getName() + " registered! UUID: " + user.getPlayer().getUniqueId().toString());
        }
    }

    @Override
    public void saveUser(User user) {
        Document playerDocument = new Document("_id", user.getPlayer().getUniqueId().toString());

        Document foundPlayer = this.userCollection.find(playerDocument).first();

        playerDocument
                .append("rank", user.getRank())
                .append("kills", user.getKills())
                .append("deaths", user.getDeaths())
                .append("coins", user.getCoins())
                .append("duels_count", user.getDuelsCount())
                .append("duels_wins", user.getDuelsWins())
                .append("duels_losses", user.getDuelsLosses());

        if (foundPlayer != null) {
            this.userCollection.updateOne(foundPlayer, new Document("$set", playerDocument));
            return;
        }

        this.userCollection.insertOne(playerDocument);
    }

    @Override
    public void modifyData(User user, DataType dataType, Object value) {
//        Document filter = new Document("uuid", user.getPlayer().getUniqueId().toString());
//
//        Document playerDocument = this.userCollection.find(filter).first();

        this.userCollection.updateOne(
                new Document("_id", user.getPlayer().getUniqueId().toString()),
                new Document("$set", new Document(dataType.name().toLowerCase(), value)));
    }

    @Override
    public Object getData(User user, DataType dataType) {
//        Document playerDocument = new Document("uuid", user.getPlayer().getUniqueId().toString());
//
//        Document foundPlayer = this.userCollection.find(playerDocument).first();
//
//        if (foundPlayer != null) {
//            return foundPlayer.get(dataType.name().toLowerCase());
//        }

        return this.userCollection.find(
                new Document("_id", user.getPlayer().getUniqueId().toString())).first()
                .get(dataType.name().toLowerCase());
    }
}
