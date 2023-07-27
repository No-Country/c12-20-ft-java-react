package c1220ftjavareact.gym.payment.service;

import c1220ftjavareact.gym.payment.dto.PaymentDTO;
import c1220ftjavareact.gym.payment.dto.PaymentProjectionDto;

import java.util.List;

public interface PaymentService {
    PaymentDTO createPayment(PaymentDTO paymentDTO);

    PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO);

    void deletePayment(Long id);

    PaymentDTO getPaymentDtoById(Long id);

    List<PaymentDTO> getAllPaymentsDto();

    List<PaymentProjectionDto> getPaymentWithSubscription(Long id);
}
