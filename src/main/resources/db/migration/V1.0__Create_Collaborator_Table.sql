create table tb_collaborator (
        number smallint,
        role tinyint not null,
        state tinyint,
        id bigint not null auto_increment,
        name varchar(255),
        email varchar(255),
        cpf varchar(14),
        city varchar(255),
        complement varchar(255),
        street varchar(255),
        zip varchar(9),
        primary key (id)
    ) engine=InnoDB