package c1220ftjavareact.gym.domain.mapper;

import c1220ftjavareact.gym.repository.entity.User;

@FunctionalInterface
public interface UserMapper<T> {
    User map(T model);
}
