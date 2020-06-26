create table adminuser
(
    adminUserId bigint auto_increment
        primary key,
    teacherId   bigint        not null,
    password    varchar(120)  not null,
    role        int default 0 not null comment '角色 0:院级管理员,1:校级管理员,2:系统管理员'
)
    charset = utf8;

create table college
(
    collegeId bigint auto_increment
        primary key,
    name      varchar(60) not null
)
    charset = utf8;

create table dailycard
(
    dailyCardId bigint auto_increment
        primary key,
    userId      bigint        not null,
    answer      text          null,
    result      int default 0 not null,
    date        date          not null
)
    charset = utf8;

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
)
    charset = utf8;

create table profession
(
    professionId bigint auto_increment
        primary key,
    collegeId    bigint      not null,
    name         varchar(60) not null
)
    charset = utf8;

create table resultcache
(
    resultCacheId bigint auto_increment
        primary key,
    userId        bigint        not null,
    result        int default 0 not null,
    codeKey       varchar(120)  not null,
    date          date          not null,
    expiredAt     timestamp     not null
)
    charset = utf8;

create table student
(
    studentId bigint auto_increment
        primary key,
    userId    bigint      not null,
    name      varchar(60) not null,
    idCard    varchar(60) not null,
    number    varchar(60) not null,
    xClassId  bigint      null,
    constraint UserId
        unique (userId)
)
    charset = utf8;

create table teacher
(
    teacherId bigint auto_increment
        primary key,
    userId    bigint      not null,
    name      varchar(60) not null,
    idCard    varchar(60) null,
    number    varchar(60) not null comment '工号',
    collegeId bigint      null,
    constraint UserId
        unique (userId)
)
    charset = utf8;

create table user
(
    userId   bigint auto_increment
        primary key,
    userType int default 0 not null comment '用户类型 0=学生;1=老师'
)
    charset = utf8;

create table useraccess
(
    userAccessId bigint auto_increment
        primary key,
    userId       bigint       not null,
    token        varchar(120) not null,
    expired      timestamp    null
)
    charset = utf8;

create table xclass
(
    xclassId     bigint auto_increment
        primary key,
    professionId bigint      not null,
    name         varchar(60) not null
)
    charset = utf8;

create definer = root@localhost view userview as
select `xhealthcode`.`student`.`userId`   AS `userId`,
       '学生'                               AS `type`,
       `xhealthcode`.`student`.`name`     AS `name`,
       `xhealthcode`.`student`.`idCard`   AS `idCard`,
       `xhealthcode`.`student`.`number`   AS `number`,
       `xhealthcode`.`student`.`xClassId` AS `field`,
       `xhealthcode`.`info`.`result`      AS `result`,
       NULL                               AS `role`,
       NULL                               AS `password`
from `xhealthcode`.`student`
         join `xhealthcode`.`info`
where (`xhealthcode`.`student`.`userId` = `xhealthcode`.`info`.`userId`)
union
select `xhealthcode`.`teacher`.`userId`     AS `userId`,
       '教师'                                 AS `type`,
       `xhealthcode`.`teacher`.`name`       AS `name`,
       `xhealthcode`.`teacher`.`idCard`     AS `idCard`,
       `xhealthcode`.`teacher`.`number`     AS `number`,
       `xhealthcode`.`teacher`.`collegeId`  AS `field`,
       `xhealthcode`.`info`.`result`        AS `result`,
       `xhealthcode`.`adminuser`.`role`     AS `role`,
       `xhealthcode`.`adminuser`.`password` AS `password`
from `xhealthcode`.`teacher`
         join `xhealthcode`.`adminuser`
         join `xhealthcode`.`info`
where ((`xhealthcode`.`teacher`.`teacherId` = `xhealthcode`.`adminuser`.`teacherId`) and
       (`xhealthcode`.`teacher`.`userId` = `xhealthcode`.`info`.`userId`))
