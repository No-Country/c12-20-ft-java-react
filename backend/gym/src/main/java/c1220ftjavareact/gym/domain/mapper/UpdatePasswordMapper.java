package c1220ftjavareact.gym.domain.mapper;

import c1220ftjavareact.gym.repository.entity.UpdatePassword;

@FunctionalInterface
public interface UpdatePasswordMapper<T> {
    UpdatePassword map(T model);
}
