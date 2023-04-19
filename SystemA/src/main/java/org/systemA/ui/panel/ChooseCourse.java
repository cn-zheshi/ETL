package org.systemA.ui.panel;

import org.systemA.App;
import org.systemA.sql.AConnection;
import org.systemA.ui.UiConsts;
import org.systemA.util.PropertyUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.systemA.http.MyHttpClient.*;
import static org.systemA.util.xmlWriter.generateSelectCourseXML;

// 选课界面

public class ChooseCourse extends JPanel {
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;
    // 本院系已选课程，跨院系已选课程
    private static JTable jt_1 = null;
    // 学生编号
    public static String student_no = null;
    private static Object[][] tableDatas = null;
    // 课程编号, 课程名称, 学分, 授课老师, 授课地点, 共享
    public static Object[] tableTitles = {"课程编号", "课程名称", "学分", "授课老师", "授课地点", "共享"};
    public static DefaultTableModel model_1 = null;
    public static DefaultTableModel model_2 = null;
    public static DefaultTableModel model_3 = null;
    // 选课按钮和下拉框
    private static JButton btn_1 = null;
    private static JComboBox<String> cb_1 = null;

    public ChooseCourse(String username) {
        super(true);
        initialize();
        initiateTableData();
        initiateTableDataFromOther();
        addComponent();
    }

    private void initialize() {
        this.setBackground(UiConsts.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());
    }

    /**
     * 获取本院系所有课程
     */
    public static void initiateTableData() {
        // 获取本院系表格数据
        ct = AConnection.getConnection();
        try {
            model_1 = new DefaultTableModel(tableTitles, 0);
            ps = ct.prepareStatement("select * from 课程");
            rs = ps.executeQuery();
            while (rs.next()) {
                String [] row_1 = {
                        rs.getString("课程编号"),
                        rs.getString("课程名称"),
                        rs.getString("学分"),
                        rs.getString("授课老师"),
                        rs.getString("授课地点"),
                        rs.getString("共享")
                };
                model_1.addRow(row_1);
            }
            tableDatas = new Object[model_1.getRowCount()][model_1.getColumnCount()];
            for (int i = 0; i < model_1.getRowCount(); i++) {
                for (int j = 0; j < model_1.getColumnCount(); j++) {
                    tableDatas[i][j] = model_1.getValueAt(i, j);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取其他院系的课程
     */
    public static void initiateTableDataFromOther() {
        cb_1 = new JComboBox<>();
        // 获取其他院系课程数据
        model_2 = getAllCourses("A", "B", App.student_no);
        model_3 = getAllCourses("A", "C", App.student_no);
        tableDatas = new Object[model_1.getRowCount() + model_2.getRowCount() + model_3.getRowCount()][model_1.getColumnCount()];
        for (int i = 0; i < model_1.getRowCount(); i++) {
            for (int j = 0; j < model_1.getColumnCount(); j++) {
                tableDatas[i][j] = model_1.getValueAt(i, j);
            }
            cb_1.addItem((String) model_1.getValueAt(i, 1));
        }
        for (int i = 0; i < model_2.getRowCount(); i++) {
            for (int j = 0; j < model_2.getColumnCount(); j++) {
                tableDatas[i + model_1.getRowCount()][j] = model_2.getValueAt(i, j);
            }
            cb_1.addItem((String) model_2.getValueAt(i, 1));
        }
        for (int i = 0; i < model_3.getRowCount(); i++) {
            for (int j = 0; j < model_3.getColumnCount(); j++) {
                tableDatas[i + model_1.getRowCount() + model_2.getRowCount()][j] = model_3.getValueAt(i, j);
            }
            cb_1.addItem((String) model_3.getValueAt(i, 1));
        }
    }

    private void addComponent() {
        this.add(getUpPanel(), BorderLayout.NORTH);
        this.add(getCenterPanel(), BorderLayout.CENTER);
        this.add(getDownPanel(), BorderLayout.SOUTH);
    }

    private JPanel getUpPanel() {
        JPanel panelUp = new JPanel();
        panelUp.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 5));
        JLabel labelTitle = new JLabel("全部课程");
        labelTitle.setFont(UiConsts.FONT_TITLE);
        labelTitle.setForeground(UiConsts.TOOL_BAR_BACK_COLOR);
        panelUp.add(labelTitle);
        return panelUp;
    }

    private JPanel getCenterPanel() {
        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelCenter.setLayout(new GridLayout(1, 1));
        panelCenter.add(getPanelGridBakFrom());
        return panelCenter;
    }

    private JPanel getPanelGridBakFrom() {
        JPanel panelGridBakFrom = new JPanel();
        panelGridBakFrom.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelGridBakFrom.setLayout(new BorderLayout());
        JPanel panelFromControl = new JPanel();
        panelFromControl.setLayout(new GridLayout(1, 2));
        JPanel panelFromControlLeft = new JPanel();
        panelFromControlLeft.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 5));
        panelFromControlLeft.setBackground(UiConsts.MAIN_BACK_COLOR);
        JLabel labelFrom = new JLabel("本院系和跨院系的全部课程");
        labelFrom.setFont(new Font(PropertyUtil.getProperty("ds.ui.font.family"), 0, 18));
        labelFrom.setForeground(Color.gray);
        panelFromControlLeft.add(labelFrom);
        panelFromControl.add(panelFromControlLeft);
        jt_1 = new JTable(tableDatas, tableTitles);
        jt_1.setFont(UiConsts.FONT_NORMAL);
        jt_1.getTableHeader().setFont(UiConsts.FONT_NORMAL);
        jt_1.getTableHeader().setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        jt_1.setRowHeight(31);
        jt_1.setGridColor(UiConsts.TABLE_LINE_COLOR);
        jt_1.setSelectionBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        JScrollPane panelScroll = new JScrollPane(jt_1);
        panelScroll.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelGridBakFrom.add(panelFromControl, BorderLayout.NORTH);
        panelGridBakFrom.add(panelScroll, BorderLayout.CENTER);
        return panelGridBakFrom;
    }

