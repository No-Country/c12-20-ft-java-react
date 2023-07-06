package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.domain.User;
import c1220ftjavareact.gym.domain.dto.UserLoginDTO;
import c1220ftjavareact.gym.domain.dto.UserAuthDTO;
import c1220ftjavareact.gym.domain.dto.UserPasswordDTO;
import c1220ftjavareact.gym.domain.dto.UserSaveDTO;
import c1220ftjavareact.gym.service.email.TemplateStrategy;

public interface UserService {

    User findUserByEmail(String email);

    void assertEmailIsNotRegistered(String email);

    User saveUser(UserSaveDTO model, String role);

    void updateForgottenPassword(UserPasswordDTO model);

    Boolean sendCreateMessage(UserSaveDTO model, TemplateStrategy strategy);

    void registerAdmin();

    void authenticate(UserAuthDTO model);

    String createToken(User user);

    String getEmailWithToken(String token);

    UserLoginDTO getUserLogin(String token, User user);

}
