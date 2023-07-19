package c1220ftjavareact.gym.payment.service;


import c1220ftjavareact.gym.payment.dto.PaymentDTO;
import c1220ftjavareact.gym.payment.dto.PaymentMapper;
import c1220ftjavareact.gym.payment.entity.PaymentEntity;
import c1220ftjavareact.gym.payment.repository.PaymentRepository;
import c1220ftjavareact.gym.subscription.dto.SubscriptionDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionMapper;
import c1220ftjavareact.gym.subscription.entity.SubscriptionEntity;
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
