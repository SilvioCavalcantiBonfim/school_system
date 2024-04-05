create table tb_student (
        id bigint not null auto_increment,
        name varchar(255),
        email varchar(255),
        cpf varchar(14),
        phone varchar(255),
        course tinyint not null,
        -- Address
        zip varchar(9),
        street varchar(255),
        number smallint not null,
        complement varchar(255),
        city varchar(255),
        state tinyint not null,
        primary key (id)
    ) engine=InnoDB