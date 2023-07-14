package c1220ftjavareact.gym.subscription.dto;

@FunctionalInterface
public interface SubscriptionMapper<T, R> {
    R map(T model);
}
