package org.systemB.util;

import org.systemB.ui.UiConsts;

import java.io.*;
import java.util.Properties;

public class PropertyUtil {

    /**
     * 获取property
     *
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        Properties pps = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(UiConsts.CURRENT_DIR + File.separator + "config" + File.separator
                    + "zh-cn.properties"));
            pps.load(in);
            String value = pps.getProperty(key);
            return value;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
