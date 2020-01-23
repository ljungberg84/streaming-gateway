package se.complexjava.apigateway.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
public class RequestData {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String personalId;
    private String avatarImagePath;
    private Set<Role> role;


}
