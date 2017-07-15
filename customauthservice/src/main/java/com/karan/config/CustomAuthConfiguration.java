package com.karan.config;

import com.meltmedia.dropwizard.mongo.MongoConfiguration;

import io.dropwizard.Configuration;

/*
 * @author karangarg on 15/07/17
*/
public class CustomAuthConfiguration extends Configuration {

    public MongoConfiguration getMongo() {
        return PropertiesConfiguration.getInstance().getMongoConfiguration();
    }

}

