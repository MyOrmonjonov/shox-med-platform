package uz.shox.med.service;

import uz.shox.med.entity.User;

import java.util.Optional;

public interface UserService {

    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    Optional<User> findByEmailOrPhone(String value);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

}