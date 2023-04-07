package org.systemA;

import javax.swing.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.systemA.ui.UiConsts;
import org.systemA.ui.panel.ToolBarPanel;

import java.awt.*;

/**
 * 程序入口，主窗口Frame
 *
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private JFrame frame;

    public static JPanel mainPanelCenter;

    /**
     * 程序入口main
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                App window = new App();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 构造函数，创建APP
     */
    public App() {
        initialize();
    }

    /**
     * 初始化frame内容
     */
    private void initialize() {
        logger.info("==================AppInitStart");
        // 设置系统默认样式
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                 | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // 初始化主窗口
        frame = new JFrame();
        frame.setBounds(UiConsts.MAIN_WINDOW_X, UiConsts.MAIN_WINDOW_Y, UiConsts.MAIN_WINDOW_WIDTH,
                UiConsts.MAIN_WINDOW_HEIGHT);
        frame.setTitle(UiConsts.APP_NAME);
        frame.setIconImage(UiConsts.IMAGE_ICON);
        frame.setBackground(UiConsts.MAIN_BACK_COLOR);
        JPanel mainPanel = new JPanel(true);
        mainPanel.setBackground(Color.white);
        mainPanel.setLayout(new BorderLayout());

        ToolBarPanel toolbar = new ToolBarPanel();

        mainPanel.add(toolbar, BorderLayout.WEST);

        mainPanelCenter = new JPanel(true);
        mainPanelCenter.setLayout(new BorderLayout());
        mainPanel.add(mainPanelCenter, BorderLayout.CENTER);
        frame.add(mainPanel);
        // 关闭窗口时退出程序
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logger.info("==================AppInitEnd");
    }


}