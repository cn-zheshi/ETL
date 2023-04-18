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
    }

    public static void executeBSQL() {
        Connection con = BConnection.getConnection();
        try {
            // conn system/123456@SYSTEMB
            String sql = "select * from 学生";
            java.sql.Statement stmt = con.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString("学号") + " " + rs.getString("姓名"));
            }
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
