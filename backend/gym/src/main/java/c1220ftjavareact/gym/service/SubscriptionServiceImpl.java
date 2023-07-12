package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.dto.SubscriptionDTO;
import c1220ftjavareact.gym.domain.mapper.SubscriptionMapper;
import c1220ftjavareact.gym.domain.mapper.UserMapperBeans;
import c1220ftjavareact.gym.repository.SubscriptionRepository;
import c1220ftjavareact.gym.repository.entity.SubscriptionEntity;
import c1220ftjavareact.gym.repository.entity.TrainingSession;
import c1220ftjavareact.gym.repository.entity.UserEntity;
import c1220ftjavareact.gym.service.interfaces.ITrainingSessionService;
import c1220ftjavareact.gym.service.interfaces.SubscriptionService;
import c1220ftjavareact.gym.service.interfaces.UserService;
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
    public SubscriptionDTO updateSubscription(Long id, SubscriptionDTO subscriptionDTO) {
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
    public Boolean deleteSubscription(Long id) {
        try {
            subscriptionRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public SubscriptionDTO getSubscriptionById(Long id) {
        SubscriptionEntity subscription = subscriptionRepository.findById(id).orElse(null);
        if (subscription != null) {
            return subscriptionMapper.convertToDto(subscription);
        }
        return null;
    }

    //cuenta todos los traingin session por id que le pasas
    @Override
    public Integer getCountTrainingSession(Long id) {
        return  subscriptionRepository.countByTrainingSessionId(id);
    }
}
