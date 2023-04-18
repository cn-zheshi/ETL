--ORACLE数据库
--创建院系B数据库, ORACLE数据库由DCMA手动创建
--CREATE DATABASE systemB;

--切换到数据库systemB
--ALTER SESSION SET CURRENT_SCHEMA=systemB;

--删除数据库B中的所有表
BEGIN
FOR c IN (SELECT table_name FROM user_tables) LOOP
EXECUTE IMMEDIATE ('DROP TABLE ' || c.table_name || ' CASCADE CONSTRAINTS');
END LOOP;
END;

--创建账户表
CREATE TABLE 账户(
  账户名 VARCHAR2(12) PRIMARY KEY,
  密码 VARCHAR2(12),
  级别 NUMBER(2),
  客体 VARCHAR2(9)
);

--创建学生表
CREATE TABLE 学生 (
  学号 VARCHAR2(9) PRIMARY KEY,
  姓名 VARCHAR2(10),
  性别 VARCHAR2(2),
  专业 VARCHAR2(16),
  密码 VARCHAR2(6)
);

--创建课程表
CREATE TABLE 课程 (
  编号 VARCHAR2(5) PRIMARY KEY,
  名称 VARCHAR2(16),
  课时 VARCHAR2(2),
  学分 VARCHAR2(1),
  老师 VARCHAR2(10),
  地点 VARCHAR2(20),
  共享 CHAR(1)
);

--创建选课表
CREATE TABLE 选课 (
  课程编号 VARCHAR2(5),
  学号 VARCHAR2(9),
  得分 VARCHAR2(3),
  CONSTRAINT pk_选课 PRIMARY KEY (课程编号, 学号)
);

--插入账户数据
INSERT ALL
  INTO 账户 (账户名, 密码, 级别, 客体) VALUES ('admin', '123456', 1, 'B')
  INTO 账户 (账户名, 密码, 级别, 客体) VALUES ('user1', '123456', 2, '20220001')
  INTO 账户 (账户名, 密码, 级别, 客体) VALUES ('user3', '123456', 2, '20220003')
  INTO 账户 (账户名, 密码, 级别, 客体) VALUES ('user4', '123456', 2, '20220004')
  INTO 账户 (账户名, 密码, 级别, 客体) VALUES ('user2', '123456', 2, '20220002')
SELECT 1 FROM DUAL;

--插入学生数据
INSERT ALL
  INTO 学生 (学号, 姓名, 性别, 专业, 密码) VALUES ('20220001', 'David', '男', '艺术管理', '123456')
  INTO 学生 (学号, 姓名, 性别, 专业, 密码) VALUES ('20220002', 'Steven', '男', '艺术管理', '123456')
  INTO 学生 (学号, 姓名, 性别, 专业, 密码) VALUES ('20220003', 'Max', '男', '艺术管理', '123456')
  INTO 学生 (学号, 姓名, 性别, 专业, 密码) VALUES ('20220004', 'Lucy', '女', '艺术管理', '123456')
SELECT 1 FROM DUAL;

--插入课程数据
INSERT ALL
  INTO 课程 (编号, 名称, 课时, 学分, 老师, 地点, 共享) VALUES ('A001', '中国艺术史', 3, 3, '周老师', '地点1', '1')
  INTO 课程 (编号, 名称, 课时, 学分, 老师, 地点, 共享) VALUES ('A002', '西方艺术史', 3, 3, '何老师', '地点2', '1')
  INTO 课程 (编号, 名称, 课时, 学分, 老师, 地点, 共享) VALUES ('A003', '美国艺术史', 3, 3, '马老师', '地点3', '1')
SELECT 1 FROM DUAL;

--插入选课数据
INSERT ALL
  INTO 选课 (课程编号, 学号, 得分) VALUES ('A001', '20220001', '90')
  INTO 选课 (课程编号, 学号, 得分) VALUES ('A001', '20220002', '80')
  INTO 选课 (课程编号, 学号, 得分) VALUES ('A001', '20220003', '70')
  INTO 选课 (课程编号, 学号, 得分) VALUES ('A001', '20220004', '60')
  INTO 选课 (课程编号, 学号, 得分) VALUES ('A002', '20220001', '90')
  INTO 选课 (课程编号, 学号, 得分) VALUES ('A002', '20220002', '80')
  INTO 选课 (课程编号, 学号, 得分) VALUES ('A002', '20220003', '70')
  INTO 选课 (课程编号, 学号, 得分) VALUES ('A002', '20220004', '60')
  INTO 选课 (课程编号, 学号, 得分) VALUES ('A003', '20220001', '90')
  INTO 选课 (课程编号, 学号, 得分) VALUES ('A003', '20220002', '80')
  INTO 选课 (课程编号, 学号, 得分) VALUES ('A003', '20220003', '70')
  INTO 选课 (课程编号, 学号, 得分) VALUES ('A003', '20220004', '60')
SELECT 1 FROM DUAL;