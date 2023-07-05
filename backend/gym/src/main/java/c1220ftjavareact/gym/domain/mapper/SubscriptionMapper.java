package c1220ftjavareact.gym.domain.mapper;

import c1220ftjavareact.gym.domain.dto.SubscriptionDTO;
import c1220ftjavareact.gym.repository.entity.SubscriptionEntity;
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