create table if not exists addresses
(
    id         serial primary key,
    city       varchar(30),
    street     varchar(30),
    flatNumber int
);

create table if not exists lessons
(
    id          serial primary key,
    name        varchar(30),
    description text
);

create table if not exists groups
(
    id   serial primary key,
    name varchar(30)
);

create table if not exists teachers
(
    id          serial primary key,
    firstName   varchar(30),
    lastName    varchar(30),
    birthDate   date,
    phoneNumber varchar(10),
    email       varchar(30),
    password    varchar(30),
    address_id  int references addresses (id) on delete set null,
    linkedinUrl varchar(30)
);

create table if not exists students
(
    id          serial primary key,
    firstName   varchar(30),
    lastName    varchar(30),
    birthDate   date,
    phoneNumber varchar(10),
    email       varchar(30),
    password    varchar(30),
    address_id  int references addresses (id) on delete set null,
    group_id    int references groups (id) on delete set null,
    studiesType text
);

create table if not exists schedules
(
    id        serial primary key,
    date      date,
    startTime time,
    endTime   time
);

create table if not exists courses
(
    id          serial primary key,
    location    varchar(30),
    schedule_id int references schedules (id) on delete set null,
    lesson_id   int references lessons (id) on delete set null,
    teacher_id  int references teachers (id) on delete set null
);

create table if not exists schedule_dates
(
    schedules_id int references schedules (id) on delete set null,
    dates        date
);

create table if not exists courses_groups
(
    courses_id int references courses (id)  on delete set null,
    groups_id  int references groups (id)  on delete set null
);