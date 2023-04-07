package org.systemA.util;

import javax.swing.*;

public class Styles {
    public static JFrame setStyle(JFrame jFrame) {
        jFrame.setTitle("A院系教务系统");
        jFrame.setSize(500, 600);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setResizable(true);
        return jFrame;
    }
}
