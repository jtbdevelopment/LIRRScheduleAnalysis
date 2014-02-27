package com.jtbdevelopment.lirr.dao

import com.jtbdevelopment.lirr.dao.converters.AnalysisReader
import com.jtbdevelopment.lirr.dao.converters.AnalysisWriter
import com.jtbdevelopment.lirr.dao.converters.TrainScheduleReader
import com.jtbdevelopment.lirr.dao.converters.TrainScheduleWriter
import com.mongodb.Mongo
import com.mongodb.WriteConcern
import org.springframework.context.annotation.Configuration
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
    @Override
    protected String getDatabaseName() {
        return "lirr"
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.jtbdevelopment"
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
        Mongo mongo = new Mongo()
        mongo.setWriteConcern(WriteConcern.JOURNALED)
        return mongo
    }
}
