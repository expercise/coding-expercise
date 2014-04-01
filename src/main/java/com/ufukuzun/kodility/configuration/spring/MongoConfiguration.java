package com.ufukuzun.kodility.configuration.spring;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("com.ufukuzun.kodility.dao")
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Value("${mongo.host}")
    private String host;

    @Value("${mongo.port}")
    private int port;

    @Value("${mongo.user}")
    private String user;

    @Value("${mongo.password}")
    private String password;

    @Override
    protected String getDatabaseName() {
        return "kodility";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(host, port);
    }

    @Override
    protected UserCredentials getUserCredentials() {
        return new UserCredentials(user, password);
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.ufukuzun.kodility.domain";
    }

}
