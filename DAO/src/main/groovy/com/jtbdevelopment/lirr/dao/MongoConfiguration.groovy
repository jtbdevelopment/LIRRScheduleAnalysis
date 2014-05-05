package com.jtbdevelopment.LIRR.dao

import com.jtbdevelopment.LIRR.dao.converters.AnalysisReader
import com.jtbdevelopment.LIRR.dao.converters.AnalysisWriter
import com.jtbdevelopment.LIRR.dao.converters.TrainScheduleReader
import com.jtbdevelopment.LIRR.dao.converters.TrainScheduleWriter
import com.mongodb.Mongo
import com.mongodb.WriteConcern
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.authentication.UserCredentials
import org.springframework.data.mongodb.config.AbstractMongoConfiguration
import org.springframework.data.mongodb.core.convert.CustomConversions
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

/**
 * Date: 2/23/14
 * Time: 6:17 PM
 */
@Configuration
@EnableMongoRepositories("com.jtbdevelopment")
class MongoConfiguration extends AbstractMongoConfiguration {
    @Value('${mongo.dbName:lirr}')
    String dbName;
    @Value('${mongo.host:localhost}')
    String dbHost;
    @Value('${mongo.userName:lirr}')
    String dbUser;
    @Value('${mongo.userPassword:lirr}')
    String dbPassword;

    @Override
    protected String getDatabaseName() {
        return dbName
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.jtbdevelopment"
    }

    @Override
    protected UserCredentials getUserCredentials() {
        return new UserCredentials(dbUser, dbPassword)
    }

    @Override
    CustomConversions customConversions() {
        return new CustomConversions([
                new TrainScheduleReader(), new TrainScheduleWriter(),
                new AnalysisReader(), new AnalysisWriter(),
        ])
    }

    @Override
    Mongo mongo() throws Exception {
        Mongo mongo = new Mongo(dbHost)
        mongo.setWriteConcern(WriteConcern.JOURNALED)
        return mongo
    }
}
