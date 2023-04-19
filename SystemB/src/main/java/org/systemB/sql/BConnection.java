package org.systemB.sql;

import java.sql.Connection;
import java.sql.DriverManager;

public class BConnection {
    public static Connection getConnection() {
        //声明连接对象、执行sql对象、结果集对象
        Connection con = null;
        String dbURL = "jdbc:oracle:thin:@localhost:1521:SYSTEMB";//@ip地址:端口号:数据库名
        String name = "system";//用户名
        String pwd = "123456";//密码
        try {
            //1.加载驱动
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("加载驱动成功！");
            //2.连接
            con = DriverManager.getConnection(dbURL, name, pwd);
            System.out.println("连接数据库成功！");
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("连接数据库失败！");
        }
        return con;
    }

    public static void main(String[] args) {
        BConnection.getConnection();
//        executeBSQL();
    }

    public static void executeBSQL() {
        Connection con = BConnection.getConnection();
        try {
            // conn system/123456@SYSTEMB
            String sql = "INSERT ALL\n" +
                    "  INTO 选课 (课程编号, 学号, 得分) VALUES ('A001', '20210001', '90')\n" +
                    "SELECT 1 FROM DUAL";
            java.sql.Statement stmt = con.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        Connection con = BConnection.getConnection();
        try {
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
