package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.dto.UserSaveDTO;

public interface UserService {

    void registerClient(UserSaveDTO model);

    void registerUser(UserSaveDTO model);
}
