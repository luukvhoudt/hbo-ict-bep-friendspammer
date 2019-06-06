package nl.hu.bep.friendspammer;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MongoSaver {
    private static final Logger logger = LoggerFactory.getLogger(MongoSaver.class);

    private MongoSaver() {}

    static boolean saveEmail(String to, String from, String subject, String text, Boolean html) {
        String database = Config.getProp("mongo.database");
        MongoConnection mongoConnection = new MongoConnection();

        boolean success = true;
        try (MongoClient mongoClient = mongoConnection.getClient()) {

            MongoDatabase db = mongoClient.getDatabase(database);

            MongoCollection<Document> c = db.getCollection("email");

            Document doc = new Document("to", to)
                    .append("from", from)
                    .append("subject", subject)
                    .append("text", text)
                    .append("asHtml", html);
            c.insertOne(doc);
        } catch (MongoException mongoException) {
            logger.info("XXXXXXXXXXXXXXXXXX ERROR WHILE SAVING TO MONGO XXXXXXXXXXXXXXXXXXXXXXXXXX");
            logger.error("Mongo Exception", mongoException);
            success = false;
        }

        return success;

    }

}
