package in.rkumar.gateway.services;

import java.security.Key;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // MUST MATCH THE SECRET IN AUTH-SERVICE
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    public String extractUsername(String token) {
    	return Jwts.parserBuilder()
    			.setSigningKey(getSignKey())
    			.build()
    			.parseClaimsJws(token)
    			.getBody()
    			.getSubject();
    }
    
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }
    
}