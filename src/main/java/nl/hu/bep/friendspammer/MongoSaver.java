package nl.hu.bep.friendspammer;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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

    public static List<EmailDTO> getAllEmails() {
        String database = Config.getProp("mongo.database");
        MongoConnection mongoConnection = new MongoConnection();

        ArrayList<EmailDTO> result = new ArrayList<>();

        try (MongoClient mongoClient = mongoConnection.getClient()) {

            MongoDatabase db = mongoClient.getDatabase(database);

            MongoCollection<Document> c = db.getCollection("email");


            for (Document email : c.find()) {
                result.add(new EmailDTO(
                        email.get("to").toString(),
                        email.get("from").toString(),
                        email.get("subject").toString(),
                        email.get("text").toString(),
                        Boolean.parseBoolean(email.get("asHtml").toString())
                ));
            }

        } catch (MongoException mongoException) {
            logger.error("Mongo Exception", mongoException);
        }
        return result;
    }

}
