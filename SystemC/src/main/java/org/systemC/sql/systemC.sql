-- 创建院系C数据库
CREATE DATABASE systemC;

-- 切换到院系C数据库
USE systemC;

-- 删除院系C数据库中的所有表
DROP TABLE 账户, 学生, 课程, 选课;

-- 创建账户表
CREATE TABLE 账户 (
  acc VARCHAR(12) PRIMARY KEY,
  passwd VARCHAR(12),
  CreateDate TIMESTAMP
);

-- 创建学生表
CREATE TABLE 学生 (
  Sno VARCHAR(9) PRIMARY KEY,
  Snm VARCHAR(10),
  Sex VARCHAR(1),
  Sde VARCHAR(6),
  Pwd CHAR(6)
);

-- 创建课程表
CREATE TABLE 课程 (
  Cno CHAR(4) PRIMARY KEY,
  Cnm VARCHAR(10),
  Ctm INTEGER,
  Cpt INTEGER,
  Tec VARCHAR(20),
  Pla VARCHAR(18),
  Share CHAR(1)
);

-- 创建选课表
CREATE TABLE 选课 (
  Cno CHAR(4),
  Sno CHAR(9),
  Grd INTEGER,
  PRIMARY KEY (Cno, Sno)
);

-- 插入账户数据
INSERT INTO 账户 (acc, passwd, CreateDate)
VALUES ('user1', '123456', '2022-12-12'),
       ('user2', '234567', '2022-11-12'),
       ('user3', '345678', '2022-12-13'),
       ('user4', '456789', '2022-12-14'),
       ('user5', '567890', '2022-12-15');

--  Sno VARCHAR(9) PRIMARY KEY,
--  Snm VARCHAR(10),
--  Sex VARCHAR(1),
--  Sde VARCHAR(6),
--  Pwd CHAR(6)
-- 插入学生数据
INSERT INTO 学生 (Sno, Snm, Sex, Sde, Pwd)
VALUES ('20230001', '小白', '男', '金融工程', '123456'),
       ('20230002', '小红', '男', '金融工程', '234567'),
       ('20230003', '小绿', '女', '金融工程', '345678'),
       ('20230004', '小黄', '男', '金融工程', '456789');

--  Cno CHAR(4) PRIMARY KEY,
--  Cnm VARCHAR(10),
--  Ctm INTEGER,
--  Cpt INTEGER,
--  Tec VARCHAR(20),
--  Pla VARCHAR(18),
--  Share CHAR(1)
-- 插入课程数据
INSERT INTO 课程 (Cno, Cnm, Ctm, Cpt, Tec, Pla, Share)
VALUES ('B001', '宏观经济学', 64, 4, '1老师', 'A101', '1'),
       ('B002', '微观经济学', 48, 3, '2老师', 'B101', '1'),
       ('B003', '政治经济学', 32, 2, '3老师', 'C101', '1');

-- 插入选课数据
INSERT INTO 选课 (Cno, Sno, Grd)
VALUES
('B001', '20230001', 85),
('B002', '20230001', 83),
('B003', '20230001', 88),
('B001', '20230002', 90),
('B002', '20230002', 90),
('B003', '20230002', 90),
('B001', '20230003', 75),
('B003', '20230004', 80);

-- 外院系同学的选课信息
INSERT INTO 选课 (Cno, Sno, Grd)
VALUES
('B001', '20210001', 85)
('B001', '20220002', 100);



