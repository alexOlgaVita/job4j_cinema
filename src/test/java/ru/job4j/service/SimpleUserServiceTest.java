package ru.job4j.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.configuration.DatasourceConfiguration;
import ru.job4j.dto.TicketDto;
import ru.job4j.model.User;
import ru.job4j.repository.user.Sql2oUserRepository;
import ru.job4j.repository.user.UserRepository;
import ru.job4j.service.user.SimpleUserService;
import ru.job4j.service.user.UserService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleUserServiceTest {

    private static UserRepository sql2oUserRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = SimpleTicketServiceTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @Test
    void whenSaveSuccessfullyWithMock() {
        var user = new User(1, "olga@milo.ru", "olga", "olgaPass");
        UserRepository userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock.save(user)).thenReturn(Optional.of(user));
        UserService simpleUserService = new SimpleUserService(userRepositoryMock);
        Optional<User> savedUser = simpleUserService.save(user);
        assertThat(savedUser.get()).isEqualTo(user);
    }

    @Test
    void whenSaveFailWithMock() {
        var user = new User(1, "olga@milo.ru", "olga", "olgaPass");
        UserRepository userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock.save(user)).thenReturn(Optional.empty());
        UserService simpleUserService = new SimpleUserService(userRepositoryMock);
        Optional<User> savedUser = simpleUserService.save(user);
        assertThat(savedUser).isEmpty();
    }

    @Test
    void whenFindByEmailAndPasswordSuccessfullyWithMock() {
        var user = new User(1, "olga@milo.ru", "olga", "olgaPass");
        var ticketDto = new TicketDto(1, "Фильм1", "Зал #1",
                Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()),
                10, 15, 120);

        UserRepository userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock
                .findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(Optional.of(user));

        UserService simpleUserService = new SimpleUserService(userRepositoryMock);
        Optional<User> foundByAttrUser =
                simpleUserService.findByEmailAndPassword(user.getEmail(), user.getPassword());

        assertThat(foundByAttrUser.get()).isEqualTo(user);
    }

    @Test
    void whenFindByEmailAndPasswordFailWithMock() {
        var user = new User(1, "olga@milo.ru", "olga", "olgaPass");

        UserRepository userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock
                .findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(Optional.empty());

        UserService simpleUserService = new SimpleUserService(userRepositoryMock);
        Optional<User> foundByAttrUser =
                simpleUserService.findByEmailAndPassword(user.getEmail(), user.getPassword());

        assertThat(foundByAttrUser).isEmpty();
    }
}