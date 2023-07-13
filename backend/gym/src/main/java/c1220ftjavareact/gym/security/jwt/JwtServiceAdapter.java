package c1220ftjavareact.gym.security.jwt;

import c1220ftjavareact.gym.user.entity.UserEntity;
import c1220ftjavareact.gym.util.TimeUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Primary
@Service
@RequiredArgsConstructor
public class JwtServiceAdapter implements JwtService<UserEntity> {
    //Rcupera la clave del archivo Yml
    @Value("${spring.security.jwt.secret}")
    private String SECRET_KEY;

    @Override
    public String generateToken(UserEntity userDetails) {
        return this.generateToken(userDetails, new HashMap<>());
    }

    @Override
    public String generateToken(UserEntity userDetails, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setId(UUID.nameUUIDFromBytes(userDetails.getEmail().getBytes()).toString() + "-" + userDetails.getId().toString())
                .claim("id", userDetails.getId().toString())
                .claim("authority", userDetails.getAuthorities().stream().findFirst().get().getAuthority())
                .setIssuer("http://localhost:8080/api/v1")
                .setIssuedAt(new Date(TimeUtils.getDateMillis()))
                .setExpiration(new Date(TimeUtils.getDateMillis() + TimeUnit.HOURS.toMillis(24)))
                .signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    @Override
    public Boolean hasClaim(String token, String name) {
        return this.extractAllClaims(token).get(name) != null;
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
    public Boolean isTokenValid(String token, UserEntity userDetails) {
        //El token no debe de estar vacio
        Assert.isTrue(StringUtils.hasText(token), "The token is empty, token is invalid");
        //Recupera el email
        final String userEmail = this.extractSubject(token);

        //Verifica que el usuario no sea nulo
        Assert.notNull(userDetails, "The user details is null, token is invalid");

        //Verifica que el email del token no este vacio
        Assert.isTrue(StringUtils.hasText(userEmail), "The token is empty, token is invalid");

        //Verifica que el email del token y del usuario sean iguales
        Assert.isTrue(userEmail.equals(userDetails.getUsername()), "Email does match, token is invalid");

        final String id = this.extractClaim(token, "id", String.class);
        Assert.isTrue(userDetails.getId().toString().equals(id), "The token ID does not match the user, token is invalid");
        final String role = this.extractClaim(token, "authority", String.class);
        Assert.isTrue(userDetails.getRole().toString().equals(role), "The role of the user does not match the role of the token, token is invalid");
        //Finalmene que el token no haya caducado
        return !isTokenExpired(token);
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return this.extractExpired(token).before(new Date());
    }

    @Override
    public <S> S extractClaim(String token, String name, Class<S> type) {
        if (!this.hasClaim(token, name)) return null;
        var claims = this.extractAllClaims(token);

        return type.cast(claims.get(name));
    }

    /**
     * Recupera una claim que este por default en el token
     *
     * @param token    Token JWT
     * @param resolver Funcion con el tipo de dato y el metodo para extraer la claim
     * @param <T>      Cambia segun el tipo de dato de la claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = this.extractAllClaims(token);
        return resolver.apply(claims);
    }

    /**
     * Recupera todas las claims del token
     *
     * @param token Token JWT
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(this.getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Recupera la llave de cifrado del token
     */
    private Key getSignInKey() {
        final byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
