package c1220ftjavareact.gym.subscription.service;

import c1220ftjavareact.gym.subscription.enums.State;
import c1220ftjavareact.gym.subscription.other.SubscribedSessionDTO;
import c1220ftjavareact.gym.subscription.other.SubscriptionInfoDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionSaveDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionUpdateDTO;
import c1220ftjavareact.gym.subscription.entity.Subscription;

import java.util.Set;

public interface ISubscriptionService {

    void saveSubscription(SubscriptionSaveDTO subscriptionSaveDTO);

    void updateSubscription(SubscriptionUpdateDTO subscriptionUpdateDTO);

    void updateSubscription(Long subscriptionId, State updateState);

    Subscription getSubscriptionById(Long id);

    /// MARCOS
    Set<SubscriptionInfoDTO> findAllSubscription();

    /// MARCOS
    Set<SubscribedSessionDTO> findSubscribedSession(Long id);

}