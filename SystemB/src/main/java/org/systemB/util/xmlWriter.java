package org.systemB.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.List;

public class xmlWriter {
    // 生成课程信息xml文件
    public static String generateCourseInfo(List<String[]> courses) {
        String xml = null;
        // 创建XML文档
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("Classes");

        for (String[] course : courses) {
            Element classElement = root.addElement("class");
            classElement.addElement("编号").addText(course[0]);
            classElement.addElement("名称").addText(course[1]);
            classElement.addElement("课时").addText(course[2]);
            classElement.addElement("学分").addText(course[3]);
            classElement.addElement("老师").addText(course[4]);
            classElement.addElement("地点").addText(course[5]);
            classElement.addElement("共享").addText(course[6]);
        }

        xml = document.asXML();

        return xml;
    }

    public static String generateChoiceInfo(List<String[]> choices) {
        String xml = null;
        // 创建XML文档
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("Choices");

        for (String[] choice : choices) {
            Element classElement = root.addElement("choice");
            classElement.addElement("课程编号").addText(choice[0]);
            classElement.addElement("学号").addText(choice[1]);
            classElement.addElement("得分").addText(choice[2]);
        }

        xml = document.asXML();

        return xml;
    }

    public static String generateSelectCourseXML(String studentNo, String courseNo) {
        String xml = null;
        // 创建XML文档
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("Choices");
        Element choiceElement = root.addElement("choice");
        choiceElement.addElement("学号").addText(studentNo);
        choiceElement.addElement("课程编号").addText(courseNo);
        xml = document.asXML();
        return xml;
    }

    public static String generateStudentInfo(List<String[]> students) {
        String xml = null;
        // 创建XML文档
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("Students");

        for (String[] student : students) {
            Element classElement = root.addElement("student");
            classElement.addElement("学号").addText(student[0]);
            classElement.addElement("姓名").addText(student[1]);
            classElement.addElement("性别").addText(student[2]);
            classElement.addElement("专业").addText(student[3]);
        }

        xml = document.asXML();

        return xml;
    }
}


