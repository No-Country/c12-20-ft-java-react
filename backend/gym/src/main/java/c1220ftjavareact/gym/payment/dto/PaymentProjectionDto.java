package c1220ftjavareact.gym.payment.dto;

public interface PaymentProjectionDto {
    String getSubscriptionId();

    String getMethod();
    String getPaymentAt();
    String getExpiredAt();

}
