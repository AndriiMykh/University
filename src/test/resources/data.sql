insert into addresses(id, city, street, flatNumber)
values (1, 'Kyiv', 'Peremohy', 4),
       (2, 'Kherson', 'Myru', 8),
       (3, 'Vinnitsa', 'Zhovtneva', 10),
       (4, 'Lviv', 'Peremohy', 1),
       (5, 'Kyiv', 'Zhovtneva', 32);

insert into lessons(id, name, description)
values (1, 'Physics', 'Physic lesson'),
       (2, 'Math', 'Math lesson'),
       (3, 'Java', 'Java lesson'),
       (4, 'History', 'History lesson');

insert into groups(id, name)
values (1, 'fd-41'),
       (2, 'jl-46'),
       (3, 'ay-82'),
       (4, 'un-73');

insert into teachers(id, firstName, lastname, birthDate, phoneNumber, email, password, address_id, linkedinUrl)
values (1, 'Mykola', 'Serheev', '1980-12-17', '123412412', 'mykola@gmail.com', '1111', 4, 'linkedin.com/in/Mykola/'),
       (2, 'Vlad', 'Petrov', '1970-12-17', '123425162', 'vlad@gmail.com', '2222', 4, 'linkedin.com/in/vlad/');

insert into students(id, firstName, lastname, birthDate, phoneNumber, email, password, address_id, group_id, studiesType)
values (1, 'Dmytro', 'Serheev', '1998-12-17', '123412412', 'Dmytro@gmail.com', '1111', 4, 2, 'full-time'),
       (2, 'Serhii', 'Petrov', '1998-12-17', '123425162', 'Serhii@gmail.com', '2222', 4, 3, 'part-time');


insert into schedules(id, date, startTime, endTime)
values (1, '2021-01-01', '13:30', '15:15'),
       (2, '2021-01-02', '13:30', '15:15'),
       (3, '2021-01-03', '13:30', '15:15');

insert into courses(id, location, schedule_id, lesson_id, teacher_id)
values (1, 'room 5', 2, 1, 2),
       (2, 'room 13', 3, 3, 1),
       (3, 'room 10', 1, 1, 1);

insert into schedule_dates(schedules_id, dates)
values (1, '2021-01-01'),
        (1, '2021-01-01');

insert into courses_groups(courses_id, groups_id)
values (1, 1),
       (1, 2),
       (2,1),
       (2,2);