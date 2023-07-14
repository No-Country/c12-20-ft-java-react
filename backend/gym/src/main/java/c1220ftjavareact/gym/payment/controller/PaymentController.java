package c1220ftjavareact.gym.payment.controller;

import c1220ftjavareact.gym.payment.dto.PaymentDTO;
import c1220ftjavareact.gym.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public PaymentDTO createPayment(@RequestBody PaymentDTO paymentDTO) {
        return paymentService.createPayment(paymentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable int id, @RequestBody PaymentDTO paymentDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(this.paymentService.updatePayment(id, paymentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable int id) {
        boolean success = paymentService.deletePayment(id);

        if (success) {
            return ResponseEntity.ok("Payment deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.paymentService.getPaymentById(id));
    }
}