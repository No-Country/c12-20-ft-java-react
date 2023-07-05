package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.mapper.SubscriptionMapper;
import c1220ftjavareact.gym.repository.SubscriptionRepository;
import c1220ftjavareact.gym.domain.dto.SubscriptionDTO;
import c1220ftjavareact.gym.repository.entity.SubscriptionEntity;
import c1220ftjavareact.gym.service.interfaces.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;

public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, SubscriptionMapper subscriptionMapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionMapper = subscriptionMapper;
    }

    @Override
    public SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO) {
        SubscriptionEntity subscription = subscriptionMapper.convertToEntity(subscriptionDTO);
        SubscriptionEntity savedSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.convertToDto(savedSubscription);
    }

    @Override
    public SubscriptionDTO updateSubscription(int id, SubscriptionDTO subscriptionDTO) {
        SubscriptionEntity subscription = subscriptionRepository.findById(id).orElse(null);
        if (subscription != null) {
            subscription.setIdClient(subscriptionDTO.getIdClient());
            subscription.setIdClass(subscriptionDTO.getIdClass());
            subscription.setState(subscriptionDTO.getState());
            subscription.setSubscriptionDay(subscriptionDTO.getSubscriptionDay());
            SubscriptionEntity updatedSubscription = subscriptionRepository.save(subscription);
            return subscriptionMapper.convertToDto(updatedSubscription);
        }
        return null;
    }

    @Override
    public Boolean deleteSubscription(int id) {
        try {
            subscriptionRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public SubscriptionDTO getSubscriptionById(int id) {
        SubscriptionEntity subscription = subscriptionRepository.findById(id).orElse(null);
        if (subscription != null) {
            return subscriptionMapper.convertToDto(subscription);
        }
        return null;
    }
}
