package c1220ftjavareact.gym.subscription.service;

import c1220ftjavareact.gym.subscription.dto.SubscribedSessionDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionInfoDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionSaveDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionUpdateDTO;
import c1220ftjavareact.gym.subscription.entity.Subscription;
import c1220ftjavareact.gym.subscription.enums.State;
import c1220ftjavareact.gym.subscription.exception.SubscriptionException;
import c1220ftjavareact.gym.subscription.repository.SubscriptionRepository;
import c1220ftjavareact.gym.training.entity.TrainingSession;
import c1220ftjavareact.gym.training.service.ITrainingSessionService;
import c1220ftjavareact.gym.user.entity.UserEntity;
import c1220ftjavareact.gym.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ImplSubscriptionService implements ISubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    private final ITrainingSessionService iTrainingSessionService;

    private final UserService iUserService;

    public ImplSubscriptionService(SubscriptionRepository subscriptionRepository, ITrainingSessionService iTrainingSessionService, UserService iUserService) {
        this.subscriptionRepository = subscriptionRepository;
        this.iTrainingSessionService = iTrainingSessionService;
        this.iUserService = iUserService;
    }

    @Override
    public void saveSubscription(SubscriptionSaveDTO subscriptionSaveDTO) {
        /// Verificar si existe Cliente
        UserEntity userEntity = iUserService.getUserEntity(subscriptionSaveDTO.getCustomerId());

        /// Verificar si existe Training
        TrainingSession training = iTrainingSessionService.getTrainingEntity(subscriptionSaveDTO.getTrainingId());

        /// Verificar training activo
        if (training.isDeleted()) {
            throw new SubscriptionException("training not available.", HttpStatus.NOT_ACCEPTABLE);
        }

        /// Verificar que no este subscripto a ese mismo training
        List<Subscription> subscriptions = training.getSubscriptions();
        this.verifyUserNotSubscribed(subscriptions, subscriptionSaveDTO.getCustomerId());

        /// Verificar si hay cupos disponibles
        /* crear metodo */

        /// Crear entidad a persistir
        Subscription savedSubscription = Subscription.builder()
                .createDate(this.generateDate())
                .customer(userEntity)
                .trainingSession(training)
                .state(State.RESERVED)
                .build();

        /// Realizar persistencia
        subscriptionRepository.save(savedSubscription);

    }

    @Override
    public void updateSubscription(SubscriptionUpdateDTO subscriptionUpdateDTO) {

    }

    @Override
    public Subscription getSubscriptionById(Long id) {
        Optional<Subscription> subscriptionEntity = subscriptionRepository.findById(id);
        if(subscriptionEntity.isEmpty()) {
            throw new SubscriptionException("Subscription not found.", HttpStatus.NOT_FOUND);
        }

        return subscriptionEntity.get();
    }

    private void verifyUserNotSubscribed(List<Subscription> subscriptions, Long customerId) {
        for (Subscription item : subscriptions) {
            if (item.getCustomer().getId().equals(customerId) && (item.getState() == State.ACTIVE || item.getState() == State.RESERVED)) {
                throw new SubscriptionException("The user is already subscribed.", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }
    }

    private LocalDate generateDate() {
        // Obtener la zona horaria de Argentina (GMT-3)
        ZoneId argentinaZone = ZoneId.of("America/Argentina/Buenos_Aires");

        return LocalDate.now(argentinaZone);
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
