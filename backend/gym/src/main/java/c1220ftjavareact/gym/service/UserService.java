package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.dto.UserSaveDTO;

public interface UserService {

    void registerCustomer(UserSaveDTO model);

    void registerEmployee(UserSaveDTO model);

    void registerAdmin(String password);
}
