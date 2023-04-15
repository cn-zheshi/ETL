package org.systemA.ui.panel;

import org.systemA.sql.AConnection;
import org.systemA.ui.UiConsts;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PersonalInfo extends JPanel {
    // 数据库连接、sql语句、结果集等对象
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;

    // 显示个人信息：学号, 姓名, 性别, 院系, 关联账户
    public static JLabel jl_1, jl_2, jl_3, jl_4, jl_5 = null;
    public static String user = null;
    public PersonalInfo(String username)
    {
        super(true);
        initialize();
        setContent(username);
        addComponent();
    }

    private void initialize() {
        this.setBackground(UiConsts.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());
    }

    private void addComponent() {
        // 设置布局
        this.setLayout(new GridLayout(6, 1));
        this.add(jl_1);
        this.add(jl_2);
        this.add(jl_3);
        this.add(jl_4);
        this.add(jl_5);
    }

    public static void setContent(String username) {
        user = username;
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
    }

}