union
select `xhealthcode`.`student`.`userId`   AS `userId`,
       '学生'                               AS `type`,
       `xhealthcode`.`student`.`name`     AS `name`,
       `xhealthcode`.`student`.`idCard`   AS `idCard`,
       `xhealthcode`.`student`.`number`   AS `number`,
       `xhealthcode`.`student`.`xClassId` AS `field`,
       NULL                               AS `NULL`,
       NULL                               AS `role`,
       NULL                               AS `password`
from `xhealthcode`.`student`
where exists(select 1
             from `xhealthcode`.`info`
             where (`xhealthcode`.`info`.`userId` = `xhealthcode`.`student`.`userId`)) is false
union
select `xhealthcode`.`teacher`.`userId`     AS `userId`,
       '教师'                                 AS `type`,
       `xhealthcode`.`teacher`.`name`       AS `name`,
       `xhealthcode`.`teacher`.`idCard`     AS `idCard`,
       `xhealthcode`.`teacher`.`number`     AS `number`,
       `xhealthcode`.`teacher`.`collegeId`  AS `field`,
       NULL                                 AS `NULL`,
       `xhealthcode`.`adminuser`.`role`     AS `role`,
       `xhealthcode`.`adminuser`.`password` AS `password`
from `xhealthcode`.`teacher`
         join `xhealthcode`.`adminuser`
where ((`xhealthcode`.`teacher`.`teacherId` = `xhealthcode`.`adminuser`.`teacherId`) and exists(select 1
                                                                                                from `xhealthcode`.`info`
                                                                                                where (`xhealthcode`.`info`.`userId` = `xhealthcode`.`teacher`.`userId`)) is false);

create definer = root@localhost view xclassview as
select `xhealthcode`.`college`.`collegeId`       AS `collegeId`,
       `xhealthcode`.`college`.`name`            AS `collegeName`,
       `xhealthcode`.`profession`.`professionId` AS `professionId`,
       `xhealthcode`.`profession`.`name`         AS `professionName`,
       `xhealthcode`.`xclass`.`xclassId`         AS `xClassid`,
       `xhealthcode`.`xclass`.`name`             AS `xClassName`
from `xhealthcode`.`college`
         join `xhealthcode`.`profession`
         join `xhealthcode`.`xclass`
where ((`xhealthcode`.`college`.`collegeId` = `xhealthcode`.`profession`.`collegeId`) and
       (`xhealthcode`.`profession`.`professionId` = `xhealthcode`.`xclass`.`professionId`))
union
select `xhealthcode`.`college`.`collegeId` AS `collegeId`,
       `xhealthcode`.`college`.`name`      AS `collegeName`,
       NULL                                AS `professionId`,
       NULL                                AS `professionName`,
       NULL                                AS `xClassId`,
       NULL                                AS `xClassName`
from `xhealthcode`.`college`
where exists(select `xhealthcode`.`profession`.`collegeId`
             from `xhealthcode`.`profession`
             where (`xhealthcode`.`college`.`collegeId` = `xhealthcode`.`profession`.`collegeId`)) is false
union
select `xhealthcode`.`college`.`collegeId`       AS `collegeId`,
       `xhealthcode`.`college`.`name`            AS `collegeName`,
       `xhealthcode`.`profession`.`professionId` AS `professionId`,
       `xhealthcode`.`profession`.`name`         AS `professionName`,
       NULL                                      AS `xClassId`,
       NULL                                      AS `xClassName`
from `xhealthcode`.`college`
         join `xhealthcode`.`profession`
where ((`xhealthcode`.`college`.`collegeId` = `xhealthcode`.`profession`.`collegeId`) and
       exists(select `xhealthcode`.`xclass`.`professionId`
              from `xhealthcode`.`xclass`
              where (`xhealthcode`.`xclass`.`professionId` = `xhealthcode`.`profession`.`professionId`)) is false);


