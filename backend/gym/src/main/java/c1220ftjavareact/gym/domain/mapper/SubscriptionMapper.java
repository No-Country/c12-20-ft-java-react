package c1220ftjavareact.gym.domain.mapper;

@FunctionalInterface
public interface SubscriptionMapper<T, R> {
    R map(T model);
}
