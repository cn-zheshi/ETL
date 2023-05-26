package org.systemA.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.systemA.App;
import org.systemA.sql.AConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.systemA.http.MyHttpClient.*;
import static org.systemA.util.xmlWriter.generateChoiceInfo;
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
        // 提供本院系的学生信息
//        httpServer.createContext("/students", new MyHandler());
        // 查询所有课程信息
        httpServer.createContext("/course", new CourseHandler());
        // 根据学号查询选课信息
        httpServer.createContext("/select", new ChoiceHandler());
        // 选择本院系的课程
        httpServer.createContext("/choose", new ChooseHandler());
        // 退选本院系的课
        httpServer.createContext("/drop", new DropHandler());
        // 前后端接口
        // 登录
        httpServer.createContext("/login", new LoginHandler());
        // 个人信息
        httpServer.createContext("/info", new InfoHandler());
        // 课程信息
        httpServer.createContext("/selfCourseInfo", new SelfCourseInfoHandler());
        // 选择本院系课程
        httpServer.createContext("/selfCourseChoose", new SelfCourseChooseHandler());
        // 获取本院系已选课程
        httpServer.createContext("/selfCourseSelected", new SelfCourseSelectedHandler());
        // 退选本院系课程
        httpServer.createContext("/selfCourseDrop", new SelfCourseDropHandler());
        // 获取本院系所有学生信息
        httpServer.createContext("/selfAllStudentInfo", new SelfAllStudentInfoHandler());
        // 获取其他院系的课程
        httpServer.createContext("/otherCourseInfo", new OtherCourseInfoHandler());
        // 选择跨院系课程
        httpServer.createContext("/otherCourseChoose", new OtherCourseChooseHandler());
        // 获取跨院系的已选课程
        httpServer.createContext("/otherCourseSelected", new OtherCourseSelectedHandler());
        // 退选跨院系的课程
        httpServer.createContext("/otherCourseDrop", new OtherCourseDropHandler());
        // 获取其它院系的学生信息
        httpServer.createContext("/otherAllStudentInfo", new OtherAllStudentInfoHandler());
        // 开放本院系课程共享
        httpServer.createContext("/selfCourseShare", new SelfCourseShareHandler());
        // 开放其它院系的课程共享
        httpServer.createContext("/otherCourseShare", new OtherCourseShareHandler());
        // 修改本院系课程分数
        httpServer.createContext("/selfCourseScore", new SelfCourseScoreHandler());
        // 修改其它院系的课程成绩
        httpServer.createContext("/otherCourseScore", new OtherCourseScoreHandler());
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
        ct = AConnection.getConnection();
        List<String> temp = new ArrayList<>();
        try {
            ps = ct.prepareStatement("select * from 课程");
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
        ct = AConnection.getConnection();
        studentNo = httpExchange.getRequestURI().getQuery().split("=")[1];
        try {
            ps = ct.prepareStatement("select * from 选课 where 学生编号 = ?");
            ps.setString(1, studentNo);
            rs = ps.executeQuery();
            while (rs.next()) {
                String[] choice = new String[3];
                choice[0] = rs.getString("课程编号");
                choice[1] = rs.getString("学生编号");
                choice[2] = rs.getString("成绩");
                choices.add(choice);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // 将查询结果转换为xml格式
        content = generateChoiceInfo(choices);
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

class ChooseHandler implements HttpHandler {
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;
    public static String studentNo = null;
    public static String courseNo = null;

    public void handle(HttpExchange httpExchange) throws IOException {
        String content = "fail";
        ct = AConnection.getConnection();
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
        System.out.println(document);
        Element root = document.getRootElement();
        Element choice = root.element("choice");
        courseNo = choice.element("课程编号").getText();
        studentNo = choice.element("学号").getText();
        System.out.println(courseNo + " " + studentNo);
        try {
            java.sql.Statement stmt = ct.createStatement();
            // 随机生成一个得分
            String score = String.valueOf((int)(Math.random() * 100));
            String sql = "INSERT INTO 选课 (课程编号, 学生编号, 成绩) VALUES ('" + courseNo + "', '" + studentNo + "', '" + score + "')";
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
        ct = AConnection.getConnection();
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
        courseNo = choice.element("课程编号").getText();
        studentNo = choice.element("学号").getText();
        try {
            java.sql.Statement stmt = ct.createStatement();
            String sql = "DELETE FROM 选课 WHERE 课程编号 = '" + courseNo + "' AND 学生编号 = '" + studentNo + "'";
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

// 前后端接口
// 登录
class LoginHandler implements HttpHandler {
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    public static String username = null;
    public static String password = null;
    public static String role = "stud";
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ct = AConnection.getConnection();
        String url = exchange.getRequestURI().toString();
        String[] params = url.split("\\?");
        String[] param = params[1].split("&");
        username = param[0].split("=")[1];
        password = param[1].split("=")[1];
        role = param[2].split("=")[1];
        String content = "fail";
        try {
            ps = ct.prepareStatement("select * from 账户 where 权限=? and 账户名=?");
            ps.setString(1, role);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String user_name = rs.getString("账户名");
                String pass_word = rs.getString("密码");
                if (user_name.equals(username) && pass_word.equals(password)) {
                    content = "success";
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // 允许所有来源的请求
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"); // 支持的请求方法
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type,Authorization"); // 允许的请求头字段
            exchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
            exchange.getResponseHeaders().set("charset", "utf-8");
            OutputStream os = exchange.getResponseBody();
            os.write(content.getBytes("UTF-8"));
            os.close();
        }
    }
}

// 个人信息
class InfoHandler implements HttpHandler {
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    public static String username = null;
    public static String role = "stud";
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ct = AConnection.getConnection();
        String url = exchange.getRequestURI().toString();
        String[] params = url.split("\\?");
        String[] param = params[1].split("&");
        username = param[0].split("=")[1];
        role = param[1].split("=")[1];
        String content = "fail";
        if(role.equals("stud")) {
            try {
                ps = ct.prepareStatement("select * from 学生 where 关联账户 = ?");
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    // 学号、姓名、性别、院系、关联账户
                    String studentNo = rs.getString("学号");
                    String studentName = rs.getString("姓名");
                    String studentGender = rs.getString("性别");
                    String studentDepartment = rs.getString("院系");
                    String studentAccount = rs.getString("关联账户");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("studentNo", studentNo);
                    jsonObject.put("studentName", studentName);
                    jsonObject.put("studentGender", studentGender);
                    jsonObject.put("studentDepartment", studentDepartment);
                    jsonObject.put("studentAccount", studentAccount);
                    content = jsonObject.toString();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // 允许所有来源的请求
                exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"); // 支持的请求方法
                exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type,Authorization"); // 允许的请求头字段
                exchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
                exchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");
                OutputStream os = exchange.getResponseBody();
                os.write(content.getBytes("UTF-8"));
                os.close();
            }
        }
    }
}

// 本院系课程信息
class SelfCourseInfoHandler implements HttpHandler {
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ct = AConnection.getConnection();
        String content = "fail";
        try {
            ps = ct.prepareStatement("select * from 课程");
            ResultSet rs = ps.executeQuery();
            JSONArray jsonArray = new JSONArray();
            while (rs.next()) {
                // 课程编号、课程名称、学分、授课老师、授课地点、共享
                String courseNo = rs.getString("课程编号");
                String courseName = rs.getString("课程名称");
                String courseCredit = rs.getString("学分");
                String courseTeacher = rs.getString("授课老师");
                String courseLocation = rs.getString("授课地点");
                String courseShare = rs.getString("共享");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("courseNo", courseNo);
                jsonObject.put("courseName", courseName);
                jsonObject.put("courseCredit", courseCredit);
                jsonObject.put("courseTeacher", courseTeacher);
                jsonObject.put("courseLocation", courseLocation);
                jsonObject.put("courseShare", courseShare);
                jsonArray.add(jsonObject);
                content = jsonArray.toString();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // 允许所有来源的请求
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"); // 支持的请求方法
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type,Authorization"); // 允许的请求头字段
            exchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
            exchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");
            OutputStream os = exchange.getResponseBody();
            os.write(content.getBytes("UTF-8"));
            os.close();
        }
    }
}

// 选择本院系课程
class SelfCourseChooseHandler implements HttpHandler {
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ct = AConnection.getConnection();
        String url = exchange.getRequestURI().toString();
        String[] params = url.split("\\?");
        String[] param = params[1].split("&");
        String courseNo = param[0].split("=")[1];
        String studentNo = param[1].split("=")[1];
        String content = "fail";
        try {
            ps = ct.prepareStatement("select * from 选课 where 课程编号 = ? and 学生编号 = ?");
            ps.setString(1, courseNo);
            ps.setString(2, studentNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            } else {
                String sql = "insert into 选课 values(?, ?, ?)";
                ps = ct.prepareStatement(sql);
                ps.setString(1, courseNo);
                ps.setString(2, studentNo);
                ps.setString(3, "0");
                ps.executeUpdate();
                content = "success";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // 允许所有来源的请求
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"); // 支持的请求方法
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type,Authorization"); // 允许的请求头字段
            exchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
            exchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");
            OutputStream os = exchange.getResponseBody();
            os.write(content.getBytes("UTF-8"));
            os.close();
        }
    }
}

// 获取本院系已选课程
class SelfCourseSelectedHandler implements HttpHandler {
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ct = AConnection.getConnection();
        String url = exchange.getRequestURI().toString();
        String[] params = url.split("\\?");
        String studentNo = params[1].split("=")[1];
        String content = "fail";
        try {
            ps = ct.prepareStatement("select * from 选课 where 学生编号 = ?");
            ps.setString(1, studentNo);
            ResultSet rs = ps.executeQuery();
            JSONArray jsonArray = new JSONArray();
            while (rs.next()) {
                // 课程编号、学生编号、成绩
                String courseNo = rs.getString("课程编号");
                String courseGrade = rs.getString("成绩");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("courseNo", courseNo);
                jsonObject.put("courseGrade", courseGrade);
                // 获取课程信息
                PreparedStatement ps1 = ct.prepareStatement("select * from 课程 where 课程编号 = ?");
                ps1.setString(1, courseNo);
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    // 课程编号、课程名称、学分、授课老师、授课地点、共享
                    String courseName = rs1.getString("课程名称");
                    String courseCredit = rs1.getString("学分");
                    String courseTeacher = rs1.getString("授课老师");
                    String courseLocation = rs1.getString("授课地点");
                    jsonObject.put("courseName", courseName);
                    jsonObject.put("courseCredit", courseCredit);
                    jsonObject.put("courseTeacher", courseTeacher);
                    jsonObject.put("courseLocation", courseLocation);
                }
                jsonArray.add(jsonObject);
                content = jsonArray.toString();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // 允许所有来源的请求
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"); // 支持的请求方法
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type,Authorization"); // 允许的请求头字段
            exchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
            exchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");
            OutputStream os = exchange.getResponseBody();
            os.write(content.getBytes("UTF-8"));
            os.close();
        }
    }
}

// 退选本院系课程
class SelfCourseDropHandler implements HttpHandler {
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ct = AConnection.getConnection();
        String url = exchange.getRequestURI().toString();
        String[] params = url.split("\\?");
        String[] param = params[1].split("&");
        String courseNo = param[0].split("=")[1];
        String studentNo = param[1].split("=")[1];
        String content = "fail";
        try {
            ps = ct.prepareStatement("select * from 选课 where 课程编号 = ? and 学生编号 = ?");
            ps.setString(1, courseNo);
            ps.setString(2, studentNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String sql = "delete from 选课 where 课程编号 = ? and 学生编号 = ?";
                ps = ct.prepareStatement(sql);
                ps.setString(1, courseNo);
                ps.setString(2, studentNo);
                ps.executeUpdate();
                content = "success";
            } else {
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // 允许所有来源的请求
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"); // 支持的请求方法
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type,Authorization"); // 允许的请求头字段
            exchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
            exchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");
            OutputStream os = exchange.getResponseBody();
            os.write(content.getBytes("UTF-8"));
            os.close();
        }
    }
}

// 获取本院系所有学生信息
class SelfAllStudentInfoHandler implements HttpHandler {
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ct = AConnection.getConnection();
        String content = "fail";
        try {
            ps = ct.prepareStatement("select * from 学生");
            ResultSet rs = ps.executeQuery();
            JSONArray jsonArray = new JSONArray();
            while (rs.next()) {
                // 学号, 姓名, 性别, 院系, 关联账户
                String studentNo = rs.getString("学号");
                String studentName = rs.getString("姓名");
                String studentSex = rs.getString("性别");
                String studentDepartment = rs.getString("院系");
                String studentAccount = rs.getString("关联账户");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("studentNo", studentNo);
                jsonObject.put("studentName", studentName);
                jsonObject.put("studentSex", studentSex);
                jsonObject.put("studentDepartment", studentDepartment);
                jsonObject.put("studentAccount", studentAccount);
                jsonArray.add(jsonObject);
                content = jsonArray.toString();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }  finally {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // 允许所有来源的请求
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"); // 支持的请求方法
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type,Authorization"); // 允许的请求头字段
            exchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
            exchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");
            OutputStream os = exchange.getResponseBody();
            os.write(content.getBytes("UTF-8"));
            os.close();
        }
    }
}

// 获取其他院系的课程信息
class OtherCourseInfoHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String url = exchange.getRequestURI().toString();
        String[] params = url.split("\\?");
        String[] param = params[1].split("&");
        String from = param[0].split("=")[1];
        String to = param[1].split("=")[1];
        String studentNo = param[2].split("=")[1];
        DefaultTableModel model = getAllCourses(from, to, studentNo);
        String content = "fail";
        // 将model转为json
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < model.getRowCount(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("courseNo", model.getValueAt(i, 0));
            jsonObject.put("courseName", model.getValueAt(i, 1));
            jsonObject.put("courseCredit", model.getValueAt(i, 2));
            jsonObject.put("courseTeacher", model.getValueAt(i, 3));
            jsonObject.put("courseLocation", model.getValueAt(i, 4));
            jsonObject.put("courseShare", model.getValueAt(i, 5));
            jsonArray.add(jsonObject);
            content = jsonArray.toString();
        }
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // 允许所有来源的请求
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"); // 支持的请求方法
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type,Authorization"); // 允许的请求头字段
        exchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
        exchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");
        OutputStream os = exchange.getResponseBody();
        os.write(content.getBytes("UTF-8"));
        os.close();
    }
}

// 选择跨院系课程
class OtherCourseChooseHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String url = exchange.getRequestURI().toString();
        String[] params = url.split("\\?");
        String[] param = params[1].split("&");
        String from = param[0].split("=")[1];
        String to = param[1].split("=")[1];
        String studentNo = param[2].split("=")[1];
        String courseNo = param[3].split("=")[1];
        String content = selectCourse(from, to, studentNo, courseNo);

        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // 允许所有来源的请求
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"); // 支持的请求方法
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type,Authorization"); // 允许的请求头字段
        exchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
        exchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");
        OutputStream os = exchange.getResponseBody();
        os.write(content.getBytes("UTF-8"));
        os.close();
    }
}

// 获取跨院系的选课信息
class OtherCourseSelectedHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String url = exchange.getRequestURI().toString();
        String[] params = url.split("\\?");
        String[] param = params[1].split("&");
        String from = param[0].split("=")[1];
        String to = param[1].split("=")[1];
        String studentNo = param[2].split("=")[1];
        DefaultTableModel model = getChoiceCourses(from, to, studentNo);
        // 将model转为json
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < model.getRowCount(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("courseNo", model.getValueAt(i, 0));
            jsonObject.put("courseName", model.getValueAt(i, 1));
            jsonObject.put("courseCredit", model.getValueAt(i, 2));
            jsonObject.put("courseTeacher", model.getValueAt(i, 3));
            jsonObject.put("courseLocation", model.getValueAt(i, 4));
            jsonObject.put("courseGrade", model.getValueAt(i, 5));
            jsonArray.add(jsonObject);
        }
        String content = jsonArray.toString();
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // 允许所有来源的请求
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"); // 支持的请求方法
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type,Authorization"); // 允许的请求头字段
        exchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
        exchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");
        OutputStream os = exchange.getResponseBody();
        os.write(content.getBytes("UTF-8"));
        os.close();
    }
}

