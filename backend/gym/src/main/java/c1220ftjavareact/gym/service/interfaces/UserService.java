package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.domain.User;
import c1220ftjavareact.gym.domain.dto.*;

public interface UserService {
    User findUserById(String id);

    UserProjection findLoginInfo(String email);

    User findUserByEmail(String email);
    void assertEmailIsNotRegistered(String email);
    void saveUser(UserSaveDTO model, String role);

    void saveAdmin(UserSaveDTO model, String role);
    void changeDeletedStateUser(String id, String role, Boolean state);

    User updateUser(UserUpdateDTO dto, String id);
}
