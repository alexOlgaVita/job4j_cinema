package ru.job4j.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.configuration.DatasourceConfiguration;
import ru.job4j.dto.TicketDto;
import ru.job4j.model.Ticket;
import ru.job4j.repository.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleTicketServiceTest {

    private static TicketRepository sql2oTicketRepository;
    private static FilmSessionRepository sql2oFilmSessionRepository;
    private static FilmRepository sql2oFilmRepository;
    private static HallRepository sql2oHallRepository;

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

        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
        sql2oHallRepository = new Sql2oHallRepository(sql2o);
    }

    @Test
    void whenSaveSuccessfullyWithMock() {
        var ticket = new Ticket(1, 1, 10, 15, 1);
        TicketRepository ticketRepositoryMock = mock(TicketRepository.class);
        when(ticketRepositoryMock.save(ticket)).thenReturn(Optional.of(ticket));
        TicketService simpleTicketService = new SimpleTicketService(ticketRepositoryMock, sql2oFilmSessionRepository,
                sql2oFilmRepository, sql2oHallRepository);
        Optional<Ticket> savedTicket = simpleTicketService.save(ticket);
        assertThat(savedTicket.get()).isEqualTo(ticket);
    }

    @Test
    void whenSaveFailWithMock() {
        var ticket = new Ticket(1, 1, 10, 15, 1);
        TicketRepository ticketRepositoryMock = mock(TicketRepository.class);
        when(ticketRepositoryMock.save(ticket)).thenReturn(Optional.empty());
        TicketService simpleTicketService = new SimpleTicketService(ticketRepositoryMock, sql2oFilmSessionRepository,
                sql2oFilmRepository, sql2oHallRepository);
        Optional<Ticket> savedTicket = simpleTicketService.save(ticket);
        assertThat(savedTicket).isEmpty();
    }

    @Test
    void whenFindBySessionRowPlaceSuccessfullyWithMock() {
        var ticket = new Ticket(1, 1, 10, 15, 1);
        var ticketDto = new TicketDto(1, "Фильм1", "Зал #1",
                Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()),
                10, 15, 120);

        TicketRepository ticketRepositoryMock = mock(TicketRepository.class);
        when(ticketRepositoryMock
                .findBySessionRowPlace(ticket.getId(), ticket.getRowNumber(), ticket.getPlaceNumber())).thenReturn(Optional.of(ticket));

        TicketService simpleTicketService = new SimpleTicketService(ticketRepositoryMock, sql2oFilmSessionRepository,
                sql2oFilmRepository, sql2oHallRepository);
        Optional<TicketDto> foundByAttrTicket =
                simpleTicketService.findBySessionRowPlace(ticket.getId(), ticket.getRowNumber(), ticket.getPlaceNumber());
        assertThat(foundByAttrTicket.get().getId()).isEqualTo(ticketDto.getId());
        assertThat(foundByAttrTicket.get().getPrice()).isEqualTo(ticketDto.getPrice());
        assertThat(foundByAttrTicket.get().getRowNumber()).isEqualTo(ticketDto.getRowNumber());
        assertThat(foundByAttrTicket.get().getPlaceNumber()).isEqualTo(ticketDto.getPlaceNumber());
    }

    @Test
    void whenFindBySessionRowPlaceFailWithMock() {
        var ticket = new Ticket(1, 1, 10, 15, 1);
        var ticketDto = new TicketDto(1, "Фильм1", "Зал #1",
                Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()),
                10, 15, 120);

        TicketRepository ticketRepositoryMock = mock(TicketRepository.class);
        when(ticketRepositoryMock.findBySessionRowPlace(ticket.getId(), ticket.getRowNumber(), ticket.getPlaceNumber()))
                .thenReturn(Optional.empty());

        TicketService simpleTicketService = new SimpleTicketService(ticketRepositoryMock, sql2oFilmSessionRepository,
                sql2oFilmRepository, sql2oHallRepository);
        Optional<TicketDto> foundByAttrTicket =
                simpleTicketService.findBySessionRowPlace(ticket.getId(), ticket.getRowNumber(), ticket.getPlaceNumber());
        assertThat(foundByAttrTicket).isEmpty();
    }
}