// 退选跨院系的课程
class OtherCourseDropHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String url = exchange.getRequestURI().toString();
        String[] params = url.split("\\?");
        String[] param = params[1].split("&");
        String from = param[0].split("=")[1];
        String to = param[1].split("=")[1];
        String studentNo = param[2].split("=")[1];
        String courseNo = param[3].split("=")[1];
        String content = unselectCourse(from, to, studentNo, courseNo);

        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // 允许所有来源的请求
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"); // 支持的请求方法
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type,Authorization"); // 允许的请求头字段
        exchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
        exchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");
        OutputStream os = exchange.getResponseBody();
        os.write(content.getBytes("UTF-8"));
        os.close();
    }
}

// 获取其它院系的所有学生信息
class OtherAllStudentInfoHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String url = exchange.getRequestURI().toString();
        String[] params = url.split("\\?");
        String[] param = params[1].split("&");
        String from = param[0].split("=")[1];
        String to = param[1].split("=")[1];
        DefaultTableModel model = getStudentInfo(from, to);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < model.getRowCount(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("studentNo", model.getValueAt(i, 0));
            jsonObject.put("studentName", model.getValueAt(i, 1));
            jsonObject.put("studentSex", model.getValueAt(i, 2));
            jsonObject.put("studentDepartment", model.getValueAt(i, 3));
            jsonArray.add(jsonObject);
        }
        String content = jsonArray.toString();
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // 允许所有来源的请求
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"); // 支持的请求方法
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type,Authorization"); // 允许的请求头字段
        exchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
        exchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");
        OutputStream os = exchange.getResponseBody();
        os.write(content.getBytes("UTF-8"));
        os.close();
    }
}

