package c1220ftjavareact.gym.subscription.dto;

import c1220ftjavareact.gym.subscription.dto.SubscriptionDTO;
import c1220ftjavareact.gym.subscription.entity.SubscriptionEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public SubscriptionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SubscriptionDTO convertToDto(SubscriptionEntity subscriptionEntity) {
        return modelMapper.map(subscriptionEntity, SubscriptionDTO.class);
    }

    public SubscriptionEntity convertToEntity(SubscriptionDTO subscriptionDTO) {
        return modelMapper.map(subscriptionDTO, SubscriptionEntity.class);
    }
}