    private JPanel getDownPanel() {
        // 下拉框和选课按钮
        JPanel panelDown = new JPanel();
        panelDown.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelDown.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 5));
        JLabel labelTitle = new JLabel("选课");
        labelTitle.setFont(UiConsts.FONT_TITLE);
        labelTitle.setForeground(UiConsts.TOOL_BAR_BACK_COLOR);
        panelDown.add(labelTitle);
        panelDown.add(cb_1);
        JButton btnChoose = new JButton("选课");
        btnChoose.setFont(UiConsts.FONT_NORMAL);
        btnChoose.setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        btnChoose.setForeground(Color.white);
        btnChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseName = (String) cb_1.getSelectedItem();
                System.out.println(courseName);
                // 如果courseName是在model_1中，说明是本院系的课程
                if (courseName != null) {
                    for (int i = 0; i < model_1.getRowCount(); i++) {
                        if (courseName.equals(model_1.getValueAt(i, 1))) {
                            // 选课
                            try {
                                chooseCourse((String) model_1.getValueAt(i, 0), App.student_no);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            // 刷新选课界面
                            App.mainPanelCenter.removeAll();
                            App.chooseCourse = new ChooseCourse(App.user);
                            App.mainPanelCenter.add(App.chooseCourse, BorderLayout.CENTER);
                            App.mainPanelCenter.updateUI();
                            break;
                        }
                    }
                    // 如果courseName是在model_2中，说明是跨院系的课程
                    for (int i = 0; i < model_2.getRowCount(); i++) {
                        if (courseName.equals(model_2.getValueAt(i, 1))) {
                            // 选课
                            try {
                                chooseCourseFromOther((String) model_2.getValueAt(i, 0), App.student_no, "B");
                            } catch (UnsupportedEncodingException ex) {
                                throw new RuntimeException(ex);
                            }
                            // 刷新选课界面
                            App.mainPanelCenter.removeAll();
                            App.chooseCourse = new ChooseCourse(App.user);
                            App.mainPanelCenter.add(App.chooseCourse, BorderLayout.CENTER);
                            App.mainPanelCenter.updateUI();
                            break;
                        }
                    }
                    // 如果courseName是在model_3中，说明是跨院系的课程
                    for (int i = 0; i < model_3.getRowCount(); i++) {
                        if (courseName.equals(model_3.getValueAt(i, 1))) {
                            // 选课
                            try {
                                chooseCourseFromOther((String) model_3.getValueAt(i, 0), App.student_no, "C");
                            } catch (UnsupportedEncodingException ex) {
                                throw new RuntimeException(ex);
                            }
                            // 刷新选课界面
                            App.mainPanelCenter.removeAll();
                            App.chooseCourse = new ChooseCourse(App.user);
                            App.mainPanelCenter.add(App.chooseCourse, BorderLayout.CENTER);
                            App.mainPanelCenter.updateUI();
                            break;
                        }
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "请选择课程", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        panelDown.add(btnChoose);
        return panelDown;
    }

    private void chooseCourse(String course_no, String student_no) throws SQLException {
        System.out.println("本院系选课");
        ps = ct.prepareStatement("select * from 选课 where 课程编号 = ? and 学生编号 = ?");
        ps.setString(1, course_no);
        ps.setString(2, student_no);
        rs = ps.executeQuery();
        if (rs.next()) {
            JOptionPane.showMessageDialog(null, "您已经选过这门课了", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String sql = "insert into 选课 values(?, ?, ?)";
        try {
            PreparedStatement ps = ct.prepareStatement(sql);
            ps.setString(1, student_no);
            ps.setString(2, course_no);
            ps.setString(3, "80");
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "选课成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void chooseCourseFromOther(String course_no, String student_no, String to) throws UnsupportedEncodingException {
        System.out.println("跨院系选课");
        String response = selectCourse("A", to, student_no, course_no);
        if (response == "success") {
            JOptionPane.showMessageDialog(null, "选课成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            // 刷新选课界面
            App.mainPanelCenter.removeAll();
            App.chooseCourse = new ChooseCourse(App.user);
            App.mainPanelCenter.add(App.chooseCourse, BorderLayout.CENTER);
            App.mainPanelCenter.updateUI();
        }
        else {
            JOptionPane.showMessageDialog(null, "选课失败", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
