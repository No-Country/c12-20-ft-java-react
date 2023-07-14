package c1220ftjavareact.gym.user.dto.mapper;

@FunctionalInterface
public interface UserMapper<T, R> {
    R map(T model);
}
