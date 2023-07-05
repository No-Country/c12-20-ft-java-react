package c1220ftjavareact.gym.security.jwt;

import c1220ftjavareact.gym.service.AssertionConcern;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceAdapter extends AssertionConcern implements JwtService<UserDetails> {

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    @Override
    public String generateToken(UserDetails userDetails) {
        return this.generateToken(userDetails, new HashMap<>());
    }

    @Override
    public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer("/api/v1/auth/")
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
                .signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Boolean hasClaim(String token, String name) {
        return this.extractAllClaims(token).get(name) != null;
    }

    @Override
    public String extractAuthority(String token) {
        var authority = (SimpleGrantedAuthority) this.extractClaim(token, "authorities", SimpleGrantedAuthority.class);
        return authority != null ? authority.getAuthority() : "";
    }

    @Override
    public String extractSubject(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpired(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    @Override
    public Boolean isTokenValid(String token, UserDetails userDetails){
        this.assertArgumentNotEmpty(token, "The token is empty, token is invalid");

        final String userEmail = this.extractSubject(token);
        this.assertArgumentNotNull(userDetails, "The user details is null, token is invalid");
        this.assertArgumentNotEmpty(userEmail, "Email in token is empty, token is invalid");
        this.assertArgumentEquals(userEmail, userDetails.getUsername(), "Email does match, token is invalid");
        return !isTokenExpired(token);
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return this.extractExpired(token).before(new Date());
    }

    @Override
    public <S> S extractClaim(String token, String name, Class<S> type) {
        if(!this.hasClaim(token, name)) return null;
        var claims =this.extractAllClaims(token);

        return type.cast(claims.get(name));
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        final Claims claims = this.extractAllClaims(token);
        return resolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        //this.assertArgumentNotEmpty(token, "The token is empty, token is invalid");
        return Jwts
                .parserBuilder()
                .setSigningKey(this.getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        final byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
