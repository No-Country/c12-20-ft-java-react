package c1220ftjavareact.gym.active.service;

import c1220ftjavareact.gym.active.dto.ActiveUserDTO;
import c1220ftjavareact.gym.active.entity.ActiveUser;
import c1220ftjavareact.gym.active.exception.ActiveUserException;
import c1220ftjavareact.gym.active.repository.ActiveUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImpActiveUserService implements IActiveUserService {
    private final ActiveUserRepository activeUserRepository;

    private final ModelMapper modelMapper;

    public ImpActiveUserService(ActiveUserRepository activeUserRepository, ModelMapper modelMapper) {
        this.activeUserRepository = activeUserRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ActiveUserDTO sendActiveUser(ActiveUserDTO activeUserDTO) {
        Optional<ActiveUser> activeUser = activeUserRepository.findByUserId(activeUserDTO.getId());
        ActiveUser aux = new ActiveUser();
        if (activeUser.isEmpty()) {
            aux = modelMapper.map(activeUserDTO, ActiveUser.class);
            activeUserRepository.save(aux);
        } else {
            Long id = activeUser.get().getId();
            aux.setId(id);
            aux.setUserId(activeUserDTO.getId());
            aux.setRole(activeUserDTO.getRole());
            aux.setPicture(activeUserDTO.getPicture());
            aux.setEmail(activeUserDTO.getEmail());
            aux.setFullName(activeUserDTO.getFullName());
            aux.setToken(activeUserDTO.getToken());
            activeUserRepository.save(aux);
        }

        return modelMapper.map(aux, ActiveUserDTO.class);
    }

    @Override
    public ActiveUserDTO obtainActiveUser(Long id) {
        Optional<ActiveUser> active = activeUserRepository.findByUserId(id);
        if (active.isEmpty()) {
            throw new ActiveUserException("Active user not found.", HttpStatus.NOT_FOUND);
        }

        ActiveUser aux = active.get();
        return modelMapper.map(aux, ActiveUserDTO.class);
    }


}
