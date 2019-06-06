package nl.hu.bep.friendspammer;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MongoSaver {
    private static final Logger logger = LoggerFactory.getLogger(MongoSaver.class);

    private MongoSaver() {}

    public static boolean saveEmail(String to, String from, String subject, String text, Boolean html) {
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

    public static List<Map<String, String>> getAllEmails() {
        String database = Config.getProp("mongo.database");
        MongoConnection mongoConnection = new MongoConnection();

        ArrayList<Map<String, String>> result = new ArrayList<>();

        try (MongoClient mongoClient = mongoConnection.getClient()) {

            MongoDatabase db = mongoClient.getDatabase(database);

            MongoCollection<Document> c = db.getCollection("email");


            for (Document email : c.find()) {
                HashMap<String, String> item = new HashMap<>();

                item.put("to", email.get("to").toString());
                item.put("from", email.get("from").toString());
                item.put("subject", email.get("subject").toString());
                item.put("text", email.get("text").toString());
                item.put("asHtml", email.get("asHtml").toString());

                result.add(item);
            }

        } catch (MongoException mongoException) {
            logger.error("Mongo Exception", mongoException);
        }
        return result;
    }

}
