package se.complexjava.apigateway.repository;

import se.complexjava.apigateway.auth.Jwt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtTokenRepository extends MongoRepository<Jwt,String> {
}
