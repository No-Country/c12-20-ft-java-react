package c1220ftjavareact.gym.subscription.service;

import c1220ftjavareact.gym.subscription.dto.SubscriptionDTO;

public interface SubscriptionService {
    SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO);

    SubscriptionDTO updateSubscription(Long id, SubscriptionDTO subscriptionDTO);

    Boolean deleteSubscription(Long id);

    SubscriptionDTO getSubscriptionById(Long id);

    Integer getCountTrainingSession(Long id);
}