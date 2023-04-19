package org.systemC.ui.panel;

import org.systemC.App;
import org.systemC.sql.CConnection;
import org.systemC.ui.UiConsts;

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

    // 显示个人信息：Sno, Snm, Sex, Sde, Pwd
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
        String password = App.password;
        ct = CConnection.getConnection();
        try {
            ps = ct.prepareStatement("select * from 学生 where Pwd = ?");
            ps.setString(1, password);
            rs = ps.executeQuery();
            while (rs.next()) {
                jl_1 = new JLabel("Sno: " + rs.getString("Sno"));
                jl_2 = new JLabel("Snm: " + rs.getString("Snm"));
                jl_3 = new JLabel("Sex: " + rs.getString("Sex"));
                jl_4 = new JLabel("Sde: " + rs.getString("Sde"));
                jl_5 = new JLabel("Pwd: " + rs.getString("Pwd"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
