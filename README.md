# Authorization

This project implements Role based access mechanism. It uses dropwizard framework and its authorization plugin. 
@RoleAllowed annotation is used which on every resource to filter access level for particular resource.

Basically it intercept every request for the resources which has annotation @RoleAllowed, get user email from cookie, fetches corresponding user from db and check if role mentioned over resource is allowed for particular user.

Current system is very basic implementation of Role based authorization, there are lot of modification that be done to current system according to use-case.

Assumptions:
1. It uses mongoDB, so it assumes that mongo is installed and running at 27017(default port)
2. DB used is auth and collection name is user: these can be changed by changing their value in config and model file respectively
3. It assumes that user emai is set in cookie.
4. Currently add a role permission is given to a Hypothetical Role "MANAGER" and remove role to "MANAGER" and "TEAM_LEAD"
5. It assumes that every role will have a clearly definined permissions related to actions that a particular Role can perform of resources.
6. For different access like Read, Write, Delete we can allow particular role to perform action at resource level itself.
