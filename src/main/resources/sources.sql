create table sources (
    id SERIAL8,
    userId varchar(50) NOT NULL,
    name varchar(50) NOT NULL,
    description varchar(100) NOT NULL,
    PRIMARY KEY(userId, name)
);