package org.systemC.ui.panel;

import org.systemC.App;
import org.systemC.sql.CConnection;
import org.systemC.ui.UiConsts;
import org.systemC.util.PropertyUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.systemC.http.MyHttpClient.getChoiceCourses;

// 查看已选课程，本院系已选课程、跨院系已选课程
// 本院系已选课程：课程编号, 课程名称, 学分, 授课老师, 授课地点, 成绩
public class ViewCourse extends JPanel {
    public static Connection ct = null;
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;
    // 本院系已选课程，跨院系已选课程
    private static JTable jt_1 = null;
    // 学生编号
    public static String student_no = null;
    private static Object[][] tableDatas = null;
    public static Object[] tableTitles = {"Cno", "Cnm", "Ctm", "Cpt", "Tec", "Pla", "Grade"};

    public static DefaultTableModel model_1 = null;


    public ViewCourse(String username) {
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
            // 获取已选课程的信息
            ps = ct.prepareStatement("select * from 选课 where Sno = ?");
            ps.setString(1, App.student_no);
            rs = ps.executeQuery();
            while (rs.next()) {
                String course_no = rs.getString("Cno");
                ps = ct.prepareStatement("select * from 课程 where Cno = ?");
                ps.setString(1, course_no);
                ResultSet rs_1 = ps.executeQuery();
                while (rs_1.next()) {
                    String [] row_1 = {
                            rs_1.getString("Cno"),
                            rs_1.getString("Cnm"),
                            rs_1.getString("Ctm"),
                            rs_1.getString("Cpt"),
                            rs_1.getString("Tec"),
                            rs_1.getString("Pla"),
                            rs.getString("Grd")
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
        // 获取跨院系表格数据，追加到tableDatas
        DefaultTableModel model_2 = getChoiceCourses("C", "A", App.student_no);
        DefaultTableModel model_3 = getChoiceCourses("C", "B", App.student_no);
        tableDatas = new Object[model_1.getRowCount() + model_2.getRowCount() + model_3.getRowCount()][model_1.getColumnCount()];
        for (int i = 0; i < model_1.getRowCount(); i++) {
            for (int j = 0; j < model_1.getColumnCount(); j++) {
                tableDatas[i][j] = model_1.getValueAt(i, j);
            }
        }
        for (int i = 0; i < model_2.getRowCount(); i++) {
            for (int j = 0; j < model_2.getColumnCount(); j++) {
                tableDatas[i + model_1.getRowCount()][j] = model_2.getValueAt(i, j);
            }
        }
        for (int i = 0; i < model_3.getRowCount(); i++) {
            for (int j = 0; j < model_3.getColumnCount(); j++) {
                tableDatas[i + model_1.getRowCount() + model_2.getRowCount()][j] = model_3.getValueAt(i, j);
            }
        }
    }
}
