package c1220ftjavareact.gym.domain.mapper;

@FunctionalInterface
public interface ForgotPasswordMapper<T, R> {
    R map(T model);
}
