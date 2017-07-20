ALTER TABLE `xiaohailuo_db`.`record` DROP FOREIGN KEY `fk_record_user_id`;

ALTER TABLE `xiaohailuo_db`.`comments` DROP FOREIGN KEY `fk_comments_record_id`;

ALTER TABLE `xiaohailuo_db`.`comments` DROP FOREIGN KEY `fk_comments_user_id`;



DROP INDEX `uni_user_name` ON `xiaohailuo_db`.`user`;

DROP INDEX `uni_comments_ruid` ON `xiaohailuo_db`.`comments`;



DROP TABLE `xiaohailuo_db`.`user` CASCADE;

DROP TABLE `xiaohailuo_db`.`record` CASCADE;

DROP TABLE `xiaohailuo_db`.`comments` CASCADE;



CREATE TABLE `xiaohailuo_db`.`user` (

`id` varchar(36) NOT NULL COMMENT '用户ID',

`name` varchar(32) NOT NULL COMMENT '用户名',

`nickname` varchar(32) NULL COMMENT '昵称',

`profilephoto` varchar(36) NOT NULL COMMENT '头像ID',

`summary` varchar(128) NULL COMMENT '摘要',

`gender` varchar(8) NULL COMMENT '性别',

`subscribetime` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '注册时间',

`qrcode` longblob NULL COMMENT '二维码',

`address` varchar(128) NULL COMMENT '我的地址',

`what's up` varchar(128) NULL COMMENT '个性签名',

`region` varchar(64) NULL COMMENT '国家/地区',

PRIMARY KEY (`id`) ,

UNIQUE INDEX `uni_user_name` (`name` ASC) COMMENT '索引用户名' 

)

COMMENT = '用户信息表';



CREATE TABLE `xiaohailuo_db`.`record` (

`id` varchar(16) NOT NULL COMMENT '录音ID',

`coordinates` tinytext NULL COMMENT '坐标',

`lat` decimal(9,6) NULL COMMENT '经度',

`lng` decimal(9,6) NULL COMMENT '纬度',

`officialflag` char(1) NOT NULL DEFAULT 'N' COMMENT '官方标志',

`uid` varchar(36) NULL COMMENT '录音用户ID',

`summary` varchar(128) NULL COMMENT '摘要',

`date` datetime NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '录音上传时间',

`poi` text NULL COMMENT 'Point of Interest兴趣点',

`citycode` varchar(16) NULL COMMENT '城市代码',

`description` varchar(255) NULL COMMENT '描述',

`url` tinytext NULL COMMENT '路语音URL',

PRIMARY KEY (`id`) 

)

COMMENT = '录音信息表';



CREATE TABLE `xiaohailuo_db`.`comments` (

`id` integer(11) NOT NULL COMMENT '评论ID',

`rid` varchar(16) NULL COMMENT '录音ID',

`uid` varchar(36) NULL COMMENT '评论者ID（不支持匿名）',

`comment` varchar(255) NOT NULL COMMENT '评论内容',

`date` datetime NULL COMMENT '评论时间',

PRIMARY KEY (`id`) ,

UNIQUE INDEX `uni_comments_ruid` (`rid` ASC, `uid` ASC) COMMENT 'rid和uid组成联合索引' 

)

COMMENT = '录音点评录表';





ALTER TABLE `xiaohailuo_db`.`record` ADD CONSTRAINT `fk_record_user_id` FOREIGN KEY (`uid`) REFERENCES `xiaohailuo_db`.`user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE `xiaohailuo_db`.`comments` ADD CONSTRAINT `fk_comments_record_id` FOREIGN KEY (`rid`) REFERENCES `xiaohailuo_db`.`record` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE `xiaohailuo_db`.`comments` ADD CONSTRAINT `fk_comments_user_id` FOREIGN KEY (`uid`) REFERENCES `xiaohailuo_db`.`user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

