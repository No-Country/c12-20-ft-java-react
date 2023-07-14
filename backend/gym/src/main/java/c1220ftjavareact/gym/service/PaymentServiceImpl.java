package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.dto.PaymentDTO;
import c1220ftjavareact.gym.domain.dto.SubscriptionDTO;
import c1220ftjavareact.gym.domain.mapper.PaymentMapper;
import c1220ftjavareact.gym.domain.mapper.SubscriptionMapper;
import c1220ftjavareact.gym.repository.PaymentRepository;
import c1220ftjavareact.gym.repository.entity.PaymentEntity;
import c1220ftjavareact.gym.repository.entity.SubscriptionEntity;
import c1220ftjavareact.gym.service.interfaces.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final SubscriptionMapper<SubscriptionDTO, SubscriptionEntity> subscriptionSaveToUserEntityMapper;


    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        PaymentEntity payment = paymentMapper.convertToEntity(paymentDTO);
        PaymentEntity savedPayment = paymentRepository.save(payment);
        return paymentMapper.convertToDto(savedPayment);
    }

    @Override
    public PaymentDTO updatePayment(int id, PaymentDTO paymentDTO) {
        PaymentEntity payment = paymentRepository.findById(id).orElse(null);
        if (payment != null) {
            SubscriptionEntity subscriptionEntity = subscriptionSaveToUserEntityMapper.map(paymentDTO.getIdSubscription());
            payment.setIdSubscription(subscriptionEntity);
            payment.setDay(paymentDTO.getDay());
            payment.setExpired(paymentDTO.getExpired());
            PaymentEntity updatedPayment = paymentRepository.save(payment);
            return paymentMapper.convertToDto(updatedPayment);
        }
        return null;
    }

    @Override
    public Boolean deletePayment(int id) {
        try {
            paymentRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public PaymentDTO getPaymentById(int id) {
        PaymentEntity payment = paymentRepository.findById(id).orElse(null);
        if (payment != null) {
            return paymentMapper.convertToDto(payment);
        }
        return null;
    }
}
