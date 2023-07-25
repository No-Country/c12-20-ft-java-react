package c1220ftjavareact.gym.subscription.service;

import c1220ftjavareact.gym.subscription.dto.SubscribedSession;
import c1220ftjavareact.gym.subscription.dto.SubscriptionDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionInfoDTO;
import c1220ftjavareact.gym.subscription.entity.SubscriptionEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface SubscriptionService {
    SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO);

    SubscriptionDTO updateSubscription(Long id, SubscriptionDTO subscriptionDTO);

    Boolean deleteSubscription(Long id);

    SubscriptionDTO getSubscriptionDtoById(Long id);

    Integer getCountTrainingSession(Long id);

    SubscriptionEntity getSubscriptionById(Long id);


    @Transactional(readOnly = true)
    Set<SubscriptionInfoDTO> findAllSubscription();

    @Transactional(readOnly = true)
    Set<SubscribedSession> findSubscribedSession(Long id);

}