package org.systemB.ui.panel;

import org.systemB.sql.BConnection;
import org.systemB.ui.UiConsts;
import org.systemB.App;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
/**
 * 登录界面 包括学生和管理员登录
 */
public class LoginPanel extends JPanel implements ActionListener {
    static Connection ct = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    JPanel jp_1, jp_2, jp_3, jp_4 = null;       // 设置4个面板
    JLabel jlb_1, jlb_2, jlb_3 = null;          // 设置3个标签（用户、密码、身份）
    JTextField jtf = null;	                    // 设置1个普通文本框，用来输入用户账号
    JPasswordField jpf = null;                  // 设置一个密码文本框，用来输入密码
    JRadioButton jrb_1, jrb_2 = null;           // 设置两个按钮，用来选择身份（学生或者老师）
    ButtonGroup bg = null;
    JButton jb_1, jb_2, jb_3 = null;            // 设置三个单击按钮（登录、重置、退出）


    public LoginPanel()
    {
        super(true);
        initialize();
        addComponent();
    }

    /**
     * 初始化
     */
    private void initialize() {
        this.setBackground(UiConsts.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());
        ct = BConnection.getConnection();
    }

    /**
     * 为面板添加组件
     */
    private void addComponent() {
        this.add(getCenterPanel(), BorderLayout.CENTER);
    }

    /**
     * 面板中部
     *
     * @return
     */
    private JPanel getCenterPanel() {
        JPanel centerPanel = new JPanel();
        jp_1 = new JPanel();
        jp_2 = new JPanel();
        jp_3 = new JPanel();
        jp_4 = new JPanel();
        jlb_1 = new JLabel("用户名");
        jlb_2 = new JLabel("密   码");
        jlb_3 = new JLabel("身   份");
        // 用户名
        jtf = new JTextField(10);
        // 密码
        jpf = new JPasswordField(10);
        // 身份
        jrb_1 = new JRadioButton("管理员");
        jrb_2 = new JRadioButton("学生");
        bg = new ButtonGroup();
        bg.add(jrb_1);
        bg.add(jrb_2);
        jrb_2.setSelected(true);
        jb_1 = new JButton("登录");
        jb_2 = new JButton("重置");
        jb_3 = new JButton("退出");
        jb_1.addActionListener(this);
        jb_2.addActionListener(this);
        jb_3.addActionListener(this);
        jp_1.add(jlb_1);
        jp_1.add(jtf);
        jp_2.add(jlb_2);
        jp_2.add(jpf);
        jp_3.add(jlb_3);
        jp_3.add(jrb_1);
        jp_3.add(jrb_2);
        jp_4.add(jb_1);
        jp_4.add(jb_2);
        jp_4.add(jb_3);
        // 设置布局
        centerPanel.setLayout(new GridLayout(4, 1));
        centerPanel.add(jp_1);
        centerPanel.add(jp_2);
        centerPanel.add(jp_3);
        centerPanel.add(jp_4);


        return centerPanel;
    }

    public void clear() {
        jtf.setText("");
        jpf.setText("");
    }

    // 动作事件监听函数
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == "登录"){
            // 选中管理员登录
            if(jrb_1.isSelected()) {

            }
            // 选中学生登录
            else if(jrb_2.isSelected()) {
                try {
                    ps = ct.prepareStatement("select * from 账户 where 级别=? and 账户名=?");
                    ps.setString(1, "2");
                    ps.setString(2, jtf.getText());
                    rs=ps.executeQuery();
                    if(rs.next()) {
                        // 将用户名，密码，级别取出
                        String user_name = rs.getString("账户名");
                        String password = rs.getString("密码");
                        String identity = rs.getString("级别");
                        String student_no = rs.getString("客体");
                        if(user_name.equals(jtf.getText()) && password.equals(String.valueOf(jpf.getPassword()))){
                            JOptionPane.showMessageDialog(null, "登陆成功！", "提示消息", JOptionPane.WARNING_MESSAGE);
                            // 设置当前用户
                            App.student_no = student_no;
                            clear();
                            // 显示功能栏，隐藏登录界面
                            App.changePanel();
                        }else if(jtf.getText().isEmpty() && String.valueOf(jpf.getPassword()).isEmpty()){
                            JOptionPane.showMessageDialog(null, "请输入用户名和密码！", "提示消息", JOptionPane.WARNING_MESSAGE);
                        }else if(jtf.getText().isEmpty()){
                            JOptionPane.showMessageDialog(null, "请输入用户名！", "提示信息", JOptionPane.WARNING_MESSAGE);
                        }else if(String.valueOf(jpf.getPassword()).isEmpty()){
                            JOptionPane.showMessageDialog(null, "请输入密码！", "提示信息", JOptionPane.WARNING_MESSAGE);
                        }else{
                            JOptionPane.showMessageDialog(null, "用户名或者密码错误！\n请重新输入", "提示信息", JOptionPane.ERROR_MESSAGE);
                            clear();
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "账户名或密码错误！");
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }else if(e.getActionCommand() == "重置"){
            clear();
        }else if(e.getActionCommand() == "退出"){
            System.exit(0);		// 结束程序，关闭所有窗口
        }
    }
}