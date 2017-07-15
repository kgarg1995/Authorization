package com.karan;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.karan.config.CustomAuthConfiguration;
import com.karan.config.PropertiesConfiguration;
import com.karan.resources.UserResource;
import com.karan.security.CustomAuthFilter;
import com.meltmedia.dropwizard.mongo.MongoBundle;
import com.mongodb.MongoClient;
import com.mongodb.ReadPreference;

import dao.UserDao;
import entities.User;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/*
 *
*/
public class CustomAuth extends Application<CustomAuthConfiguration> {

    private MongoBundle<CustomAuthConfiguration> mongoBundle;

    @Override
    public String getName() {
        return "CustomAuth";
    }

    @Override
    public void initialize(Bootstrap<CustomAuthConfiguration> bootstrap) {

        bootstrap.addBundle(
            mongoBundle = MongoBundle
                .<CustomAuthConfiguration> builder()
                .withConfiguration(CustomAuthConfiguration::getMongo)
                .build());

    }


    @Override
    public void run(CustomAuthConfiguration configuration, Environment environment) throws Exception {
        UserDao userDao = new UserDao(getStore());
        setupAuth(environment, userDao);
        environment.jersey().register(new UserResource(userDao));
    }

    private void setupAuth(Environment environment, UserDao userDao) {
        CustomAuthFilter filter = new CustomAuthFilter(userDao);
        environment.jersey().register(new AuthDynamicFeature(filter));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
    }

    private Datastore getStore(){
        final Morphia morphia = new Morphia();
        morphia.map(User.class);

        MongoClient client = mongoBundle.getClient();
        mongoBundle.getClient().setReadPreference(ReadPreference.nearest());
        Datastore store = morphia.createDatastore(client, PropertiesConfiguration.getInstance().getMongoDatabase());
        store.ensureIndexes();

        return store;
    }

    public static void main(String[] args) throws Exception {
        new CustomAuth().run(args);
    }

}
