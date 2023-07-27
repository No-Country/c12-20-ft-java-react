package c1220ftjavareact.gym.payment.service;

import c1220ftjavareact.gym.payment.dto.PaymentDTO;

import java.util.List;

public interface PaymentService {
    PaymentDTO createPayment(PaymentDTO paymentDTO);

    PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO);

    void deletePayment(Long id);

    PaymentDTO getPaymentDtoById(Long id);

    List<PaymentDTO> getAllPaymentsDto();
}
