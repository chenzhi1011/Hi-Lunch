create table admin
(
    id       int auto_increment
        primary key,
    login_id varchar(15) not null,
    pwd      varchar(20) not null
);

create table cart
(
    id          int auto_increment
        primary key,
    user_id     int      not null,
    menu_id     int      not null,
    num         smallint not null,
    create_time datetime null
);

create table employee
(
    id          bigint auto_increment comment 'pk'
        primary key,
    name        varchar(32)   not null,
    username    varchar(32)   not null,
    password    varchar(64)   not null,
    phone       varchar(11)   not null,
    status      int default 1 not null comment '0 unavailable 1 available',
    create_time datetime      null,
    update_time datetime      null,
    create_user bigint        null,
    update_user bigint        null,
    constraint idx_username
        unique (username)
)
    comment 'employee info' collate = utf8mb3_bin;

create table menu
(
    id          tinyint auto_increment comment 'menu_id'
        primary key,
    name        varchar(10)  not null,
    price       smallint     not null,
    description varchar(80)  null,
    image       varchar(300) not null comment 'image link',
    category    tinyint      not null comment '1 is food; 2 is drinks',
    is_sale     tinyint      not null comment '0: stop saling ; 1 is saling',
    create_time datetime     null,
    update_time datetime     null,
    weekday     tinyint      null
)
    comment 'all of the food';

create table `order`
(
    id          int auto_increment
        primary key,
    order_no    int      not null,
    user_id     int      not null,
    menu_id     int      not null,
    num         int      null,
    create_time datetime null
);

create table persist_token
(
    secret_token varchar(200) not null
        primary key,
    create_time  datetime     not null
);

create table purchase_num
(
    id          int auto_increment
        primary key,
    user_id     int      not null,
    menu_id     smallint not null,
    num         int      null,
    create_time datetime null,
    update_time datetime null
);

create table stocks
(
    id          int auto_increment
        primary key,
    menu_id     int           not null,
    stock       int default 0 not null,
    create_time datetime      null,
    update_time datetime      null
);

create table user
(
    id          int                            not null
        primary key,
    pwd         varchar(20)                    not null,
    department  varchar(10)                    not null,
    email       varchar(20)                    null,
    create_time datetime                       not null,
    update_time datetime                       null,
    image       varchar(200) default 's3Links' null comment 'icon for user'
);


