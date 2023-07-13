package c1220ftjavareact.gym.password.dto;

@FunctionalInterface
public interface ForgotPasswordMapper<T, R> {
    R map(T model);
}
