package c1220ftjavareact.gym.domain.mapper;

@FunctionalInterface
public interface UpdatePasswordMapper<T, R> {
    R map(T model);
}
