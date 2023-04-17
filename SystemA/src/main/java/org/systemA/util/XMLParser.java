package org.systemA.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.systemA.ui.panel.ViewCourse;
import org.systemA.sql.AConnection;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class XMLParser {
    public void testParser(){
        Connection con = AConnection.getConnection();
        PreparedStatement ps = null;
        int rs = 0;
        Document doc = null;
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(new FileReader(new File("src/org.systemA.main/java/schema/choiceA.xml")));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        for (Iterator i = root.elementIterator(); i.hasNext();) {
            Element foo = (Element) i.next();
            Vector<String> employee = new Vector<String>();
            for (Iterator j = foo.elementIterator(); j.hasNext();) {
                Element tmp = (Element) j.next();
                employee.add(tmp.getStringValue());
            }
            Vector<String> allName = new Vector<String>();
            String sql = "insert into 选课 values('"+ employee.elementAt(0);
            for (int k = 1; k < employee.size(); k++)
                sql += "','" + employee.elementAt(k);
            sql += "')";
            try {
                ps = con.prepareStatement(sql);
                rs = ps.executeUpdate();
            } catch (SQLException throwables) {
                System.out.println(sql);
                throwables.printStackTrace();
            }
        }
    }

    // 解析课程信息
    public static DefaultTableModel parseCoursesInfo(String coursesResponse) {
        DefaultTableModel model = new DefaultTableModel(ViewCourse.tableTitles, 0);
        SAXReader saxReader = new SAXReader();
        Document courseDocument = null;
        List<String[]> courses = new ArrayList< String[]>();
        try {
            InputStream inputStream = new ByteArrayInputStream(coursesResponse.getBytes("UTF-8"));
            courseDocument = saxReader.read(inputStream);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        Element root = courseDocument.getRootElement();
        for (Iterator i = root.elementIterator(); i.hasNext();) {
            Element foo = (Element) i.next();
            Vector<String> course = new Vector<String>();
            for (Iterator j = foo.elementIterator(); j.hasNext();) {
                Element tmp = (Element) j.next();
                course.add(tmp.getStringValue());
            }
            courses.add(course.toArray(new String[0]));
        }
        for (String[] course : courses) {
            String[] row = new String[ViewCourse.tableTitles.length];
            // 课程编号, 课程名称, 学分, 授课老师, 授课地点, 共享
            row[0] = course[0]; // 课程编号
            row[1] = course[1]; // 课程名称
            row[2] = course[2]; // 学分
            row[3] = course[3]; // 授课老师
            row[4] = course[4]; // 授课地点
            row[5] = course[5]; // 共享
            model.addRow(row);
        }
        return model;
    }


    // 解析选课信息
    public static DefaultTableModel parseClassesInfo(String coursesResponse, String choicesResponse) {
        DefaultTableModel model = new DefaultTableModel(ViewCourse.tableTitles, 0);
        SAXReader saxReader = new SAXReader();
        Document courseDocument = null;
        Document choiceDocument = null;
        List<String[]> courses = new ArrayList< String[]>();
        List<String[]> choices = new ArrayList< String[]>();
        // 先解析课程信息
        try {
            InputStream inputStream = new ByteArrayInputStream(coursesResponse.getBytes("UTF-8"));
            courseDocument = saxReader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Element root = courseDocument.getRootElement();
        for (Iterator i = root.elementIterator(); i.hasNext();) {
            Element foo = (Element) i.next();
            Vector<String> course = new Vector<String>();
            for (Iterator j = foo.elementIterator(); j.hasNext();) {
                Element tmp = (Element) j.next();
                course.add(tmp.getStringValue());
            }
            courses.add(course.toArray(new String[0]));
        }

        // 再解析选课信息
        try {
            InputStream inputStream = new ByteArrayInputStream(choicesResponse.getBytes("UTF-8"));
            choiceDocument = saxReader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        root = choiceDocument.getRootElement();
        for (Iterator i = root.elementIterator(); i.hasNext();) {
            Element foo = (Element) i.next();
            Vector<String> choice = new Vector<String>();
            for (Iterator j = foo.elementIterator(); j.hasNext();) {
                Element tmp = (Element) j.next();
                choice.add(tmp.getStringValue());
            }
            choices.add(choice.toArray(new String[0]));
        }

        // 将课程信息和选课信息合并
        for (String[] course : courses) {
            for (String[] choice : choices) {
                if (course[0].equals(choice[0])) {
                    String[] row = new String[ViewCourse.tableTitles.length];
                    row[0] = course[0]; // 课程编号
                    row[1] = course[1]; // 课程名称
                    row[2] = course[2]; // 学分
                    row[3] = course[3]; // 授课老师
                    row[4] = course[4]; // 授课地点
                    row[5] = choice[2]; // 成绩
                    model.addRow(row);
                }
            }
        }
        return model;
    }
}
