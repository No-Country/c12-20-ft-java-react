package c1220ftjavareact.gym.domain.mapper;

import c1220ftjavareact.gym.domain.dto.PaymentDTO;
import c1220ftjavareact.gym.repository.entity.PaymentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    private final ModelMapper modelMapper;
    @Autowired
    public PaymentMapper(ModelMapper modelMapper) {
       this.modelMapper = modelMapper;
    }
    public PaymentDTO convertToDto(PaymentEntity paymentEntity) {
            return modelMapper.map(paymentEntity, PaymentDTO.class);
    }
    public PaymentEntity convertToEntity(PaymentDTO paymentDTO) {
            return modelMapper.map(paymentDTO, PaymentEntity.class);
    }

}
