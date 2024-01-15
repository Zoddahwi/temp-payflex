package com.fintechedge.userservice.service;

import com.fintechedge.userservice.config.Credentials;
import com.fintechedge.userservice.config.KeycloakConfig;
import com.fintechedge.userservice.dto.NewUserDTO;
import org.keycloak.admin.client.resource.UsersResource;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class KeyClockService {

    public void save(NewUserDTO newUser) {

        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(newUser.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(newUser.getUsername());
        user.setFirstName(newUser.getFirstname());
        user.setLastName(newUser.getLastname());
        user.setEmail(newUser.getEmailId());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);

        UsersResource instance = getInstance();
        instance.create(user);
    }

    private static UsersResource getInstance() {
        return KeycloakConfig.getInstance().realm("payflex-realm").users();

    }

    public List<UserRepresentation> getUser(String userName){
        UsersResource usersResource = getInstance();
        List<UserRepresentation> user = usersResource.search(userName, true);
        return user;
    }

//    public Flux<UserRepresentation> getUser(String userName){
//        UsersResource usersResource = getInstance();
//        List<UserRepresentation> user = usersResource.search(userName, true);
//        return (Flux<UserRepresentation>) user;
//    }

    public void updateUser(String userId, NewUserDTO newUser){
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(newUser.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(newUser.getUsername());
        user.setFirstName(newUser.getFirstname());
        user.setLastName(newUser.getLastname());
        user.setEmail(newUser.getEmailId());
        user.setCredentials(Collections.singletonList(credential));

        UsersResource usersResource = getInstance();
        usersResource.get(userId).update(user);
    }

    public void deleteUser(String userId){
        UsersResource usersResource = getInstance();
        usersResource.get(userId)
                .remove();
    }

    public void sendVerificationLink(String userId){
        UsersResource usersResource = getInstance();
        usersResource.get(userId)
                .sendVerifyEmail();
    }

    public void sendResetPassword(String userId){
        UsersResource usersResource = getInstance();

        usersResource.get(userId)
                .executeActionsEmail(Collections.singletonList("UPDATE_PASSWORD"));
    }



//    Flux<UserRepresentation> getUser(String userName){
//        UsersResource usersResource = getInstance();
//        List<UserRepresentation> user = usersResource.search(userName, true);
//        return (Flux<UserRepresentation>) user;
//    }

}
