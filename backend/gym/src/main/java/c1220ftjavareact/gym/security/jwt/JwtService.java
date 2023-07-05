package c1220ftjavareact.gym.security.jwt;

import java.util.Date;
import java.util.Map;

public interface JwtService<T> {

    String generateToken(T userDetails);

    String generateToken(T userDetails, Map<String, Object> extraClaims);

    <S> S extractClaim(String token, String name, Class<S> type);

    Boolean hasClaim(String token, String name);

    String extractAuthority(String token);

    String extractSubject(String token);

    Date extractExpired(String token);

    Boolean isTokenValid(String token, T userDetails);

    Boolean isTokenExpired(String token);

}
