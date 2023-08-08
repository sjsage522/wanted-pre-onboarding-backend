CREATE TABLE IF NOT EXISTS users (
    id          bigint not null auto_increment,
    email       varchar(255) not null,
    password    varchar(255) not null,
    salt        varchar(255) not null,
    created_at  datetime(6),
    modified_at datetime(6),

    PRIMARY KEY (id),
    INDEX (email)
) Engine=InnoDB;

CREATE TABLE IF NOT EXISTS posts (
    id          bigint not null auto_increment,
    user_id     bigint not null,
    title       varchar(255) not null,
    content     varchar(255) not null,
    created_at  datetime(6),
    modified_at datetime(6),

    PRIMARY KEY (id),
    CONSTRAINT fk_posts_users FOREIGN KEY (user_id) REFERENCES users (id)
) Engine=InnoDB;