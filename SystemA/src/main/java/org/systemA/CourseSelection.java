package org.systemA;

import org.systemA.util.Styles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// 本院系的课程信息，选课和退选按钮，返回按钮

public class CourseSelection extends JFrame implements ActionListener {
    // 数据库连接、sql语句、结果集等对象
    static Connection ct = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String username = null;
    String student_no = null;
    JTable courseTable = null;
    DefaultTableModel tableModel;

    JButton returnButton = new JButton("返回");

    // 下拉框
    JComboBox<String> jComboBox = new JComboBox<String>();

    // 选课按钮
    JButton selectButton = new JButton("选课");


    public CourseSelection(String username) {
        this.username = username;
        this.setLayout(new GridLayout(3, 1));

        // 创建表格模型
        String[] columnNames = {"课程编号", "课程名称", "学分", "授课老师", "授课地点"};
        tableModel = new DefaultTableModel(columnNames, 0);
        // 创建表格组件
        courseTable = new JTable(tableModel);
        tableModel.addRow(columnNames);
        returnButton.addActionListener(this);
        selectButton.addActionListener(this);

        // 设置表格的行高
        courseTable.setRowHeight(50);
        // 设置成不可编辑
        courseTable.setEnabled(false);

        // 获取表格数据
        ct = AConnection.getConnection();
        try {
            ps = ct.prepareStatement("select * from 课程");
            rs = ps.executeQuery();
            while (rs.next()) {
                String course_no = rs.getString("课程编号");
                String course_name = rs.getString("课程名称");
                String credit = rs.getString("学分");
                String teacher = rs.getString("授课老师");
                String location = rs.getString("授课地点");
                String[] row = {course_no, course_name, credit, teacher, location};
                tableModel.addRow(row);
                jComboBox.addItem(course_name);
            }
            // 获取学生编号
            ps = ct.prepareStatement("select * from 学生 where 关联账户=?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                student_no= rs.getString("学号");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // 将表格加入到面板
        JPanel panel = new JPanel();
        panel.add(courseTable);

        JPanel panel2 = new JPanel();
        panel2.add(returnButton);

        JPanel panel3 = new JPanel();
        panel3.add(jComboBox);
        panel3.add(selectButton);

        this.add(panel);
        this.add(panel3);
        this.add(panel2);

        Styles.setStyle(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnButton) {
            this.dispose();
            new Selection(this.username);
        }
        else if (e.getSource() == selectButton) {
            String course_name = (String) jComboBox.getSelectedItem();
            try {
                ps = ct.prepareStatement("select * from 课程 where 课程名称=?");
                ps.setString(1, course_name);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String course_no = rs.getString("课程编号");
                    ps = ct.prepareStatement("INSERT INTO 选课 (课程编号, 学生编号, 成绩) VALUES (?, ?, ?)");
                    ps.setString(1, course_no);
                    ps.setString(2, student_no);
                    // 随机生成成绩 80-100
                    int grade = (int) (Math.random() * 20 + 80);
                    ps.setInt(3, grade);
                    try {
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "选课成功！");
                    }
                    catch (Exception e1) {
                        System.out.println(e1.getMessage());
                        JOptionPane.showMessageDialog(null, "选课失败！");
                    }
                }
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }


}