// 开放本院系课程共享
class SelfCourseShareHandler implements HttpHandler {
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ct = AConnection.getConnection();
        String url = exchange.getRequestURI().toString();
        String[] params = url.split("\\?");
        String[] param = params[1].split("&");
        String courseNo = param[0].split("=")[1];
        String content = "fail";
        try {
            ps = ct.prepareStatement("select * from 课程 where 课程编号 = ?");
            ps.setString(1, courseNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String share = rs.getString("共享");
                if (share.equals("0")) {
                    share = "1";
                } else {
                    share = "0";
                }
                ps = ct.prepareStatement("update 课程 set 共享 = ? where 课程编号 = ?");
                ps.setString(1, share);
                ps.setString(2, courseNo);
                ps.executeUpdate();
                content = "success";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // 允许所有来源的请求
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"); // 支持的请求方法
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type,Authorization"); // 允许的请求头字段
            exchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
            exchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");
            OutputStream os = exchange.getResponseBody();
            os.write(content.getBytes("UTF-8"));
            os.close();
        }

    }
}

// 开放外院系课程共享
class OtherCourseShareHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String url = exchange.getRequestURI().toString();
        String[] params = url.split("\\?");
        String[] param = params[1].split("&");
        String from = param[0].split("=")[1];
        String to = param[1].split("=")[1];
        String courseNo = param[2].split("=")[1];
        String content = openShareCourse(from, to, courseNo);

        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // 允许所有来源的请求
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"); // 支持的请求方法
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type,Authorization"); // 允许的请求头字段
        exchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
        exchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");
        OutputStream os = exchange.getResponseBody();
        os.write(content.getBytes("UTF-8"));
        os.close();
    }

}

