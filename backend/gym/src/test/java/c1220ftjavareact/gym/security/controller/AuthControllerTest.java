package c1220ftjavareact.gym.security.controller;

import c1220ftjavareact.gym.user.dto.EmployeeSaveDTO;
import c1220ftjavareact.gym.user.dto.UserSaveDTO;
import c1220ftjavareact.gym.user.dto.mapper.UserMapperBeans;
import c1220ftjavareact.gym.events.event.UserCreatedEvent;
import c1220ftjavareact.gym.security.jwt.GoogleOauth2Service;
import c1220ftjavareact.gym.security.jwt.JwtService;
import c1220ftjavareact.gym.security.service.AuthService;
import c1220ftjavareact.gym.email.UserCreatedStrategy;
import c1220ftjavareact.gym.user.service.UserService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService service;
    @Mock
    private AuthService springAuth;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserMapperBeans userMapper;
    @Mock
    private GoogleOauth2Service googleOauth2Service;
    @Mock
    private ApplicationEventPublisher publisher;

    @Test
    public void employeeSignUp(){
        EmployeeSaveDTO employeeMock = new EmployeeSaveDTO("Marcos", "Lopez", "owner@gmail.com");

        verify( service ).assertEmailIsNotRegistered(employeeMock.email());

        var userMock =when( this.userMapper.employeeSaveToUserSave().map(employeeMock) ).thenReturn(UserSaveDTO.builder()
                .name(employeeMock.name()).lastname(employeeMock.lastname())
                .email(employeeMock.email()).password(UUID.randomUUID().toString().substring(0, 6))
                .build());


        verify( service ).saveUser(userMock.getMock(), "EMPLOYEE");
        verify( publisher ).publishEvent(
                new UserCreatedEvent(
                        authController,
                        employeeMock.email(),
                        employeeMock.name(),
                        employeeMock.lastname(),
                        "1234",
                        new UserCreatedStrategy()
                )
        );

        authController.employeeSignUp(employeeMock);
    }
}
