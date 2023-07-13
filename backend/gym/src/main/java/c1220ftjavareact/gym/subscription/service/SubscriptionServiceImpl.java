package c1220ftjavareact.gym.subscription.service;

import c1220ftjavareact.gym.subscription.dto.SubscriptionDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionMapper;
import c1220ftjavareact.gym.subscription.entity.SubscriptionEntity;
import c1220ftjavareact.gym.subscription.repository.SubscriptionRepository;
import c1220ftjavareact.gym.user.dto.UserMapperBeans;
import c1220ftjavareact.gym.training.entity.TrainingSession;
import c1220ftjavareact.gym.user.entity.UserEntity;
import c1220ftjavareact.gym.training.service.ITrainingSessionService;
import c1220ftjavareact.gym.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserMapperBeans userMapper;
    private final UserService userService;
    private final ITrainingSessionService trainingSessionService;

    @Override
    public SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO) {
        SubscriptionEntity subscription = subscriptionMapper.convertToEntity(subscriptionDTO);
        SubscriptionEntity savedSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.convertToDto(savedSubscription);
    }

    @Override
    public SubscriptionDTO updateSubscription(int id, SubscriptionDTO subscriptionDTO) {
        SubscriptionEntity subscription = subscriptionRepository.findById(id).orElse(null);
        UserEntity user = userMapper.userToUserEntity().map(userService.findUserById(subscriptionDTO.getIdClient().toString()));
        TrainingSession trainingSession = trainingSessionService.getTrainingEntity(subscriptionDTO.getIdClass());


        if (subscription != null) {
            subscription.setIdCustomer(user);
            subscription.setTraining(trainingSession);
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
