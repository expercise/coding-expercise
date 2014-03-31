package com.ufukuzun.kodility.configuration.spring;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("com.ufukuzun.kodility.dao")
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "kodility";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient("127.3.40.130", 27017);
    }

    @Override
    protected UserCredentials getUserCredentials() {
        return new UserCredentials("admin", "ldjjkipDBDEF");
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.ufukuzun.kodility.domain";
    }

}
