create schema crc_schema;
set schema crc_schema;

create table projet (
    projet_id bigint primary key not null,
    nom varchar(255) unique
);

create table klass (
    klass_id bigint primary key not null,
    nom varchar(255),
    projet_id bigint,
    foreign key (projet_id) references projet,
    unique(nom,projet_id)
);

create table responsabilite(
    responsabilite_id bigint primary key not null,
    titre varchar(255),
    klass_id bigint,
    foreign key (klass_id) references klass
);

create table collaborateur (
    collaborateur_id bigint primary key,
    principal_id bigint,
    collaborant_id bigint,
    foreign key(principal_id) references klass,
    foreign key (collaborant_id) references klass,
    unique(principal_id, collaborant_id)
);

create table id_table(
    id varchar(255) primary key,
    next_id bigint not null
);