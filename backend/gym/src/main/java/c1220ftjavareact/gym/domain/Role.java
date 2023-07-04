package c1220ftjavareact.gym.domain;

import c1220ftjavareact.gym.domain.exception.RoleNotFoundException;

import java.util.List;

public enum Role {
    CLIENT, USER, ADMIN;

    public static Role stringToRole(final String value){
        return List.of(CLIENT, USER, ADMIN)
                .stream()
                .filter(role -> role.equals(value))
                .findFirst()
                .orElseThrow(()-> new RoleNotFoundException("Rol: "+value+" invalido, los roles validos son CLIENT, USER, ADMIN"));
    }
}
