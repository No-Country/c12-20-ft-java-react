package c1220ftjavareact.gym.subscription.service;

import c1220ftjavareact.gym.subscription.other.SubscribedSessionDTO;
import c1220ftjavareact.gym.subscription.other.SubscriptionInfoDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionSaveDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionUpdateDTO;
import c1220ftjavareact.gym.subscription.entity.Subscription;

import java.util.Set;

public interface ISubscriptionService {

    /// Crear una subscripcion
    void saveSubscription(SubscriptionSaveDTO subscriptionSaveDTO);

    /// Actualiza el estado de una subscripcion
    void updateSubscription(SubscriptionUpdateDTO subscriptionUpdateDTO);

    /// Obtener la entidad de la subscripcion
    Subscription getSubscriptionById(Long id);

    /// Actualiza todas las subscripciones (Funcion que se ejecuta automaticamente cuando el sistema se inicia o una vez al dia)
    void updateSubscriptionsStatus();

    /// MARCOS
    Set<SubscriptionInfoDTO> findAllSubscription();

    /// MARCOS
    Set<SubscribedSessionDTO> findSubscribedSession(Long id);

}