package c1220ftjavareact.gym.domain.mapper;

@FunctionalInterface
public interface UserMapper<T, R> {
    R map(T model);
}
