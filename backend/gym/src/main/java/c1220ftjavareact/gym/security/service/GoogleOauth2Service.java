package c1220ftjavareact.gym.security.service;

import c1220ftjavareact.gym.security.dto.UserGoogleDTO;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class GoogleOauth2Service {
    @Value("spring.security.oauth2.client.google.clientId")
    private String googleClientId;

    @Value("spring.security.oauth2.client.google.clientSecret")
    private String googleSecretId;

    private final GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
    private final NetHttpTransport transport = new NetHttpTransport();

    public String extractEmail(String token) {
        return this.extractPayload(token).getEmail();
    }

    public Boolean extractVerified(String token) {
        return this.extractPayload(token).getEmailVerified();
    }

    public UserGoogleDTO extractUser(String token) {
        var payload = this.extractPayload(token);

        return UserGoogleDTO.builder()
                .email(payload.getEmail())
                .name((String) payload.get("given_name"))
                .lastName((String) payload.get("family_name"))
                .picture((String) payload.get("picture"))
                .build();
    }

    public Boolean isExpired(String token) {
        return (System.currentTimeMillis() / 1000) >= this.extractPayload(token).getExpirationTimeSeconds();
    }

    public GoogleIdToken.Payload extractPayload(String token) {
        try {
            return GoogleIdToken.parse(this.getTokenVerifier().getJsonFactory(), token).getPayload();
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public GoogleIdTokenVerifier getTokenVerifier() {
        return new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(googleClientId))
                .build();
    }

    public Boolean isValidToken(String token) {
        Assert.hasText(token, "Token is empty, token invalid");
        Assert.isTrue(this.extractVerified(token), "Email in Google Account is not verified, token is invalid");
        var email = this.extractEmail(token);
        Assert.hasText(email, "Email in token is empty, token invalid");
        //Assert.isTrue(!this.isExpired(token), "Token already expired, token invalid");
        return true;
    }
}
