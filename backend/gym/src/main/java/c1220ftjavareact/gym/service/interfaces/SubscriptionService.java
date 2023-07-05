package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.domain.dto.SubscriptionDTO;
import org.springframework.stereotype.Service;

@Service
public interface SubscriptionService {
    SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO);
    SubscriptionDTO updateSubscription(int id, SubscriptionDTO subscriptionDTO);
    Boolean deleteSubscription(int id);
    SubscriptionDTO getSubscriptionById(int id);
}