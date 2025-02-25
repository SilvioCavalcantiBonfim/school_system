create table tb_collaborator (
        id bigint not null auto_increment,
        name varchar(255),
        email varchar(255),
        cpf varchar(14),
        role tinyint not null,
        -- address
        zip varchar(9),
        street varchar(255),
        number smallint,
        complement varchar(255),
        city varchar(255),
        state tinyint,
        primary key (id)
    ) engine=InnoDB