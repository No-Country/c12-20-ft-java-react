package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.dto.UserAuthDTO;
import c1220ftjavareact.gym.domain.dto.UserKeysDTO;
import c1220ftjavareact.gym.domain.dto.UserSaveDTO;

public interface UserService {

    void registerCustomer(UserSaveDTO model);

    void registerEmployee(UserSaveDTO model);

    void registerAdmin();

    void authenticate(UserAuthDTO model);

    UserKeysDTO generateUserKeys(String email);

}
