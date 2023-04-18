package org.systemB.ui.panel;

import org.systemB.App;
import org.systemB.ui.UiConsts;
import org.systemB.ui.component.MyIconButton;
import org.systemB.util.PropertyUtil;

import javax.swing.*;
import java.awt.*;

/**
 * 工具栏面板
 *
 * @author Bob
 */
public class ToolBarPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static MyIconButton buttonPersonalInfo; // 个人信息
    private static MyIconButton buttonViewCourse; // 查看课程
    private static MyIconButton buttonChooseCourse; // 选择课程
    private static MyIconButton buttonDeleteCourse; // 退选课程



    /**
     * 构造
     */
    public ToolBarPanel() {
        initialize();
        addButtion();
    }

    /**
     * 初始化
     */
    private void initialize() {
        Dimension preferredSize = new Dimension(60, UiConsts.MAIN_WINDOW_HEIGHT);
        this.setPreferredSize(preferredSize);
        this.setMaximumSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        this.setLayout(new GridLayout(2, 1));
    }

    /**
     * 添加工具按钮
     */
    private void addButtion() {
        JPanel panelUp = new JPanel();
        panelUp.setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(-2, -2, -4));
        JPanel panelDown = new JPanel();
        panelDown.setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        panelDown.setLayout(new BorderLayout(0, 0));

        this.add(panelUp);
        this.add(panelDown);

    }

    /**
     * 为各按钮添加事件动作监听
     */
    private void addListener() {
        buttonPersonalInfo.addActionListener(e -> {
            buttonPersonalInfo.setIcon(UiConsts.ICON_PERSONAL_INFO_HOVER);
            buttonViewCourse.setIcon(UiConsts.ICON_VIEW_COURSE);
            buttonChooseCourse.setIcon(UiConsts.ICON_CHOOSE_COURSE);
            buttonDeleteCourse.setIcon(UiConsts.ICON_DROP_COURSE);
            App.mainPanelCenter.removeAll();
            App.personalInfo = new PersonalInfo(App.user);
            App.mainPanelCenter.add(App.personalInfo, BorderLayout.CENTER);
            App.mainPanelCenter.updateUI();
        });
        buttonViewCourse.addActionListener(e -> {
            buttonPersonalInfo.setIcon(UiConsts.ICON_PERSONAL_INFO);
            buttonViewCourse.setIcon(UiConsts.ICON_VIEW_COURSE_HOVER);
            buttonChooseCourse.setIcon(UiConsts.ICON_CHOOSE_COURSE);
            buttonDeleteCourse.setIcon(UiConsts.ICON_DROP_COURSE);
            App.mainPanelCenter.removeAll();
            App.viewCourse = new ViewCourse(App.user);
            App.mainPanelCenter.add(App.viewCourse, BorderLayout.CENTER);
            App.mainPanelCenter.updateUI();
        });
        buttonChooseCourse.addActionListener(e -> {
            buttonPersonalInfo.setIcon(UiConsts.ICON_PERSONAL_INFO);
            buttonViewCourse.setIcon(UiConsts.ICON_VIEW_COURSE);
            buttonChooseCourse.setIcon(UiConsts.ICON_CHOOSE_COURSE_HOVER);
            buttonDeleteCourse.setIcon(UiConsts.ICON_DROP_COURSE);
            App.mainPanelCenter.removeAll();
            App.chooseCourse = new ChooseCourse(App.user);
            App.mainPanelCenter.add(App.chooseCourse, BorderLayout.CENTER);
            App.mainPanelCenter.updateUI();
        });
        buttonDeleteCourse.addActionListener(e -> {
            buttonPersonalInfo.setIcon(UiConsts.ICON_PERSONAL_INFO);
            buttonViewCourse.setIcon(UiConsts.ICON_VIEW_COURSE);
            buttonChooseCourse.setIcon(UiConsts.ICON_CHOOSE_COURSE);
            buttonDeleteCourse.setIcon(UiConsts.ICON_DROP_COURSE_HOVER);
            App.mainPanelCenter.removeAll();
            App.deleteCourse = new DeleteCourse(App.user);
            App.mainPanelCenter.add(App.deleteCourse, BorderLayout.CENTER);
            App.mainPanelCenter.updateUI();
        });
    }


    /**
     * 添加并显示按钮
     */
    public void showButtonForStudent() {
        this.removeAll();
        // 个人信息按钮
        buttonPersonalInfo = new MyIconButton(UiConsts.ICON_PERSONAL_INFO_HOVER, UiConsts.ICON_PERSONAL_INFO_HOVER,
                UiConsts.ICON_PERSONAL_INFO, PropertyUtil.getProperty("ds.ui.status.title"));
        // 查看课程按钮
        buttonViewCourse = new MyIconButton(UiConsts.ICON_VIEW_COURSE_HOVER, UiConsts.ICON_VIEW_COURSE_HOVER,
                UiConsts.ICON_VIEW_COURSE, PropertyUtil.getProperty("ds.ui.status.title"));
        // 选择课程按钮
        buttonChooseCourse = new MyIconButton(UiConsts.ICON_CHOOSE_COURSE_HOVER, UiConsts.ICON_CHOOSE_COURSE_HOVER,
                UiConsts.ICON_CHOOSE_COURSE, PropertyUtil.getProperty("ds.ui.status.title"));
        // 退选课程按钮
        buttonDeleteCourse = new MyIconButton(UiConsts.ICON_DROP_COURSE_HOVER, UiConsts.ICON_DROP_COURSE_HOVER,
                UiConsts.ICON_DROP_COURSE, PropertyUtil.getProperty("ds.ui.status.title"));

        JPanel panelUp = new JPanel();
        panelUp.setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(-2, -2, -4));
        JPanel panelDown = new JPanel();
        panelDown.setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        panelDown.setLayout(new BorderLayout(0, 0));

        panelUp.add(buttonPersonalInfo);
        panelUp.add(buttonViewCourse);
        panelUp.add(buttonChooseCourse);
        panelUp.add(buttonDeleteCourse);
        System.out.println(panelUp.getComponentCount());

        this.add(panelUp);
        this.add(panelDown);

        addListener();


    }

}
