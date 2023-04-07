package org.systemA;
import org.systemA.util.Styles;

import javax.swing.*;                             // 引入swing工具包进行GUI的设计
import java.awt.*;					             // 引入awt工具包
import java.awt.event.*;

// 选择查看个人信息、查看已选课程、本院系课程选择、跨院系课程选择、退出
public class Selection extends JFrame implements ActionListener {
    // 5个单击按钮
    JButton jb_1, jb_2, jb_3, jb_4, jb_5 = null;
    String username = null;
    public Selection(String username) {
        this.username = username;
        jb_1 = new JButton("查看个人信息");
        jb_2 = new JButton("查看已选课程");
        jb_3 = new JButton("本院系课程选择");
        jb_4 = new JButton("跨院系课程选择");
        jb_5 = new JButton("退出");
        // 设置布局
        this.setLayout(new GridLayout(5, 1));
        this.add(jb_1);
        this.add(jb_2);
        this.add(jb_3);
        this.add(jb_4);
        JPanel jp = new JPanel();
        jp.add(jb_5);
        this.add(jp);
        jb_1.addActionListener(this);
        jb_2.addActionListener(this);
        jb_3.addActionListener(this);
        jb_4.addActionListener(this);
        jb_5.addActionListener(this);

        Styles.setStyle(this);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb_1) {
            this.dispose();
            new PersonalInformation(this.username);
        } else if (e.getSource() == jb_2) {
            this.dispose();
            new SelectedCourse(this.username);
        } else if (e.getSource() == jb_3) {
            this.dispose();
            new CourseSelection(this.username);
        } else if (e.getSource() == jb_4) {
            this.dispose();
            new CrossDepartmentCourseSelection();
        } else if (e.getSource() == jb_5) {
            System.exit(0);
        }
    }
}
