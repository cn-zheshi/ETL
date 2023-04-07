package org.systemA.http;

public class MyHttpClient {
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

    // TODO: 提供接口
    // 提供本院系的课程信息
    // 根据xml文件的信息，选择本院系的课
    // 根据xml文件的信息，退选本院系的课
}