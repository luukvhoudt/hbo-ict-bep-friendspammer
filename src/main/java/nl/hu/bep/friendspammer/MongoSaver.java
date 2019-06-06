package nl.hu.bep.friendspammer;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MongoSaver {
    private static final Logger logger = LoggerFactory.getLogger(MongoSaver.class);

    private MongoSaver() {}

    static boolean saveEmail(String to, String from, String subject, String text, Boolean html) {
        String userName = Config.getProp("mongo.username");
        String password = Config.getProp("mongo.password");
        String database = Config.getProp("mongo.database");

        MongoCredential credential = MongoCredential.createCredential(userName, database, password.toCharArray());

        boolean success = true;

        try (MongoClient mongoClient = new MongoClient(new ServerAddress("ds227939.mlab.com", 27939), credential, MongoClientOptions.builder().build())) {

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
