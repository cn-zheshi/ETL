package org.systemA.http;

import javax.swing.table.DefaultTableModel;

import static org.systemA.util.XMLParser.parseClassesInfo;
import static org.systemA.util.XMLParser.parseCoursesInfo;

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