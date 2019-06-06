package nl.hu.bep.friendspammer;

import java.net.UnknownHostException;
import java.util.Arrays;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoSaver {

	private static final Logger logger = LoggerFactory.getLogger(MongoSaver.class);

	public static boolean saveEmail(String to, String from, String subject, String text, Boolean html) {
		String userName = "spammer";
		String password = "hamspam";
		String database = "friendspammer";
		
		MongoCredential credential = MongoCredential.createCredential(userName, database, password.toCharArray());
		
		boolean success = true;
		
		try (MongoClient mongoClient = new MongoClient(new ServerAddress("ds227939.mlab.com", 27939), credential, MongoClientOptions.builder().build()) ) {
			
			MongoDatabase db = mongoClient.getDatabase( database );
			
			MongoCollection<Document> c = db.getCollection("email");
			
			Document  doc = new Document ("to", to)
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
