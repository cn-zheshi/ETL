--use master
--go
--IF EXISTS(SELECT * FROM sysdatabases WHERE name = 'systemA')
--alter database systemA set single_user with rollback immediate
--IF EXISTS(SELECT * FROM sysdatabases WHERE name = 'systemA')
--drop database systemA

-- 创建院系A数据库
IF NOT EXISTS(SELECT name FROM master.dbo.sysdatabases WHERE name = 'systemA')
CREATE DATABASE systemA;

-- 切换到院系A数据库
USE systemA;

-- 删除院系A数据库中的所有表
IF EXISTS(SELECT * FROM sysobjects WHERE xtype='U')
DROP TABLE 账户, 学生, 课程, 选课;

-- 创建账户表
IF NOT EXISTS(SELECT * FROM sysobjects WHERE name='账户' AND xtype='U')
CREATE TABLE 账户 (
  账户名 VARCHAR(10) PRIMARY KEY,
  密码 VARCHAR(6),
  权限 CHAR(4)
);

-- 创建学生表
IF NOT EXISTS(SELECT * FROM sysobjects WHERE name='学生' AND xtype='U')
CREATE TABLE 学生 (
  学号 VARCHAR(12) PRIMARY KEY,
  姓名 VARCHAR(10),
  性别 VARCHAR(2),
  院系 VARCHAR(10),
  关联账户 VARCHAR(10) FOREIGN KEY REFERENCES 账户(账户名)
);

-- 创建课程表
IF NOT EXISTS(SELECT * FROM sysobjects WHERE name='课程' AND xtype='U')
CREATE TABLE 课程 (
  课程编号 VARCHAR(8) PRIMARY KEY,
  课程名称 VARCHAR(10),
  学分 VARCHAR(2),
  授课老师 VARCHAR(10),
  授课地点 VARCHAR(20),
  共享 CHAR(1)
);

-- 创建选课表
IF NOT EXISTS(SELECT * FROM sysobjects WHERE name='选课' AND xtype='U')
CREATE TABLE 选课 (
  课程编号 VARCHAR(8),
  学生编号 VARCHAR(12),
  成绩 VARCHAR(3),
  CONSTRAINT PK_选课 PRIMARY KEY (课程编号, 学生编号),
  CONSTRAINT FK_选课_课程 FOREIGN KEY (课程编号) REFERENCES 课程(课程编号),
  CONSTRAINT FK_选课_学生 FOREIGN KEY (学生编号) REFERENCES 学生(学号)
);

-- 插入账户数据
INSERT INTO 账户 (账户名, 密码, 权限)
VALUES ('user1', '123456', 'admi'),
       ('user2', '123456', 'stud'),
       ('user3', '123456', 'stud'),
       ('user4', '123456', 'stud'),
       ('user5', '123456', 'stud');

-- 插入学生数据
INSERT INTO 学生 (学号, 姓名, 性别, 院系, 关联账户)
VALUES ('20210001', '张三', '男', '计算机', 'user3'),
       ('20210002', '李四', '男', '信息管理', 'user4'),
       ('20210003', '王五', '女', '数学', 'user5');
       ('20210004', '赵六', '男', '计算机', 'user2')

-- 插入课程数据
INSERT INTO 课程 (课程编号, 课程名称, 学分, 授课老师, 授课地点, 共享)
VALUES ('C001', '计组', '3', '任老师', 'A101', '0'),
       ('C002', '数据结构', '4', '刘老师', 'B201', '1'),
       ('C003', '数据库', '3', '刘老师', 'C301', '0');

-- 插入选课数据
INSERT INTO 选课 (课程编号, 学生编号, 成绩)
VALUES ('C001', '20210001', '88'),
       ('C002', '20210001', '95'),
       ('C003', '20210001', '76'),
       ('C002', '20210002', '90'),
       ('C003', '20210002', '85'),
       ('C002', '20210003', '92');
       ('C003', '20210004', '80');




