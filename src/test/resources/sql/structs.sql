create table adminuser
(
    adminUserId bigint auto_increment
        primary key,
    teacherId   bigint        not null,
    password    varchar(120)  not null,
    role        int default 0 not null comment '角色 0:院级管理员,1:校级管理员,2:系统管理员'
);

create table college
(
    collegeId bigint auto_increment
        primary key,
    name      varchar(60) not null
);

create table dailycard
(
    dailyCardId bigint auto_increment
        primary key,
    userId      bigint        not null,
    answer      text          null,
    result      int default 0 not null,
    date        date          not null
);

create table info
(
    infoId              bigint auto_increment
        primary key,
    userId              bigint      not null,
    phone               varchar(20) null,
    result              int         null,
    date                date        null,
    continuousClockDays int         null,
    constraint UserId
        unique (userId)
);

create table profession
(
    professionId bigint auto_increment
        primary key,
    collegeId    bigint      not null,
    name         varchar(60) not null
);

create table resultcache
(
    resultCacheId bigint auto_increment
        primary key,
    userId        bigint                              not null,
    result        int       default 0                 not null,
    codeKey       varchar(120)                        not null,
    date          date                                not null,
    expiredAt     timestamp default 0                 not null,
);

create table student
(
    studentId bigint auto_increment
        primary key,
    userId    bigint      not null,
    name      varchar(60) not null,
    idCard    varchar(60) not null,
    number    varchar(60) not null,
    xClassId  bigint      null,
);

create table teacher
(
    teacherId bigint auto_increment
        primary key,
    userId    bigint      not null,
    name      varchar(60) not null,
    idCard    varchar(60) null,
    number    varchar(60) not null comment '工号',
    collegeId bigint      null,
);

create table user
(
    userId   bigint auto_increment
        primary key,
    userType int default 0 not null comment '用户类型 0=学生;1=老师'
);

create table useraccess
(
    userAccessId bigint auto_increment
        primary key,
    userId       bigint       not null,
    token        varchar(120) not null,
    expired      timestamp    null
);

create table xclass
(
    xclassId     bigint auto_increment
        primary key,
    professionId bigint      not null,
    name         varchar(60) not null
);


