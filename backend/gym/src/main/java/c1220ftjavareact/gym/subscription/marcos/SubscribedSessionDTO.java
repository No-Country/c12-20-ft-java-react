package c1220ftjavareact.gym.subscription.marcos;

public interface SubscribedSessionDTO {

    Long getId();

    String getPaymentAt();

    String getExpiredAt();

    String getState();

    String getActivity();

    String getDescription();

    String getRoom();

    String getClassTime();

    Boolean getMonday();

    Boolean getTuesday();

    Boolean getWednesday();

    Boolean getThursday();

    Boolean getFriday();

    Boolean getSaturday();

}
