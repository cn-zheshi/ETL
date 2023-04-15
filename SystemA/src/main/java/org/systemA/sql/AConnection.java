package org.systemA.sql;

import java.sql.Connection;
import java.sql.DriverManager;

public class AConnection {
    public static Connection getConnection() {
        String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=systemA";
        Connection con = null;
        try {
            //1.加载驱动
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("加载驱动成功！");
            //2.连接
            con = DriverManager.getConnection(dbURL, "sa", "123456");
            System.out.println("连接数据库成功！");
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("连接数据库失败！");
        }
        return con;
    }

    public static void main(String[] args) {
        AConnection.getConnection();
    }
}
