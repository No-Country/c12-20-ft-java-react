package c1220ftjavareact.gym.subscription.service;

import c1220ftjavareact.gym.subscription.dto.SubscribedSessionDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionInfoDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionSaveDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionUpdateDTO;
import c1220ftjavareact.gym.subscription.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class ImplSubscriptionService implements ISubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public ImplSubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Object saveSubscription(SubscriptionSaveDTO subscriptionSaveDTO) {
        return null;
    }

    @Override
    public void updateSubscription(SubscriptionUpdateDTO subscriptionUpdateDTO) {

    }

    ///MARCOS
    @Override
    public Set<SubscriptionInfoDTO> findAllSubscription() {
        return this.subscriptionRepository.listSubscriptions();
    }

    ///MARCOS
    @Transactional(readOnly = true)
    @Override
    public Set<SubscribedSessionDTO> findSubscribedSession(Long id) {
        return this.subscriptionRepository.findUserSubscriptions(id);
    }
}
