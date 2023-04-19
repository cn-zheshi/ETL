package org.systemB.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.systemB.sql.BConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.systemB.util.xmlWriter.generateChoiceInfo;
import static org.systemB.util.xmlWriter.generateCourseInfo;

/**
 * 根据Java提供的API实现Http服务器
 */
public class MyHttpServer {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // 创建HttpServer服务器
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(5051), 10);
        //将 / 请求交给MyHandler处理器处理
        httpServer.createContext("/test", new MyHandler());
        // 提供本院系的学生信息
        httpServer.createContext("/students", new MyHandler());

        // 选择本院系的课程
        httpServer.createContext("/choose", new ChooseHandler());

        // 退选本院系的课
        httpServer.createContext("/unselect", new MyHandler());

        // 查询所有课程信息
        // /course?studentNo=xxx
        httpServer.createContext("/course", new CourseHandler());

        // 根据学号查询选课信息
        // /select?studentNo=xxx
        httpServer.createContext("/select", new ChoiceHandler());

        httpServer.start();
    }
}

class MyHandler implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        String content = "test";
        //设置响应头属性及响应信息的长度
        httpExchange.sendResponseHeaders(200, content.length());
        //获得输出流
        OutputStream os = httpExchange.getResponseBody();
        os.write(content.getBytes());
        os.close();
    }
}

class CourseHandler implements HttpHandler {
    public static Connection ct = null;
    public static Statement ps = null;
    public static ResultSet rs = null;
    public static List<String[]> courses = new ArrayList<>();
    public void handle(HttpExchange httpExchange) throws IOException {
        String content = null;
        courses = new ArrayList<>();
        // 连接数据库，查询学生的课程信息
        ct = BConnection.getConnection();
        try {
            String sql = "select * from 课程";
            java.sql.Statement stmt = ct.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String[] course = new String[7];
                course[0] = rs.getString("编号");
                course[1] = rs.getString("名称");
                course[2] = rs.getString("课时");
                course[3] = rs.getString("学分");
                course[4] = rs.getString("老师");
                course[5] = rs.getString("地点");
                course[6] = rs.getString("共享");
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // 将查询结果转换为xml格式
        content = generateCourseInfo(courses);
        //设置响应头属性及响应信息的长度
        httpExchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
        // 设置utf-8编码
        httpExchange.getResponseHeaders().set("charset", "utf-8");
        //获得输出流
        OutputStream os = httpExchange.getResponseBody();
        os.write(content.getBytes("UTF-8"));
        os.close();
    }
}

class ChoiceHandler implements HttpHandler {
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;

    // 传参
    public static String studentNo = null;

    // 存放查询结果，选课信息
    public static List<String[]> choices = new ArrayList<>();

    public void handle(HttpExchange httpExchange) throws IOException {
        String content = null;
        choices = new ArrayList<>();
        ct = BConnection.getConnection();
        studentNo = httpExchange.getRequestURI().getQuery().split("=")[1];
        try {
            java.sql.Statement stmt = ct.createStatement();
            rs = stmt.executeQuery("select * from 选课 where 学号 = " + studentNo);
            while (rs.next()) {
                String[] choice = new String[3];
                choice[0] = rs.getString("课程编号");
                choice[1] = rs.getString("学号");
                choice[2] = rs.getString("得分");
                choices.add(choice);
            }
        } catch (SQLException e) {
            System.out.println("查询选课信息失败");
            throw new RuntimeException(e);
        }
        // 将查询结果转换为xml格式
        content = generateChoiceInfo(choices);
        //设置响应头属性及响应信息的长度
        httpExchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
        // 设置utf-8编码
        httpExchange.getResponseHeaders().set("charset", "utf-8");
        //获得输出流
        OutputStream os = httpExchange.getResponseBody();
        os.write(content.getBytes("UTF-8"));
        os.close();
    }
}

class ChooseHandler implements HttpHandler {
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;
    public static String studentNo = null;
    public static String courseNo = null;

    public void handle(HttpExchange httpExchange) throws IOException {
        String content = "fail";
        ct = BConnection.getConnection();
        // 解析xml文件
        InputStream is = httpExchange.getRequestBody();
        SAXReader saxReader = new SAXReader();
        Document document = null;
        System.out.println("开始解析xml文件");
        try {
            document = saxReader.read(is);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        Element root = document.getRootElement();
        Element choice = root.element("choice");
        studentNo = choice.element("学号").getText();
        courseNo = choice.element("课程编号").getText();
        try {
            java.sql.Statement stmt = ct.createStatement();
            // 随机生成一个得分
            String score = String.valueOf((int)(Math.random() * 100));
            String sql = "INSERT ALL INTO 选课 (课程编号, 学号, 得分) VALUES\n" +
                    "('" + courseNo + "', '" + studentNo + "', '" + score + "')\n" +
                    "SELECT 1 FROM DUAL";
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            content = "success";
            System.out.println("选课成功");
        } catch (SQLException e) {
            System.out.println("选课失败");
            throw new RuntimeException(e);
        }
        finally {
            //设置响应头属性及响应信息的长度
            httpExchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
            // 设置utf-8编码
            httpExchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
            //获得输出流
            OutputStream os = httpExchange.getResponseBody();
            os.write(content.getBytes("UTF-8"));
            os.close();
        }
    }
}