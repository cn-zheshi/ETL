package org.systemA;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 查看跨院系课程，选择跨院系课程
public class CrossDepartmentCourseSelection extends JFrame implements ActionListener {
    // B院系课程信息，选课按钮；C院系课程信息，选课按钮；返回按钮
    JButton returnButton = new JButton("返回");
    JButton selectBButton = new JButton("选择B院系课程");
    JButton selectCButton = new JButton("选择C院系课程");
    JTable courseBTable = null;
    JTable courseCTable = null;
    public void actionPerformed(ActionEvent e) {

    }
}
