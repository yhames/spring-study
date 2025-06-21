create table `coupon`.`coupon`
(
    `id`                   bigint(20)   not null auto_increment,
    `title`                varchar(255) not null comment '쿠폰명',
    `coupon_type`          varchar(255) not null comment '쿠폰 타입',
    `total_quantity`       int          null comment '쿠폰 발급 최대 수량',
    `issued_quantity`      int          not null comment '발급된 쿠폰 수량',
    `discount_amount`      int          not null comment '할인 금액',
    `min_available_amount` int          not null comment '최소 사용 금액',
    `date_issue_start`     datetime(6)  not null comment '발급 시작 일시',
    `date_issue_end`       datetime(6)  not null comment '발급 종료 일시',
    `date_created`         datetime(6)  not null comment '생성 일시',
    `date_updated`         datetime(6)  not null comment '수정 일시',
    PRIMARY KEY (`id`)
) engine = InnoDB
  default charset = utf8mb4
    comment "쿠폰정책";

create table `coupon`.`coupon_issues`
(
    `id`           bigint(20)  not null auto_increment,
    `coupon_id`    bigint(20)  not null comment '쿠폰 Id',
    `user_id`      bigint(20)  not null comment '사용자 Id',
    `date_issued`  datetime(6) not null comment '발급 일시',
    `date_used`    datetime(6) null comment '사용 일시',
    `date_created` datetime(6) not null comment '생성 일시',
    `date_updated` datetime(6) not null comment '수정 일시',
    PRIMARY KEY (`id`)
) engine = InnoDB
  default charset = utf8mb4
    comment "쿠폰 발급 내역";