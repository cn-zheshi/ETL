package org.systemA;

import util.Styles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonalInformation extends JFrame implements ActionListener {
    // 数据库连接、sql语句、结果集等对象
    static Connection ct = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // 显示个人信息：学号, 姓名, 性别, 院系, 关联账户
    JLabel jl_1, jl_2, jl_3, jl_4, jl_5 = null;
    // 返回按钮
    JButton jb_1 = null;
    String username = null;
    public PersonalInformation(String username)
    {
        this.username = username;
        // 设置布局
        this.setLayout(new GridLayout(6, 1));
        // 显示个人信息
        ct = AConnection.getConnection();
        try {
            ps = ct.prepareStatement("select * from 学生 where 关联账户 = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                jl_1 = new JLabel("学号: " + rs.getString("学号"));
                jl_2 = new JLabel("姓名: " + rs.getString("姓名"));
                jl_3 = new JLabel("性别: " + rs.getString("性别"));
                jl_4 = new JLabel("院系: " + rs.getString("院系"));
                jl_5 = new JLabel("关联账户: " + rs.getString("关联账户"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.add(jl_1);
        this.add(jl_2);
        this.add(jl_3);
        this.add(jl_4);
        this.add(jl_5);
        // 返回按钮
        jb_1 = new JButton("返回");
        this.add(jb_1);
        jb_1.addActionListener(this);
        Styles.setStyle(this);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb_1) {
            this.dispose();
            new Selection(this.username);
        }
    }
}
