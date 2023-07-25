package c1220ftjavareact.gym.subscription.service;

import c1220ftjavareact.gym.subscription.dto.SubscribedSession;
import c1220ftjavareact.gym.subscription.dto.SubscriptionDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionInfoDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionMapper;
import c1220ftjavareact.gym.subscription.entity.SubscriptionEntity;
import c1220ftjavareact.gym.subscription.repository.SubscriptionRepository;
import c1220ftjavareact.gym.user.dto.mapper.UserMapperBeans;
import c1220ftjavareact.gym.training.entity.TrainingSession;
import c1220ftjavareact.gym.user.entity.UserEntity;
import c1220ftjavareact.gym.training.service.ITrainingSessionService;
import c1220ftjavareact.gym.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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
    @Transactional(readOnly = true)
    @Override
    public Integer getCountTrainingSession(Long id) {
        return  subscriptionRepository.countByTrainingSessionId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<SubscriptionInfoDTO> findAllSubscription(){
        return this.subscriptionRepository.listSubscriptions();
    }

    @Transactional(readOnly = true)
    @Override
    public Set<SubscribedSession> findSubscribedSession(Long id){
        return this.subscriptionRepository.findUserSubscriptions(id);
    }
}
