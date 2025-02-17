package ru.job4j.service.user;


import ru.job4j.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findById(int id);

    boolean deleteByEmail(String email);
}
