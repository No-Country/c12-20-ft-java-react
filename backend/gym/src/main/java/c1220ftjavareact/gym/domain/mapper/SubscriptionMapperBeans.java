package c1220ftjavareact.gym.domain.mapper;

import c1220ftjavareact.gym.domain.dto.*;
import c1220ftjavareact.gym.repository.entity.SubscriptionEntity;
import c1220ftjavareact.gym.repository.entity.TrainingSession;
import c1220ftjavareact.gym.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionMapperBeans {



    //devuelve el dto
    @Bean
    public SubscriptionMapper<SubscriptionEntity, SubscriptionDTO> subscriptionEntityToSubscriptionSave() {
        return (entity) -> SubscriptionDTO.builder()
                .customerId(entity.getIdCustomer().getId())
                .training(entity.getTraining().getId())
                .state(entity.getState())
                .subscriptionDay(entity.getSubscriptionDay())
                .build();
    }

    @Bean
    public SubscriptionMapper<SubscriptionDTO, SubscriptionEntity> subscriptionSaveToUserEntity() {
        return (subscriptionDTO) -> SubscriptionEntity.builder()
                .idCustomer(UserEntity.builder().id(subscriptionDTO.customerId()).build())
                .training(TrainingSession.builder().id(subscriptionDTO.idTrainingSession()).build())
                .state(subscriptionDTO.state())
                .subscriptionDay(subscriptionDTO.subscriptionDay())
                .build();
    }



}
