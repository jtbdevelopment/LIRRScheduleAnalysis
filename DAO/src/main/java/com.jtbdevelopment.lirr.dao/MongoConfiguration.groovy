package com.jtbdevelopment.lirr.dao

import com.mongodb.Mongo
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

/**
 * Date: 2/23/14
 * Time: 6:17 PM
 */
@Configuration
@EnableMongoRepositories("com.jtbdevelopment")
class MongoConfiguration extends AbstractMongoConfiguration {
    @Override
    protected String getDatabaseName() {
        return "lirr"
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.jtbdevelopment"
    }

    @Override
    Mongo mongo() throws Exception {
        return new Mongo()
    }
}
