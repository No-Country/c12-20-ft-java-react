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
    private final UserMapperBeans userMapper;
    private final UserService userService;
    private final ITrainingSessionService trainingSessionService;
    private final SubscriptionMapper<SubscriptionDTO, SubscriptionEntity> subscriptionSaveToUserEntityMapper;
    private final SubscriptionMapper<SubscriptionEntity,SubscriptionDTO> subscriptionSaveToUserDtoMapper;


    @Override
    public SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO) {

        SubscriptionEntity subscription = subscriptionSaveToUserEntityMapper.map(subscriptionDTO);

        SubscriptionEntity savedSubscription = subscriptionRepository.save(subscription);
        return subscriptionSaveToUserDtoMapper.map(savedSubscription);
    }


    @Override
    public SubscriptionDTO updateSubscription(Long id, SubscriptionDTO subscriptionDTO) {
        SubscriptionEntity subscription = subscriptionRepository.findById(id).orElse(null);
        UserEntity user = userMapper.userToUserEntity().map(userService.findUserById(subscriptionDTO.customerId().toString()));
        TrainingSession trainingSession = trainingSessionService.getTrainingEntity(subscriptionDTO.idTrainingSession());


        if (subscription != null) {
            subscription.setIdCustomer(user);
            subscription.setTraining(trainingSession);
            subscription.setState(subscriptionDTO.state());
            subscription.setSubscriptionDay(subscriptionDTO.subscriptionDay());
            SubscriptionEntity updatedSubscription = subscriptionRepository.save(subscription);
            return subscriptionSaveToUserDtoMapper.map(updatedSubscription);
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
            return subscriptionSaveToUserDtoMapper.map(subscription);
        }
        return null;
    }

    //cuenta todos los traingin session por id que le pasas
    @Override
    public Integer getCountTrainingSession(Long id) {
        return  subscriptionRepository.countByTrainingSessionId(id);
    }
}
