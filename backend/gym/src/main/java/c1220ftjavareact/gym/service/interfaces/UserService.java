package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.domain.User;
import c1220ftjavareact.gym.domain.dto.*;
import c1220ftjavareact.gym.service.email.TemplateStrategy;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    User findUserById(String id);

    UserProjection findLoginInfo(String email);

    User findUserByEmail(String email);
    void assertEmailIsNotRegistered(String email);
    void saveUser(UserSaveDTO model, String role);

    void saveAdmin(UserSaveDTO model, String role);

    Boolean sendCreateMessage(UserSaveDTO model, TemplateStrategy strategy);
    void userLogicalDeleteById(String id, String role);

    User updateUser(UserUpdateDTO dto, String id);
}
