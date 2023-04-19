package org.systemC.ui;
import org.systemC.App;
import org.systemC.util.PropertyUtil;
import javax.swing.*;
import java.awt.*;

/**
 * UI相关的常量
 *
 * @author Bob
 */
public class UiConsts {

    /**
     * 软件名称,版本
     */
    public final static String APP_NAME = "C教务系统";

    /**
     * 主窗口大小
     */
    public final static int MAIN_WINDOW_X = 240;
    public final static int MAIN_WINDOW_Y = 100;
    public final static int MAIN_WINDOW_WIDTH = 885;
    public final static int MAIN_WINDOW_HEIGHT = 636;

    /**
     * 系统当前路径
     */
    public final static String CURRENT_DIR = System.getProperty("user.dir");

    /**
     * 主窗口图标
     */
    public final static Image IMAGE_ICON = Toolkit.getDefaultToolkit()
            .getImage(App.class.getResource("/icon/system.png"));

    /**
     * 主窗口背景色
     */
    public final static Color MAIN_BACK_COLOR = Color.WHITE;

    /**
     * 工具栏背景色
     */
    public final static Color TOOL_BAR_BACK_COLOR = new Color(66, 99, 123);
    /**
     * 表格线条背景色
     */
    public final static Color TABLE_LINE_COLOR = new Color(229, 229, 229);

    // 字体
    /**
     * 标题字体
     */
    public final static Font FONT_TITLE = new Font(PropertyUtil.getProperty("ds.ui.font.family"), 0, 27);
    /**
     * 普通字体
     */
    public final static Font FONT_NORMAL = new Font(PropertyUtil.getProperty("ds.ui.font.family"), 0, 13);
    /**
     * radio字体
     */
    public final static Font FONT_RADIO = new Font(PropertyUtil.getProperty("ds.ui.font.family"), 0, 15);

    /**
     * 主图标
     */
    public final static ImageIcon ICON_DATA_SYNC = new ImageIcon(
            App.class.getResource("/icon/system.png"));

    /**
     * 个人信息图标
     */
    public  final static ImageIcon ICON_PERSONAL_INFO = new ImageIcon(
            App.class.getResource("/icon/personal_info.png"));

    public  final static ImageIcon ICON_PERSONAL_INFO_HOVER = new ImageIcon(
            App.class.getResource("/icon/personal_info_hover.png"));


    /**
     * 查看课程图标
     */
    public  final static ImageIcon ICON_VIEW_COURSE = new ImageIcon(
            App.class.getResource("/icon/view_course.png"));

    public  final static ImageIcon ICON_VIEW_COURSE_HOVER = new ImageIcon(
            App.class.getResource("/icon/view_course_hover.png"));

    /**
     * 选择课程图标
     */
    public final static ImageIcon ICON_CHOOSE_COURSE = new ImageIcon(
            App.class.getResource("/icon/choose_course.png"));

    public final static ImageIcon ICON_CHOOSE_COURSE_HOVER = new ImageIcon(
            App.class.getResource("/icon/choose_course_hover.png"));

    /**
     * 退选课程图标
     */
    public final static ImageIcon ICON_DROP_COURSE = new ImageIcon(
            App.class.getResource("/icon/drop_course.png"));

    public final static ImageIcon ICON_DROP_COURSE_HOVER = new ImageIcon(
            App.class.getResource("/icon/drop_course_hover.png"));

    /**
     * 主面板水平间隔
     */
    public final static int MAIN_H_GAP = 25;
    /**
     * 主面板Label 大小
     */
    public final static Dimension LABLE_SIZE = new Dimension(1300, 30);
    /**
     * Item Label 大小
     */
    public final static Dimension LABLE_SIZE_ITEM = new Dimension(78, 30);
    /**
     * Item text field 大小
     */
    public final static Dimension TEXT_FIELD_SIZE_ITEM = new Dimension(400, 24);
    /**
     * radio 大小
     */
    public final static Dimension RADIO_SIZE = new Dimension(1300, 60);
    /**
     * 高级选项面板Item 大小
     */
    public final static Dimension PANEL_ITEM_SIZE = new Dimension(1300, 40);

}