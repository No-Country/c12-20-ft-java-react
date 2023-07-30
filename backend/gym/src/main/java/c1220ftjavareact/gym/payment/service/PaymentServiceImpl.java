package c1220ftjavareact.gym.payment.service;

import c1220ftjavareact.gym.payment.dto.PaymentDTO;
import c1220ftjavareact.gym.payment.entity.PaymentEntity;
import c1220ftjavareact.gym.payment.exception.PaymentException;
import c1220ftjavareact.gym.payment.repository.PaymentRepository;
import c1220ftjavareact.gym.subscription.entity.Subscription;
import c1220ftjavareact.gym.subscription.service.ImplSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;
    private final ImplSubscriptionService subscriptionService;

    @Transactional
    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Long suscriptionId = paymentDTO.getSubscriptionId();
        Subscription subscription = this.subscriptionService.getSubscriptionById(suscriptionId);
        if (paymentDTO.getSubscriptionId() == null || paymentDTO.getSubscriptionId() <= 0) {
            throw new PaymentException("Suscription not found", HttpStatus.NOT_FOUND);
        }

        if (paymentDTO.getPaymentAt() == null) {
            throw new PaymentException("The day is incorrect, please insert a valid date", HttpStatus.BAD_REQUEST);
        }

        if (paymentDTO.getExpiredAt() == null) {
            throw new PaymentException("The expiration date is incorrect, please insert a valid date", HttpStatus.BAD_REQUEST);
        }

        if (subscription != null) {
            PaymentEntity payment = this.modelMapper.map(paymentDTO, PaymentEntity.class);
            payment.setSubscriptionId(subscription);
            payment = this.paymentRepository.save(payment);
            PaymentDTO updatedPaymentDTO = this.modelMapper.map(payment, PaymentDTO.class);;
            return updatedPaymentDTO;
        }
        return null;
    }

    @Transactional
    @Override
    public PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO) {
        PaymentEntity payment = paymentRepository.findById(id).orElse(null);
        if (paymentDTO.getSubscriptionId() == null || paymentDTO.getSubscriptionId() <= 0) {
            throw new PaymentException("Suscription not found", HttpStatus.NOT_FOUND);
        }

        if (paymentDTO.getPaymentAt() == null) {
            throw new PaymentException("The day is incorrect, please insert a valid date", HttpStatus.BAD_REQUEST);
        }

        if (paymentDTO.getExpiredAt() == null) {
            throw new PaymentException("The expiration date is incorrect, please insert a valid date", HttpStatus.BAD_REQUEST);
        }

        if (payment == null) {
            throw new PaymentException("Suscription not found", HttpStatus.NOT_FOUND);
        } else {
            this.modelMapper.map(paymentDTO, payment);
            PaymentEntity updatePayment = this.paymentRepository.save(payment);
            return this.modelMapper.map(updatePayment, PaymentDTO.class);
        }
    }

    @Transactional
    @Override
    public void deletePayment(Long id) {
        PaymentEntity paymentExist = this.paymentRepository.findById(id).orElseThrow(() -> {throw new PaymentException("Payment not found",HttpStatus.NOT_FOUND);});
        if (paymentExist != null) {
            this.paymentRepository.deleteById(id);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public PaymentDTO getPaymentDtoById(Long id) {
        PaymentEntity payment = paymentRepository.findById(id).orElse(null);
        if (payment == null) {
            throw new PaymentException("Payment not found", HttpStatus.NOT_FOUND);
        } else {
            return this.modelMapper.map(payment, PaymentDTO.class);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<PaymentDTO> getAllPaymentsDto() {
        List<PaymentEntity> paymentList = this.paymentRepository.findAll();
        List<PaymentDTO> paymentDtoList = new ArrayList<>();

        if (paymentList.size() <= 0) {
            throw new PaymentException("The list of payments is empty", HttpStatus.BAD_REQUEST);
        }
        for (PaymentEntity payment: paymentList) {
            PaymentDTO paymentDto = this.modelMapper.map(payment, PaymentDTO.class);
            paymentDtoList.add(paymentDto);
        }
        return paymentDtoList;
    }
}
