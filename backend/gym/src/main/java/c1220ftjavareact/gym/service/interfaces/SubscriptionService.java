package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.domain.dto.SubscriptionDTO;

public interface SubscriptionService {
    SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO);

    SubscriptionDTO updateSubscription(Long id, SubscriptionDTO subscriptionDTO);

    Boolean deleteSubscription(Long id);

    SubscriptionDTO getSubscriptionById(Long id);

    Integer getCountTrainingSession(Long id);
}