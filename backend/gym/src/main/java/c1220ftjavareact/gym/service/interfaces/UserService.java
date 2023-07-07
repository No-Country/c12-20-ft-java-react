package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.domain.User;
import c1220ftjavareact.gym.domain.dto.*;
import c1220ftjavareact.gym.service.email.TemplateStrategy;

public interface UserService {
    User findUserById(String id);

    UserProjection findLoginInfo(String email);

    User findUserByEmail(String email);
    void assertEmailIsNotRegistered(String email);
    User saveUser(UserSaveDTO model, String role);
    void updateForgottenPassword(UserPasswordDTO model);
    Boolean sendCreateMessage(UserSaveDTO model, TemplateStrategy strategy);
    void registerAdmin();
    void authenticate(UserAuthDTO model);
    void userLogicalDeleteById(String id, String role);

    User updateUser(UserUpdateDTO dto, String id);
}
