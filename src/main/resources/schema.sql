use reservation_system_test;

create table company
(
    company_id      bigint auto_increment
        primary key,
    created_at      datetime(6) not null,
    deleted_at      datetime(6) null,
    updated_at      datetime(6) null,
    business_number varchar(13) not null,
    name            varchar(20) not null,
    phone_number    varchar(11) not null
)
    auto_increment = 23;

create table customer
(
    customer_id  bigint auto_increment
        primary key,
    created_at   datetime(6) not null,
    deleted_at   datetime(6) null,
    updated_at   datetime(6) null,
    email        varchar(50) not null,
    name         varchar(10) not null,
    password     varchar(65) not null,
    phone_number varchar(11) not null,
    role         varchar(15) not null
);

create table review
(
    review_id  bigint auto_increment
        primary key,
    created_at datetime(6)  not null,
    deleted_at datetime(6)  null,
    updated_at datetime(6)  null,
    comment    varchar(255) null,
    score      varchar(255) not null
);

create table store
(
    store_id     bigint auto_increment
        primary key,
    created_at   datetime(6)  not null,
    deleted_at   datetime(6)  null,
    updated_at   datetime(6)  null,
    address      varchar(255) not null,
    zip_code     varchar(255) not null,
    category     varchar(255) not null,
    description  varchar(255) null,
    latitude     double       not null,
    longitude    double       not null,
    name         varchar(255) not null,
    phone_number varchar(255) not null,
    company_id   bigint       not null,
    constraint FKgk2yu2v7te9hv591spxnwc48k
        foreign key (store_id) references company (company_id),
    constraint FKq9ktqdkaycdcknuhslsk22uwd
        foreign key (company_id) references company (company_id)
)
    auto_increment = 21;

create table menu
(
    menu_id        bigint auto_increment
        primary key,
    created_at     datetime(6)  not null,
    deleted_at     datetime(6)  null,
    updated_at     datetime(6)  null,
    amount         bigint       not null,
    category       varchar(255) null,
    description    varchar(255) null,
    name           varchar(255) not null,
    store_store_id bigint       null,
    constraint FKhs7j5tgjv1cx1qx5vgekv5svn
        foreign key (store_store_id) references store (store_id),
    constraint FKtmf9neh4iah2b2lxw8n21nmm
        foreign key (menu_id) references store (store_id)
);

create table operation_time
(
    operation_time_id bigint auto_increment
        primary key,
    created_at        datetime(6)  not null,
    deleted_at        datetime(6)  null,
    updated_at        datetime(6)  null,
    break_end_time    time         null,
    break_start_time  time         null,
    day_of_week       varchar(255) not null,
    end_time          time         not null,
    start_time        time         not null,
    constraint FK97e8h4b6ny772bggsrh3dt86s
        foreign key (operation_time_id) references store (store_id)
);

create table reservation
(
    reservation_id     bigint auto_increment
        primary key,
    created_at         datetime(6) not null,
    deleted_at         datetime(6) null,
    updated_at         datetime(6) null,
    amount             bigint      null,
    number_of_customer int         not null,
    payment_date       datetime(6) null,
    payment_status     varchar(40) null,
    payment_type       varchar(40) null,
    reservation_status varchar(40) not null,
    reserved_date      date        not null,
    reserved_time      time        not null,
    customer_id        bigint      null,
    review_id          bigint      null,
    store_id           bigint      null,
    constraint FK41v6ueo0hiran65w8y1cta2c2
        foreign key (customer_id) references customer (customer_id),
    constraint FKi3wg5pq3qkuxx7e6csndvce63
        foreign key (store_id) references store (store_id),
    constraint FKrqch34pw8aspb1gmgbdwjceya
        foreign key (review_id) references review (review_id)
)
    auto_increment = 5;

create table reservation_menu
(
    reservation_menu_id bigint auto_increment
        primary key,
    created_at          datetime(6) not null,
    deleted_at          datetime(6) null,
    updated_at          datetime(6) null,
    menu_id             bigint      null,
    reservation_id      bigint      null,
    constraint FK6ie10vjca5yk9yo79i8fhi331
        foreign key (menu_id) references menu (menu_id),
    constraint FK6m8t814b7jj5vst549hnp9lpd
        foreign key (reservation_id) references reservation (reservation_id)
);

