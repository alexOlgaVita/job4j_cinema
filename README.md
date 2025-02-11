# job4j_ciinema

### О проекте
Проект посвящен разработке сайта по покупке билетов в кинотеатр (один кинотеатр, а не сеть).

### Цель
В Веб-приложении реализовать:
1. Регистрацию/Вход;
2. Вывод киносеансов и фильмов;
3. Покупку билетов.

### Техническое задание
Для реализации нужно использовать: Spring Boot, Thymeleaf, Bootstrap, Liquibase, Sql2o, PostgreSQL 
(зависимости аналогичны проекту job4j_dreamjob).
#### Представления:
Главная страница. По аналогии с job4j_dreamjob выводите общую информацию о ресурсе;
Расписание. Выводите сеансы и связанные с ними фильмы. При выборе конкретного сеанса пользователь переходит на страницу покупки билета;
Кинотека. Выводите список фильмов;
Страница покупки билета. Выводите информацию о сеансе и фильм. Также 2 выпадающих списка - один для указания ряда, другой для указания места, и кнопки "Купить", "Отменить";
Страница с результатом успешной покупки билета. Выводите, сообщение пользователю, например, "Вы успешно приобрели билет на такое место ...";
Страница с результатом неудачной покупки билета (билет уже купили). Выводите, сообщение пользователю, например, "Не удалось приобрести билет на заданное место. Вероятно оно уже занято. Перейдите на страницу бронирования билетов и попробуйте снова.". Реализовать подобный функционал нужно аналогично регистрации пользователя;
Страница регистрации. Аналогично job4j_dreamjob;
Страница вход. Аналогично job4j_dreamjob.
#### Навигационная панель:
Лого. При клике на него выполняется переход на главную страницу;
Расписание. Выводите сеансы и связанные с ними фильмы;
Кинотека. Выводите список фильмов, которые показываются в кинотеатре;
Регистрация/Вход. Если пользователь не вошел в систему;
Имя пользователя/Выйти. Если пользователь вошел в систему.
#### Разделение прав:
Все пользователи имеют право просматривать информацию на сайте;
Только зарегистрированные пользователю могут покупать билеты. 
Если пользователь не зарегистрирован и нажимает на кнопку "Купить билет", то его перебрасывает на страницу входа.
#### Схема БД:
create table files
(
id   serial primary key,
name varchar not null,
path varchar not null unique
);

create table genres
(
id   serial primary key,
name varchar unique not null
);

create table films
(
id                  serial primary key,
name                varchar                    not null,
description         varchar                    not null,
"year"              int                        not null,
genre_id            int references genres (id) not null,
minimal_age         int                        not null,
duration_in_minutes int                        not null,
file_id             int references files (id)  not null
);

create table halls
(
id          serial primary key,
name        varchar not null,
row_count   int     not null,
place_count int     not null,
description varchar not null
);

create table film_sessions
(
id         serial primary key,
film_id    int references films (id) not null,
halls_id   int references halls (id) not null,
start_time timestamp                 not null,
end_time   timestamp                 not null,
price      int                       not null
);

create table users
(
id        serial primary key,
full_name varchar        not null,
email     varchar unique not null,
password  varchar        not null
);

create table tickets
(
id           serial primary key,
session_id   int references film_sessions (id) not null,
row_number   int                               not null,
place_number int                               not null,
user_id      int                               not null,
unique (session_id, row_number, place_number)
);

### Задание
1. Ознакомиться с требованиями к Web-проектам. Проверка будет происходить в соответствии с ними;
   https://github.com/ShamRail/job4j_requirements/wiki
2. Создать репозиторий job4j_cinema;
3. Настроить проект. Указать версию Java, зависимости, плагины, профили в pom.xml. Заполнить .gitignore;
4. Создать БД cinema и создать схему db/scripts/001_ddl_create_initial_schema.sql, указанную выше;
5. Заполнить схему (таблицы). Обратите внимание, файлы-постеры нужно скачать предварительно и сохранить в папке files. 
   При вставке данных в таблицу files нужно указать относительный путь к файлам;
6. Настроить Liquibase и с помощью него выполнить скрипты SQL. На текущий момент БД готова;
7. На основе таблиц создать классы модели;
8. Спроектировать интерфейсы репозиториев и реализовать их;
9. Спроектировать интерфейсы сервисов и реализовать их;
   Важно! Не ограничивайтесь моделями БД. Модели БД используются для сохранения данных в БД и их извлечения. 
   Для представлений они не подойдут. Вы можете создать отдельные классы и их возвращать.
   Например, чтобы вывести фильмы, вам нужно еще вывести жанр. Для этого можно создать класс FilmDto или FilmPreview c полями:
   public class FilmDto {
   private int id;
   private String name;
   private String description;
   private int year;
   private int minimalAge;
   private int durationInMinutes;
   private String genre;
   }
  В сервисе нужно будет собрать объект из двух таблицы: films и genres.
  Помните, что задача сервисов не только обрабатывать данные, как в FileService, но и собирать данные. 
  Вы можете вызывать не только один репозиторий.
10. Реализовать контроллеры;
11. Реализовать фильтры;
12. Реализовать Unit тесты для всех слоев приложения;