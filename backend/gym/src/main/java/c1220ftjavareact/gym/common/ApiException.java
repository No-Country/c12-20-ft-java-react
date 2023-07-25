package c1220ftjavareact.gym.common;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private String title;

    public ApiException(String message, String title) {
        super(message);
        this.title = title;
    }
}
