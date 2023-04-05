package org.systemA;

import util.Styles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// 查看已选课程，本院系已选课程、跨院系已选课程、返回
// 本院系已选课程：课程编号, 课程名称, 学分, 授课老师, 授课地点, 成绩
// TODO: 跨院系已选课程

public class SelectedCourse extends JFrame implements ActionListener {
    // 数据库连接、sql语句、结果集等对象
    static Connection ct = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // 本院系已选课程，跨院系已选课程
    // 课程编号, 学生编号, 课程名称, 学分, 授课老师, 授课地点, 得分
    JTable jt_1, jt_2 = null;
    DefaultTableModel model_1 = null;
    JButton jb_1 = null;
    String username = null;
    // 学生编号
    String student_no = null;
    JComboBox<String> jComboBox = new JComboBox<String>();
    JButton selectButton = new JButton("退选");

    public SelectedCourse(String username) {
        this.username = username;
        this.setLayout(new GridLayout(3, 1));
        jb_1 = new JButton("返回");
        jb_1.addActionListener(this);
        selectButton.addActionListener(this);
        jt_1 = new JTable();
        String [] colomn_1 = {"课程编号", "学生编号", "课程名称", "学分", "授课老师", "授课地点", "得分"};
        jt_1.setModel(model_1 = new DefaultTableModel(colomn_1, 0));
        model_1.addRow(colomn_1);

        // 获取表格数据
        ct = AConnection.getConnection();
        try {
            // 获取学生编号
            ps = ct.prepareStatement("select * from 学生 where 关联账户 = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                student_no = rs.getString("学号");
            }
            // 获取已选课程的信息
            ps = ct.prepareStatement("select * from 选课 where 学生编号 = ?");
            ps.setString(1, student_no);
            rs = ps.executeQuery();
            while (rs.next()) {
                String course_no = rs.getString("课程编号");
                ps = ct.prepareStatement("select * from 课程 where 课程编号 = ?");
                ps.setString(1, course_no);
                ResultSet rs_1 = ps.executeQuery();
                while (rs_1.next()) {
                    String [] row_1 = {
                            rs_1.getString("课程编号"),
                            rs.getString("学生编号"),
                            rs_1.getString("课程名称"),
                            rs_1.getString("学分"),
                            rs_1.getString("授课老师"),
                            rs_1.getString("授课地点"),
                            rs.getString("成绩")
                    };
                    model_1.addRow(row_1);
                }
                jComboBox.addItem(rs.getString("课程编号"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // 将表格加入到面板
        JPanel jp_1 = new JPanel();
        JLabel label = new JLabel("本院系已选课程");
        jp_1.add(label);
        jp_1.add(jt_1);
        // 将按钮加入到面板
        JPanel jp_2 = new JPanel();
        jp_2.add(jb_1);

        JPanel jp_3 = new JPanel();
        jp_3.add(jComboBox);
        jp_3.add(selectButton);

        // 将面板加入到框架
        this.add(jp_1);
        this.add(jp_3);
        this.add(jp_2);

        Styles.setStyle(this);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb_1) {
            // 返回
            this.dispose();
            new Selection(this.username);
        }
        else if (e.getSource() == selectButton) {
            // 退选
            String course_no = (String) jComboBox.getSelectedItem();
            try {
                ps = ct.prepareStatement("delete from 选课 where 学生编号 = ? and 课程编号 = ?");
                ps.setString(1, student_no);
                ps.setString(2, course_no);
                try {
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "退选成功");
                }
                catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "退选失败");
                }
                this.dispose();
                new SelectedCourse(this.username);
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
