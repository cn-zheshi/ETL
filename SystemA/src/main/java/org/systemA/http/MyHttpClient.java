package org.systemA.http;
import com.alibaba.fastjson.JSONObject;

import javax.swing.table.DefaultTableModel;
import java.io.UnsupportedEncodingException;

import static org.systemA.util.XMLParser.*;
import static org.systemA.util.xmlWriter.generateSelectCourseXML;
import static org.systemA.util.xmlWriter.generateUpdateScoreXML;

public class MyHttpClient {

    public static String baseUrl = "http://localhost:8080";
    private static final String basePath = "src/main/java/schema/";
    // 获取其它系统的课程信息
    public static DefaultTableModel getAllCourses(String from, String to, String studentNo) {
        String coursesUrl = null;
        coursesUrl = baseUrl + "/getAllCourses?from=" + from + "&to=" + to + "&studentNo=" + studentNo;
        String charset = "utf-8";
        String coursesResponse = HttpClientUtil.doGet(coursesUrl, charset);
        DefaultTableModel model = parseCoursesInfo(coursesResponse);
        return model;
    }
    // 选择其它系统的课
    public static String selectCourse(String from, String to, String studentNo, String courseNo) throws UnsupportedEncodingException {
        String url = baseUrl + "/selectCourse?from=" + from + "&to=" + to;
        // 生成选课的xml文件
        String xml = generateSelectCourseXML(studentNo, courseNo);
        String charset = "utf-8";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("xml", xml);
        // 把xml作为RequestBody发送给其它系统
        String response = HttpClientUtil.doPost(url,jsonObject);
        return response;
    }
    // 退选其它系统的课
    public static String unselectCourse(String from, String to, String studentNo, String courseNo) throws UnsupportedEncodingException {
        String url = baseUrl + "/unselectCourse?from=" + from + "&to=" + to;
        // 生成选课的xml文件
        String xml = generateSelectCourseXML(studentNo, courseNo);
        String charset = "utf-8";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("xml", xml);
        // 把xml作为RequestBody发送给其它系统
        String response = HttpClientUtil.doPost(url,jsonObject);
        return response;
    }
    // 获取其他系统的选课信息
    public static DefaultTableModel getChoiceCourses(String from, String to, String studentNo) {
        String coursesUrl;
        String choiceUrl;
        coursesUrl = baseUrl + "/getAllCourses?from=" + from + "&to=" + to + "&studentNo=" + studentNo;
        choiceUrl = baseUrl + "/getAllChoices?from=" + from + "&to=" + to + "&studentNo=" + studentNo;
        String charset = "utf-8";
        String coursesResponse = HttpClientUtil.doGet(coursesUrl, charset);
        String choicesResponse = HttpClientUtil.doGet(choiceUrl, charset);
        DefaultTableModel model = parseClassesInfo(coursesResponse, choicesResponse);
        return model;
    }
    // 获取其它系统的学生信息
    public static DefaultTableModel getStudentInfo(String from, String to) {
        String studentUrl = null;
        studentUrl = baseUrl + "/getAllStudents?from=" + from + "&to=" + to;
        String charset = "utf-8";
        String studentResponse = HttpClientUtil.doGet(studentUrl, charset);
        DefaultTableModel model = parseStudentInfo(studentResponse);
        return model;
    }
    // 开放其它系统的课程共享
    public static String openShareCourse(String from, String to, String courseNo) {
        String url = baseUrl + "/openShareCourse?from=" + from + "&to=" + to + "&courseNo=" + courseNo;
        String charset = "utf-8";
        String response = HttpClientUtil.doGet(url, charset);
        return response;
    }
    // 修改其它院系的课程成绩
    public static String updateScore(String from, String to, String studentNo, String courseNo, String score) throws UnsupportedEncodingException {
        String url = baseUrl + "/updateScore?from=" + from + "&to=" + to + "&score=" + score;
        // 生成选课的xml文件
        String xml = generateUpdateScoreXML(studentNo, courseNo);
        String charset = "utf-8";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("xml", xml);
        // 把xml作为RequestBody发送给其它系统
        String response = HttpClientUtil.doPost(url,jsonObject);
        return response;
    }
}