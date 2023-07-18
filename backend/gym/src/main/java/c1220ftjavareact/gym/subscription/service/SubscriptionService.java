package c1220ftjavareact.gym.subscription.service;

import c1220ftjavareact.gym.subscription.dto.SubscriptionDTO;

public interface SubscriptionService {
    SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO);

    SubscriptionDTO updateSubscription(int id, SubscriptionDTO subscriptionDTO);

    Boolean deleteSubscription(int id);

    SubscriptionDTO getSubscriptionById(int id);
}