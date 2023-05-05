drop table if exists codes cascade;

create table codes (
    id bigserial not null,
    value varchar(255) not null unique,
    primary key (id)
 );

INSERT INTO codes(value)
VALUES('a0a0');