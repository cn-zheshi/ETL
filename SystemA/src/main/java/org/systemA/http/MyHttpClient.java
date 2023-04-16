package org.systemA.http;

import javax.swing.table.DefaultTableModel;

import static org.systemA.util.XMLParser.parseClassesInfo;

public class MyHttpClient {

    public static String baseUrl = "http://localhost:8080";
    private static final String basePath = "src/main/java/schema/";

    // TODO: 请求接口
    // 获取其它系统的课程信息
    public static String getCourseInfo(String department) {
        String url = null;
        if (department.equals("B")) {
            url = "http://localhost:8080/getCourseInfoB";
        } else if (department.equals("C")) {
            url = "http://localhost:8080/getCourseInfoC";
        }
        String charset = "utf-8";
        String response = HttpClientUtil.doGet(url, charset);
        // TODO: 验证和解析xml文件
        return response;
    }
    // 选择其它系统的课
    // 退选其它系统的课

    // 获取其他系统的选课信息
    // /getAllChoices?from=A&to=B&studentNo=201800000000
    public static DefaultTableModel getChoiceCourses(String from, String to, String studentNo) {
        String coursesUrl = null;
        String choiceUrl = null;
        coursesUrl = baseUrl + "/getAllCourses?from=" + from + "&to=" + to + "&studentNo=" + studentNo;
        choiceUrl = baseUrl + "/getAllChoices?from=" + from + "&to=" + to + "&studentNo=" + studentNo;
        String charset = "utf-8";
        String coursesResponse = HttpClientUtil.doGet(coursesUrl, charset);
        String choicesResponse = HttpClientUtil.doGet(choiceUrl, charset);
        DefaultTableModel model = parseClassesInfo(coursesResponse, choicesResponse);
        return model;
    }


    // TODO: 提供接口
    // 提供本院系的课程信息
    // 根据xml文件的信息，选择本院系的课
    // 根据xml文件的信息，退选本院系的课
}