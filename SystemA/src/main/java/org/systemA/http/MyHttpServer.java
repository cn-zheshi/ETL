package org.systemA.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.systemA.sql.AConnection;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.systemA.util.xmlWriter.generateCourseInfo;

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
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(5050), 10);
        //将 / 请求交给MyHandler处理器处理
        httpServer.createContext("/test", new MyHandler());
        // 提供本院系的课程信息
        httpServer.createContext("/courses", new MyHandler());
        // 提供本院系的学生信息
        httpServer.createContext("/students", new MyHandler());
        // 根据xml文件的信息，选择本院系的课
        // select?courseId=xxx&studentId=xxx
        httpServer.createContext("/select", new MyHandler());
        // 根据xml文件的信息，退选本院系的课
        // unselect?courseId=xxx&studentId=xxx
        httpServer.createContext("/unselect", new MyHandler());

        // 根据学号查询学生的课程信息
        // /courseChosed?studentNo=xxx
        httpServer.createContext("/courseChosed", new CourseChosedHandler());

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

class CourseChosedHandler implements HttpHandler {

    public static Connection ct = null;
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;

    // 传参
    public static String studentNo = null;

    // 存放查询结果，课程信息
    public static List<String[]> courses = new ArrayList<>();


    public void handle(HttpExchange httpExchange) throws IOException {
        String content = null;
        courses = new ArrayList<>();
        // 连接数据库，查询学生的课程信息
        // 将查询结果转换为xml格式
        ct = AConnection.getConnection();
        studentNo = httpExchange.getRequestURI().getQuery().split("=")[1];
        List<String> temp = new ArrayList<>();
        try {
            ps = ct.prepareStatement("select * from 选课 where 学生编号 = ?");
            ps.setString(1, studentNo);
            rs = ps.executeQuery();
            while (rs.next()) {
                temp.add(rs.getString("课程编号"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // 课程数量
        int count = temp.size();
        // 根据课程号查询课程信息
        try {
            ps = ct.prepareStatement("select * from 课程 where 课程编号 = ?");
            for (int i = 0; i < count; i++) {
                ps.setString(1, temp.get(i));
                rs = ps.executeQuery();
                while (rs.next()) {
                    String[] course = new String[6];
                    course[0] = rs.getString("课程编号");
                    course[1] = rs.getString("课程名称");
                    course[2] = rs.getString("学分");
                    course[3] = rs.getString("授课老师");
                    course[4] = rs.getString("授课地点");
                    course[5] = rs.getString("共享");
                    courses.add(course);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // 将查询结果转换为xml格式
        content = generateCourseInfo(courses);
        System.out.println(content);
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
