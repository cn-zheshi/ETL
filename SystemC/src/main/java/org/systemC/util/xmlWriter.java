package org.systemC.util;

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
            classElement.addElement("Cno").addText(course[0]);
            classElement.addElement("Cnm").addText(course[1]);
            classElement.addElement("Ctm").addText(course[2]);
            classElement.addElement("Cpt").addText(course[3]);
            classElement.addElement("Tec").addText(course[4]);
            classElement.addElement("Pla").addText(course[5]);
            classElement.addElement("Share").addText(course[6]);
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
            classElement.addElement("Cno").addText(choice[0]);
            classElement.addElement("Sno").addText(choice[1]);
            classElement.addElement("Grd").addText(choice[2]);
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
        choiceElement.addElement("Sno").addText(studentNo);
        choiceElement.addElement("Cno").addText(courseNo);
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
            classElement.addElement("Sno").addText(student[0]);
            classElement.addElement("Snm").addText(student[1]);
            classElement.addElement("Sex").addText(student[2]);
            classElement.addElement("Sde").addText(student[3]);
        }

        xml = document.asXML();

        return xml;
    }
}


