package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.mapper.UpdatePasswordMapper;
import c1220ftjavareact.gym.repository.UpdatePasswordRepository;
import c1220ftjavareact.gym.repository.UserRepository;
import c1220ftjavareact.gym.service.interfaces.MailService;
import c1220ftjavareact.gym.service.interfaces.UpdatePasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdatePasswordServiceImp implements UpdatePasswordService {
    private final UserRepository repository;
    private final UpdatePasswordRepository passwordRepository;
    private final UpdatePasswordMapper mapper;
    private final MailService mailService;

    @Override
    public Boolean verifyEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Boolean updatePasswordEvent(String email) {
        var code = "";
        do {
            code = UUID.randomUUID().toString();
            log.info("*************** code is: {} AND LENGHT: IS {} *********************", code, code.length());
        } while (passwordRepository.existsByCode(code));

        var user = repository.findByEmail(email);
        var updatePassword = mapper.map(user);
        updatePassword.code(code);
        passwordRepository.saveAndFlush(updatePassword);

        return mailService.send(
                email,
                "CAMBIO DE CONTRASEÑA",
                updatePassword.editPasswordMessage(
                        user.get().fullname(),
                        "http://localhost:3300/password?code=" + code + "?id=" + user.get().getId().toString()
                )
        );
    }

    @Override
    public void checkEmail(String code, String id) {

    }
}
