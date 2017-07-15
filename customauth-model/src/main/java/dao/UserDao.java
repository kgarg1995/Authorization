package dao;

import java.util.List;

import org.mongodb.morphia.Datastore;

import entities.Role;
import entities.User;

public class UserDao {

    private Datastore dataStore;

    public UserDao(Datastore store) {
        this.dataStore = store;
    }

    /**
     * add roles a user
     * @param userEmail : to identify user
     * @param roles : roles to be added
     * @return : updated user object
     */
    public User addRoles(String userEmail, List<Role> roles){
        User user = dataStore.find(User.class).filter("userEmail", userEmail).get();
        user.getRole().addAll(roles);
        dataStore.save(user);
        return user;
    }

    /**
     * remove roles from user
     * @param userEmail
     * @param roles
     * @return
     */
    public User removeRoles(String userEmail, List<Role> roles) {
        User user = dataStore.find(User.class).filter("userEmail", userEmail).get();
        user.getRole().removeAll(roles);
        dataStore.save(user);
        return user;
    }

    /**
     * find user by email
     * @param userEmail
     * @return
     */
    public User getUser(String userEmail) {
        return dataStore.find(User.class)
            .filter("userEmail", userEmail)
            .get();
    }
}
