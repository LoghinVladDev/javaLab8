
use music_albums;

create table artists(
                        ID integer not null auto_increment,
                        name varchar(100) not null unique,
                        country varchar(100),
                        primary key (ID)
);

create table albums(
    ID integer not null auto_increment,
    name varchar(100) not null,
    artist_id integer not null references artists on delete restrict,
    release_year integer,
    primary key (ID)
);

create table charts(
    ID integer not null auto_increment,
    name varchar(100) not null,
    primary key (ID)
);

create table chart_positions(
    ID integer not null auto_increment,
    chart_ID integer not null,
    album_ID integer not null,
    position integer not null,
    primary key (ID),
    unique (chart_ID, album_ID),
    unique (chart_ID, position)
);