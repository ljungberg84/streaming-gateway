package se.complexjava.apigateway.rest;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import se.complexjava.apigateway.auth.*;
import se.complexjava.apigateway.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private ILoginService iLoginService;
    @Autowired
    RestTemplate restTemplate;

    @CrossOrigin("*")
    @PostMapping("/signin")
    @ResponseBody
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = iLoginService.login(loginRequest.getUsername(), loginRequest.getPassword());
        HttpHeaders headers = new HttpHeaders();
        List<String> headerlist = new ArrayList<>();
        List<String> exposeList = new ArrayList<>();
        headerlist.add("Content-Type");
        headerlist.add(" Accept");
        headerlist.add("X-Requested-With");
        headerlist.add("Authorization");
        headers.setAccessControlAllowHeaders(headerlist);
        exposeList.add("Authorization");
        headers.setAccessControlExposeHeaders(exposeList);
        headers.set("Authorization", token);
        return new ResponseEntity<AuthResponse>(new AuthResponse(token), headers, HttpStatus.CREATED);
    }

    @CrossOrigin("*")
    @PostMapping("/signout")
    @ResponseBody
    public ResponseEntity<AuthResponse> logout(@RequestHeader(value = "Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        if (iLoginService.logout(token)) {
            headers.remove("Authorization");
            return new ResponseEntity<AuthResponse>(new AuthResponse("logged out"), headers, HttpStatus.CREATED);
        }
        return new ResponseEntity<AuthResponse>(new AuthResponse("Logout Failed"), headers, HttpStatus.NOT_MODIFIED);
    }

    /**
     * @param token
     * @return boolean.
     * if request reach here it means it is a valid token.
     */
    @PostMapping("/valid/token")
    @ResponseBody
    public Boolean isValidToken(@RequestHeader(value = "Authorization") String token) {
        return true;
    }


    @PostMapping("/signin/token")
    @CrossOrigin("*")
    @ResponseBody
    public ResponseEntity<AuthResponse> createNewToken(@RequestHeader(value = "Authorization") String token) {
        String newToken = iLoginService.createNewToken(token);
        HttpHeaders headers = new HttpHeaders();
        List<String> headerList = new ArrayList<>();
        List<String> exposeList = new ArrayList<>();
        headerList.add("Content-Type");
        headerList.add(" Accept");
        headerList.add("X-Requested-With");
        headerList.add("Authorization");
        headers.setAccessControlAllowHeaders(headerList);
        exposeList.add("Authorization");
        headers.setAccessControlExposeHeaders(exposeList);
        headers.set("Authorization", newToken);
        return new ResponseEntity<AuthResponse>(new AuthResponse(newToken), headers, HttpStatus.CREATED);
    }


    @CrossOrigin("*")
    @GetMapping("/test1")
    @ResponseBody
    public String test1() {
        return "Test 1, without token";
    }

    @CrossOrigin("*")
    @GetMapping("/test2")
    @ResponseBody
    public String test2() {
        return "Test 2, with token";
    }


    @CrossOrigin("*")
    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<String> signup(@RequestBody RequestData data) {

       //save to Sql
        SqlUser sqlUser = new SqlUser();
        sqlUser.setEmail(data.getEmail());
        sqlUser.setFirstName(data.getFirstName());
        sqlUser.setLastName(data.getLastName());
        sqlUser.setPersonalId(data.getPersonalId());
        sqlUser.setAvatarImagePath(data.getAvatarImagePath());
        String url = "http://video-data/users/register";
        HttpEntity<SqlUser> request = new HttpEntity<>(sqlUser);
        long id = restTemplate.postForObject(url, request, Long.class);

        //save to MongoDB
        if (id != 0) {
            User mongoUser = new User();
            mongoUser.setId(id);
            mongoUser.setEmail(data.getEmail());
            mongoUser.setPassword(data.getPassword());
            mongoUser.setRole(data.getRole());
            iLoginService.saveUser(mongoUser);
        }

        return new ResponseEntity<String>("User with id '" +id+ "' created ", HttpStatus.CREATED);
    }

}
