package in.rkumar.authservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key; // ⚠️ CRITICAL: Not java.awt.Key
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
	
	private static final String DEFAULT_ROLE = "USER";
	private static final long DEFAULT_TOKEN_EXPIRATION_TIME_IN_MS = 1000 * 60 * 30; // 30 Mins

    // SECRET KEY: 256-bit Hex Key for HS256 algorithm, In production, store this in application.yml
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    public String generateToken(String userId) {
    	Map<String, Object> claims = new HashMap<>();
    	claims.put("role", DEFAULT_ROLE);
    	return createToken(claims, userId);
    }
    
    private String createToken(Map<String, Object> claims, String userId) {
    	return Jwts.builder()
    			.setClaims(claims)
    			.setSubject(userId) // userId is stored here
    			.setIssuedAt(new Date(System.currentTimeMillis()))
    			.setExpiration(new Date(System.currentTimeMillis() + DEFAULT_TOKEN_EXPIRATION_TIME_IN_MS))
    			.signWith(getSignKey(), SignatureAlgorithm.HS256)
    			.compact();
    }
    
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> (String) claims.get("role"));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}