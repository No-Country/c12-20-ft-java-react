package c1220ftjavareact.gym.payment.service;

import c1220ftjavareact.gym.payment.dto.PaymentDTO;
import c1220ftjavareact.gym.payment.entity.PaymentEntity;
import c1220ftjavareact.gym.payment.exception.PaymentException;
import c1220ftjavareact.gym.payment.repository.PaymentRepository;
import c1220ftjavareact.gym.subscription.entity.SubscriptionEntity;
import c1220ftjavareact.gym.subscription.service.SubscriptionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;
    private final SubscriptionServiceImpl subscriptionService;

    @Transactional
    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        SubscriptionEntity subscription = this.subscriptionService.getSubscriptionById(paymentDTO.getIdSubscription());
        if (paymentDTO.getIdSubscription() == null || paymentDTO.getIdSubscription() <= 0) {
            throw new PaymentException("Suscription not found", HttpStatus.NOT_FOUND);
        }

        if (paymentDTO.getDay() == null) {
            throw new PaymentException("The day is incorrect, please insert a valid date", HttpStatus.BAD_REQUEST);
        }

        if (paymentDTO.getExpired() == null) {
            throw new PaymentException("The expiration date is incorrect, please insert a valid date", HttpStatus.BAD_REQUEST);
        }
        if (subscription != null) {
            PaymentEntity payment = this.modelMapper.map(paymentDTO, PaymentEntity.class);
            this.paymentRepository.save(payment);
            this.modelMapper.map(payment, PaymentDTO.class);
            return paymentDTO;
        }
        return null;
    }

    @Transactional
    @Override
    public PaymentDTO updatePayment(int id, PaymentDTO paymentDTO) {
        PaymentEntity payment = paymentRepository.findById(id).orElse(null);
        if (paymentDTO.getIdSubscription() == null || paymentDTO.getIdSubscription() <= 0) {
            throw new PaymentException("Suscription not found", HttpStatus.NOT_FOUND);
        }

        if (paymentDTO.getDay() == null) {
            throw new PaymentException("The day is incorrect, please insert a valid date", HttpStatus.BAD_REQUEST);
        }

        if (paymentDTO.getExpired() == null) {
            throw new PaymentException("The expiration date is incorrect, please insert a valid date", HttpStatus.BAD_REQUEST);
        }

        if (payment == null) {
            throw new PaymentException("Suscription not found", HttpStatus.NOT_FOUND);
        } else {
            this.modelMapper.map(paymentDTO, PaymentEntity.class);

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
        }
        return null;
    }
}
