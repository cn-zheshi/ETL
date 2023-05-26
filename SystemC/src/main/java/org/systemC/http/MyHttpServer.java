package org.systemC.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.systemC.sql.CConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.systemC.util.xmlWriter.generateChoiceInfo;
import static org.systemC.util.xmlWriter.generateCourseInfo;
import static org.systemC.util.xmlWriter.generateStudentInfo;

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
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(5052), 10);
        // 提供本院系的学生信息
        httpServer.createContext("/student", new StudentHandler());

        // 查询所有课程信息
        // /course?studentNo=xxx
        httpServer.createContext("/course", new CourseHandler());

        // 根据学号查询选课信息
        // /select?studentNo=xxx
        httpServer.createContext("/select", new ChoiceHandler());

        // 选择本院系的课程
        httpServer.createContext("/choose", new ChooseHandler());

        // 退选本院系的课
        httpServer.createContext("/drop", new DropHandler());

        // 开放选课
        httpServer.createContext("/openCourseShare", new OpenCourseShareHandler());

        // 修改分数
        httpServer.createContext("/updateScore", new UpdateScoreHandler());

        httpServer.start();
    }
}

class CourseHandler implements HttpHandler {

    public static Connection ct = null;
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;


    // 存放查询结果，课程信息
    public static List<String[]> courses = new ArrayList<>();


    public void handle(HttpExchange httpExchange) throws IOException {
        String content = null;
        courses = new ArrayList<>();
        // 连接数据库，查询学生的课程信息
        ct = CConnection.getConnection();
        List<String> temp = new ArrayList<>();
        try {
            ps = ct.prepareStatement("select * from 课程");
            rs = ps.executeQuery();
            while (rs.next()) {
                String[] course = new String[7];
                course[0] = rs.getString("Cno");
                course[1] = rs.getString("Cnm");
                course[2] = rs.getString("Ctm");
                course[3] = rs.getString("Cpt");
                course[4] = rs.getString("Tec");
                course[5] = rs.getString("Pla");
                course[6] = rs.getString("Share");
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
        ct = CConnection.getConnection();
        studentNo = httpExchange.getRequestURI().getQuery().split("=")[1];
        try {
            ps = ct.prepareStatement("select * from 选课 where Sno = ?");
            ps.setString(1, studentNo);
            rs = ps.executeQuery();
            while (rs.next()) {
                String[] choice = new String[3];
                choice[0] = rs.getString("Cno");
                choice[1] = rs.getString("Sno");
                choice[2] = rs.getString("Grd");
                choices.add(choice);
            }
        } catch (SQLException e) {
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
        ct = CConnection.getConnection();
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
        courseNo = choice.element("Cno").getText();
        studentNo = choice.element("Sno").getText();
        try {
            java.sql.Statement stmt = ct.createStatement();
            // 随机生成一个得分
            String score = "0";
            String sql = "INSERT INTO 选课 (Cno, Sno, Grd)\n" +
                    "VALUES ('" + courseNo + "', '" + studentNo + "', '" + score + "')";
            int rows = stmt.executeUpdate(sql);
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

class DropHandler implements HttpHandler {
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;
    public static String studentNo = null;
    public static String courseNo = null;

    public void handle(HttpExchange httpExchange) throws IOException {
        String content = "fail";
        ct = CConnection.getConnection();
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
        courseNo = choice.element("Cno").getText();
        studentNo = choice.element("Sno").getText();
        try {
            java.sql.Statement stmt = ct.createStatement();
            String sql = "DELETE FROM 选课 WHERE Cno = '" + courseNo + "' AND Sno = '" + studentNo + "'";
            int rows = stmt.executeUpdate(sql);
            content = "success";
            System.out.println("退课成功");
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("退课失败");
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

class StudentHandler implements HttpHandler {
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;
    public void handle(HttpExchange httpExchange) throws IOException {
        String content = null;
        ct = CConnection.getConnection();
        try {
            ps = ct.prepareStatement("select * from 学生");
            rs = ps.executeQuery();
            List<String[]> students = new ArrayList<>();
            while (rs.next()) {
                String[] student = new String[4];
                // Sno, Snm, Sex, Sde
                student[0] = rs.getString("Sno");
                student[1] = rs.getString("Snm");
                student[2] = rs.getString("Sex");
                student[3] = rs.getString("Sde");
                students.add(student);
            }
            // 将查询结果转换为xml格式
            content = generateStudentInfo(students);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

class OpenCourseShareHandler implements HttpHandler {
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ct = CConnection.getConnection();
        String url = exchange.getRequestURI().toString();
        String[] params = url.split("\\?");
        String[] param = params[1].split("&");
        String courseNo = param[0].split("=")[1];
        String content = "fail";
        try {
            // 获取课程的share的值
            ps = ct.prepareStatement("select Share from 课程 where Cno = ?");
            ps.setString(1, courseNo);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String share = rs.getString("Share");
            if (share.equals("0")) {
                share = "1";
            } else {
                share = "0";
            }
            // 更新课程的share的值
            ps = ct.prepareStatement("update 课程 set Share = ? where Cno = ?");
            ps.setString(1, share);
            ps.setString(2, courseNo);
            int rows = ps.executeUpdate();
            content = "success";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            exchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
            OutputStream os = exchange.getResponseBody();
            os.write(content.getBytes("UTF-8"));
            os.close();
        }
        }
    }

class UpdateScoreHandler implements HttpHandler {
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;
    public static String studentNo = null;
    public static String courseNo = null;

    public void handle(HttpExchange httpExchange) throws IOException {
        String content = "fail";
        ct = CConnection.getConnection();
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
        courseNo = choice.element("Cno").getText();
        studentNo = choice.element("Sno").getText();
        // 从url中获取score
        String url = httpExchange.getRequestURI().toString();
        String[] params = url.split("\\?");
        String[] param = params[1].split("&");
        String score = param[0].split("=")[1];
        System.out.println(score);
        try {
            String sql = "UPDATE 选课 SET Grd = ? WHERE Sno = ? AND Cno = ?";
            ps = ct.prepareStatement(sql);
            ps.setString(1, score);
            ps.setString(2, courseNo);
            ps.setString(3, studentNo);
            ps.executeUpdate();
            content = "success";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            httpExchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
            httpExchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
            OutputStream os = httpExchange.getResponseBody();
            os.write(content.getBytes("UTF-8"));
            os.close();
        }
    }
}