package org.systemC.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CConnection {
    public static Connection getConnection() {
        String dbURL = "jdbc:mysql://localhost:3306/systemC?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
        Connection con = null;
        try {
            //1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("加载驱动成功！");
            //2.连接
            con = DriverManager.getConnection(dbURL, "root", "123456");
            System.out.println("连接数据库成功！");

        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("连接数据库失败！");
        }
        return con;
    }

    public static void main(String[] args) {
        CConnection.getConnection();
    }

    public static void closeConnection() {
        Connection con = CConnection.getConnection();
        try {
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
