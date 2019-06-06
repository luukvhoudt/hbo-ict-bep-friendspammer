package nl.hu.bep.friendspammer;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

class MongoConnection {
    private MongoCredential credential;
    private ServerAddress address;

    MongoConnection() {
        String userName = Config.getProp("mongo.username");
        String password = Config.getProp("mongo.password");
        String database = Config.getProp("mongo.database");
        String hostname = Config.getProp("mongo.hostname");
        int port = Integer.parseInt(Config.getProp("mongo.port"));

        credential = MongoCredential.createCredential(userName, database, password.toCharArray());
        address = new ServerAddress(hostname, port);
    }

    MongoClient getClient() {
         return new MongoClient(address, credential, MongoClientOptions.builder().build());
    }
}
