insert into film_sessions(film_id, halls_id, start_time, end_time, price) values(1, 1, '2025-08-07 10:00:01', '2025-08-07 11:01:01', 120);

insert into film_sessions(film_id, halls_id, start_time, end_time, price) values(2, 1,'2025-08-07 15:00:01', '2025-08-07 16:01:01', 120);
insert into film_sessions(film_id, halls_id, start_time, end_time, price) values(3, 2, '2025-08-07 10:30:01', '2025-08-07 11:40:01', 120);
insert into film_sessions(film_id, halls_id, start_time, end_time, price) values(4, 3, '2025-08-07 15:45:01', '2025-08-07 17:01:01', 90);
insert into film_sessions(film_id, halls_id, start_time, end_time, price) values(5, 2, '2025-08-07 09:00:01', '2025-08-07 11:20:01', 176);
insert into film_sessions(film_id, halls_id, start_time, end_time, price) values(6, 3, '2025-08-07 12:20:01', '2025-08-07 14:05:01', 210);
insert into film_sessions(film_id, halls_id, start_time, end_time, price) values(7, 1, '2025-08-07 13:25:01', '2025-08-07 15:05:01', 105);
insert into film_sessions(film_id, halls_id, start_time, end_time, price) values(8, 2, '2025-08-07 17:10:01', '2025-08-07 19:25:01', 99);

-- начиная со 2 записи (с id=2) был вариант с использованием current_timestamp() для инициализации - но с H2 (в отличие 
-- от postgres - профиль prod) почему-то работает плохо (возникает ошибка про timezone). Поэтому, чтобы воспользоваться таким вариантом, 
-- приходилось сперва очищать времен.таблицу, потом выполнять скрипт с первой строкой (где в строковом виде задано значение)
-- и только потом можно раскомментировать последующие строки current_timestamp() и выполнить все вместе. 
-- Пока не знаю, как это обойти.
-- чисто для H2 начиная с id=2 сделала все в виде строк для задания значения полей типа timeStamp.
-- Старый вариант:
--insert into film_sessions(film_id, halls_id, start_time, end_time, price) values(2, 1, current_timestamp(0)+'62', current_timestamp(1), 120);
--insert into film_sessions(film_id, halls_id, start_time, end_time, price) values(3, 2, current_timestamp(0)+'7200', current_timestamp(1), 120);
--insert into film_sessions(film_id, halls_id, start_time, end_time, price) values(4, 3, current_timestamp(0)+'6000', current_timestamp(1), 90);
--insert into film_sessions(film_id, halls_id, start_time, end_time, price) values(5, 2, current_timestamp(0)+'8000', current_timestamp(1), 176);
--insert into film_sessions(film_id, halls_id, start_time, end_time, price) values(6, 3, current_timestamp(0)+'8300', current_timestamp(1), 210);
--insert into film_sessions(film_id, halls_id, start_time, end_time, price) values(7, 1, current_timestamp(0)+'5300', current_timestamp(1), 105);
--insert into film_sessions(film_id, halls_id, start_time, end_time, price) values(8, 2, current_timestamp(0)+'7700', current_timestamp(1), 99);