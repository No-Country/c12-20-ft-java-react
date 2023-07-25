package c1220ftjavareact.gym.subscription.service;

import c1220ftjavareact.gym.subscription.dto.SubscribedSessionDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionInfoDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionSaveDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionUpdateDTO;
import c1220ftjavareact.gym.subscription.entity.Subscription;

import java.util.Set;

public interface ISubscriptionService {

    void saveSubscription(SubscriptionSaveDTO subscriptionSaveDTO);

    void updateSubscription(SubscriptionUpdateDTO subscriptionUpdateDTO);

    Subscription getSubscriptionById(Long id);

    /// MARCOS
    Set<SubscriptionInfoDTO> findAllSubscription();

    /// MARCOS
    Set<SubscribedSessionDTO> findSubscribedSession(Long id);

}