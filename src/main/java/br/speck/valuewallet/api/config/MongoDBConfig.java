package br.speck.valuewallet.api.config;

import com.azure.security.keyvault.secrets.SecretClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
@DependsOn("secretClient")
public class MongoDBConfig {

    @Autowired
    private SecretClient secretClient;

    @Autowired
    private Environment env;

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {
        String mongoUri = getSecretValue("APPS-DATABASE-MONGO-URI");
        String databaseName = getSecretValue("VALUEWALLET-DATABASE-MONGO-DATABASE");

        return new SimpleMongoClientDatabaseFactory(mongoUri + "/" + databaseName);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }

    @Bean
    public MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTransactionManager(mongoDatabaseFactory);
    }

    @Bean
    public MongoDatabase mongoDatabase() {
        String mongoUri = getSecretValue("APPS-DATABASE-MONGO-URI");
        String databaseName = getSecretValue("VALUEWALLET-DATABASE-MONGO-DATABASE");
        System.out.println("mongo uri " + mongoUri);
        System.out.println("databaseName " + databaseName);
        return MongoClients.create(mongoUri).getDatabase(databaseName);
    }

    private String getSecretValue(String secretName) {
        try {
            return secretClient.getSecret(secretName).getValue();
        } catch (Exception e) {
            System.err.println("❌ Error whiling retrieving Key Vault secret '" + secretName + "': " + e.getMessage());
            return env.getProperty(secretName, "");
        }
    }

    @PostConstruct
    public void init() {
        System.out.println("🚀 MongoDBConfig FOI CARREGADO PELO SPRING!");
    }
}
