package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.domain.dto.PaymentDTO;
import org.springframework.stereotype.Service;

public interface PaymentService {
    PaymentDTO createPayment(PaymentDTO paymentDTO);
    PaymentDTO updatePayment(int id, PaymentDTO paymentDTO);
    Boolean deletePayment(int id);
    PaymentDTO getPaymentById(int id);
}
