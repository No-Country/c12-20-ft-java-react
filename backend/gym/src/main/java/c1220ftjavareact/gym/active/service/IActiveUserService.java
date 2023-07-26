package c1220ftjavareact.gym.active.service;

import c1220ftjavareact.gym.active.dto.ActiveUserDTO;

public interface IActiveUserService {
    ActiveUserDTO sendActiveUser(ActiveUserDTO activeUserDTO);

    ActiveUserDTO obtainActiveUser(Long id);
}
