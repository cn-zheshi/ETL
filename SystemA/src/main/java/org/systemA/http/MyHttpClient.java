package org.systemA.http;
import com.alibaba.fastjson.JSONObject;

import javax.swing.table.DefaultTableModel;
import java.io.UnsupportedEncodingException;

import static org.systemA.util.XMLParser.parseClassesInfo;
import static org.systemA.util.XMLParser.parseCoursesInfo;
import static org.systemA.util.xmlWriter.generateSelectCourseXML;

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

}