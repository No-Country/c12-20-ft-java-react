package c1220ftjavareact.gym.payment.service;

import c1220ftjavareact.gym.payment.dto.PaymentDTO;

import java.util.List;

public interface PaymentService {
    PaymentDTO createPayment(PaymentDTO paymentDTO);

    PaymentDTO updatePayment(int id, PaymentDTO paymentDTO);

    Boolean deletePayment(int id);

    PaymentDTO getPaymentDtoById(int id);

    List<PaymentDTO> getAllPaymentsDto();
}
