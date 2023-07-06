package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.domain.UpdatePassword;

public interface UpdatePasswordService {

    Boolean createUpdatePasswordEvent(String email);

    UpdatePassword verifyUpdatePasswordEvent(String code, String id);

    void finishUpdatePasswordEvent(UpdatePassword updatePassword);
}
