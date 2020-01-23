package se.complexjava.apigateway.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@ToString
@Document()
public class User {
    @Id
    private long id;
    @Email(message = "Please provide a valid email")
    @NotEmpty(message = "Please provide your email")
    @Indexed(unique=true)
    private String email;
    @NotEmpty(message = "Please provide your password")
    private String password;
    private Integer active=1;
    private boolean isLocked =false;
    private boolean isExpired=false;
    private boolean isEnabled=true;
    private Set<Role> role;


}
