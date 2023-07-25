package c1220ftjavareact.gym.subscription.service;

import c1220ftjavareact.gym.subscription.dto.SubscriptionDTO;
import c1220ftjavareact.gym.subscription.entity.SubscriptionEntity;

public interface SubscriptionService {
    SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO);

    SubscriptionDTO updateSubscription(Long id, SubscriptionDTO subscriptionDTO);

    Boolean deleteSubscription(Long id);

    SubscriptionDTO getSubscriptionDtoById(Long id);

    Integer getCountTrainingSession(Long id);

    SubscriptionEntity getSubscriptionById(Long id);
}