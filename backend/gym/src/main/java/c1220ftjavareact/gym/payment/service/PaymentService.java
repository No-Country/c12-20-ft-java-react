package c1220ftjavareact.gym.payment.service;

import c1220ftjavareact.gym.payment.dto.PaymentDTO;

public interface PaymentService {
    PaymentDTO createPayment(PaymentDTO paymentDTO);

    PaymentDTO updatePayment(int id, PaymentDTO paymentDTO);

    Boolean deletePayment(int id);

    PaymentDTO getPaymentById(int id);
}