// 修改本院系课程分数
class SelfCourseScoreHandler implements HttpHandler {
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ct = AConnection.getConnection();
        String url = exchange.getRequestURI().toString();
        String[] params = url.split("\\?");
        String[] param = params[1].split("&");
        String courseNo = param[0].split("=")[1];
        String studentNo = param[1].split("=")[1];
        String score = param[2].split("=")[1];
        String content = "fail";
        try {
            ps = ct.prepareStatement("select * from 选课 where 学生编号 = ? and 课程编号 = ?");
            ps.setString(1, studentNo);
            ps.setString(2, courseNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps = ct.prepareStatement("update 选课 set 成绩 = ? where 学生编号 = ? and 课程编号 = ?");
                ps.setString(1, score);
                ps.setString(2, studentNo);
                ps.setString(3, courseNo);
                ps.executeUpdate();
                content = "success";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // 允许所有来源的请求
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"); // 支持的请求方法
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type,Authorization"); // 允许的请求头字段
            exchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
            exchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");
            OutputStream os = exchange.getResponseBody();
            os.write(content.getBytes("UTF-8"));
            os.close();
        }

    }
}

// 修改其它院系的课程成绩
class OtherCourseScoreHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String url = exchange.getRequestURI().toString();
        String[] params = url.split("\\?");
        String[] param = params[1].split("&");
        String from = param[0].split("=")[1];
        String to = param[1].split("=")[1];
        String courseNo = param[2].split("=")[1];
        String studentNo = param[3].split("=")[1];
        String score = param[4].split("=")[1];
        String content = updateScore(from, to, courseNo, studentNo, score);

        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // 允许所有来源的请求
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"); // 支持的请求方法
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type,Authorization"); // 允许的请求头字段
        exchange.sendResponseHeaders(200, content.getBytes("UTF-8").length);
        exchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");
        OutputStream os = exchange.getResponseBody();
        os.write(content.getBytes("UTF-8"));
        os.close();
    }
}