package c1220ftjavareact.gym.user.dto.mapper;

@FunctionalInterface
public interface ForgotPasswordMapper<T, R> {
    R map(T model);
}
