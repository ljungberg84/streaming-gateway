package se.complexjava.apigateway.repository;

import se.complexjava.apigateway.auth.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,Long> {
    @Query(value="{'email' : ?0}")
    User findByEmail(String email);
}
