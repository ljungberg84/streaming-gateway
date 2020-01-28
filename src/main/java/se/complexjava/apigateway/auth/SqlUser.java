package se.complexjava.apigateway.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SqlUser {

    private String email;
    private String firstName;
    private String lastName;
    private String personalId;
    private String avatarImagePath;
}
