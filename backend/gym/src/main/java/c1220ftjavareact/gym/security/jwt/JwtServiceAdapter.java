package c1220ftjavareact.gym.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceAdapter implements JwtService<UserDetails> {
    //Rcupera la clave del archivo Yml
    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    /**
     * Crea un token solo con el usuario
     *
     * @param userDetails Datos del usuario que crea el token
     * @return Token JWT
     */
    @Override
    public String generateToken(UserDetails userDetails) {
        return this.generateToken(userDetails, new HashMap<>());
    }

    /**
     * Crea un token que contenga mas detalles
     *
     * @param userDetails Datos del usuario que crea el token
     * @param extraClaims Detalles extra para el token
     * @return Token JWT
     */
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

    /**
     * Verifica si la claim que deseas se encuentra en el token
     *
     * @param token Token JWT
     * @param name  Nombre de la claim
     */
    @Override
    public Boolean hasClaim(String token, String name) {
        return this.extractAllClaims(token).get(name) != null;
    }

    /**
     * Recupera el rol del usuario que genero el token
     *
     * @param token Token JWT
     */
    @Override
    public String extractAuthority(String token) {
        var authority = (SimpleGrantedAuthority) this.extractClaim(token, "authorities", SimpleGrantedAuthority.class);
        return authority != null ? authority.getAuthority() : "";
    }

    /**
     * Recupera la credencial principal del usuario que creo el token
     * (En nuestro caso Email)
     *
     * @param token Token JWT
     */
    @Override
    public String extractSubject(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    /**
     * Recupera la fecha de caducidad del token
     *
     * @param token Token JWT
     */
    @Override
    public Date extractExpired(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    /**
     * Verifica que el token sea valido
     *
     * @param token       Token JWT
     * @param userDetails Usuario que pertenece el token
     * @return
     */
    @Override
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        Assert.isTrue(StringUtils.hasText(token), "The token is empty, token is invalid");
        final String userEmail = this.extractSubject(token);

        Assert.notNull(userDetails, "The user details is null, token is invalid");
        Assert.isTrue(userEmail.equals(userDetails.getUsername()), "Email does match, token is invalid");
        Assert.isTrue(StringUtils.hasText(userEmail), "The token is empty, token is invalid");
        return !isTokenExpired(token);
    }

    /**
     * Verifica si el token ha caducado
     *
     * @param token Token JWT
     */
    @Override
    public Boolean isTokenExpired(String token) {
        return this.extractExpired(token).before(new Date());
    }

    /**
     * Recupera una claim especifica del token
     *
     * @param token Token JWT
     * @param name  Nombre de la claim que desea recuperar
     * @param type  Tipo de dato de la claim
     * @param <S>   Cambia segun el tipo de dato de la claim
     */
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
