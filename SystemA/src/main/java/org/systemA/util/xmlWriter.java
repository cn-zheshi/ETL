package org.systemA.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
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
            classElement.addElement("课程编号").addText(course[0]);
            classElement.addElement("课程名称").addText(course[1]);
            classElement.addElement("学分").addText(course[2]);
            classElement.addElement("授课老师").addText(course[3]);
            classElement.addElement("授课地点").addText(course[4]);
            classElement.addElement("共享").addText(course[5]);
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
            classElement.addElement("学生编号").addText(choice[1]);
            classElement.addElement("成绩").addText(choice[2]);
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
        choiceElement.addElement("学生编号").addText(studentNo);
        choiceElement.addElement("课程编号").addText(courseNo);
        xml = document.asXML();
        return xml;
    }
}


