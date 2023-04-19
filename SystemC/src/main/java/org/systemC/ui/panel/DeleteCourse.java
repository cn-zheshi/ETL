package org.systemC.ui.panel;
import org.systemC.App;
import org.systemC.sql.CConnection;
import org.systemC.ui.UiConsts;
import org.systemC.util.PropertyUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.systemC.http.MyHttpClient.getChoiceCourses;

public class DeleteCourse extends JPanel {
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;
    // 本院系已选课程，跨院系已选课程
    private static JTable jt_1 = null;
    // 学生编号
    public static String student_no = null;
    private static Object[][] tableDatas = null;
    public static Object[] tableTitles = {"课程编号", "课程名称", "学分", "授课老师", "授课地点", "成绩"};

    public static DefaultTableModel model_1 = null;

    // 退选按钮和下拉框
    private static JButton btn_1 = null;
    private static JComboBox<String> cb_1 = null;

    public DeleteCourse(String username) {
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


    private void addComponent() {
        this.add(getUpPanel(), BorderLayout.NORTH);
        this.add(getCenterPanel(), BorderLayout.CENTER);
        this.add(getDownPanel(), BorderLayout.SOUTH);
    }

    private JPanel getUpPanel() {
        JPanel panelUp = new JPanel();
        panelUp.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 5));
        JLabel labelTitle = new JLabel("已选课程");
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
        // 本院系已选课程
        // 跨院系已选课程
        JPanel panelGridBakFrom = new JPanel();
        panelGridBakFrom.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelGridBakFrom.setLayout(new BorderLayout());

        // 本院系已选课程控制面板 panelFromControl
        JPanel panelFromControl = new JPanel();
        panelFromControl.setLayout(new GridLayout(1, 2));

        JPanel panelFromControlLeft = new JPanel();
        panelFromControlLeft.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 5));
        panelFromControlLeft.setBackground(UiConsts.MAIN_BACK_COLOR);

        JLabel labelFrom = new JLabel("本院系和跨院系已选课程，根据课程编号区分");
        labelFrom.setFont(new Font(PropertyUtil.getProperty("ds.ui.font.family"), 0, 18));
        labelFrom.setForeground(Color.gray);
        panelFromControlLeft.add(labelFrom);
        panelFromControl.add(panelFromControlLeft);


        // 本院系已选课程表格 jt_1 panelScroll
        jt_1 = new JTable(tableDatas, tableTitles);
        jt_1.setFont(UiConsts.FONT_NORMAL);
        jt_1.getTableHeader().setFont(UiConsts.FONT_NORMAL);
        jt_1.getTableHeader().setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        jt_1.setRowHeight(31);
        jt_1.setGridColor(UiConsts.TABLE_LINE_COLOR);
        jt_1.setSelectionBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        JScrollPane panelScroll = new JScrollPane(jt_1);
        panelScroll.setBackground(UiConsts.MAIN_BACK_COLOR);
        // 本院系课程 标题和表格加入到面板
        panelGridBakFrom.add(panelFromControl, BorderLayout.NORTH);
        panelGridBakFrom.add(panelScroll, BorderLayout.CENTER);
        return panelGridBakFrom;
    }

    /**
     * 获取课程表格数据，包括表头、内容名
     */
    public static void initiateTableData() {
        // 获取本院系表格数据
        ct = CConnection.getConnection();
        try {
            model_1 = new DefaultTableModel(tableTitles, 0);
            // 获取学生编号
            ps = ct.prepareStatement("select * from 学生 where 关联账户 = ?");
            ps.setString(1, App.user);
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
                            rs_1.getString("课程名称"),
                            rs_1.getString("学分"),
                            rs_1.getString("授课老师"),
                            rs_1.getString("授课地点"),
                            rs.getString("成绩")
                    };
                    model_1.addRow(row_1);
                }
            }
            tableDatas = new Object[model_1.getRowCount()][model_1.getColumnCount()];
            for (int i = 0; i < model_1.getRowCount(); i++) {
                for (int j = 0; j < model_1.getColumnCount(); j++) {
                    tableDatas[i][j] = model_1.getValueAt(i, j);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取跨院系课程表格数据，包括表头、内容名
     */
    public static void initiateTableDataFromOther() {
        cb_1 = new JComboBox<>();
        // 获取跨院系表格数据，追加到tableDatas
        DefaultTableModel model_2 = getChoiceCourses("A", "A", "20210001");
        tableDatas = new Object[model_1.getRowCount() + model_2.getRowCount()][model_1.getColumnCount()];
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
    }

    private JPanel getDownPanel() {
        // 下拉框和选课按钮
        JPanel panelDown = new JPanel();
        panelDown.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelDown.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 5));
        JLabel labelTitle = new JLabel("退选");
        labelTitle.setFont(UiConsts.FONT_TITLE);
        labelTitle.setForeground(UiConsts.TOOL_BAR_BACK_COLOR);
        panelDown.add(labelTitle);
        panelDown.add(cb_1);
        JButton btnChoose = new JButton("退选");
        btnChoose.setFont(UiConsts.FONT_NORMAL);
        btnChoose.setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        btnChoose.setForeground(Color.white);
        btnChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseName = (String) cb_1.getSelectedItem();
                String courseNo = "";
            }
        });
        panelDown.add(btnChoose);
        return panelDown;
    }

}

