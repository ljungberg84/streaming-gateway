package se.complexjava.apigateway.auth;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Jwt {
    @Id
    private String token;

    public Jwt(String token) {
        this.token = token;
    }

    public Jwt() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
