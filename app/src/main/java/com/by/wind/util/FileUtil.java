package com.by.wind.util;

import com.by.wind.component.net.PreferenceHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wind on 17/4/27.
 */

public class FileUtil {

    public static void clearCache() {
        PreferenceHelper.saveUserToken(null);
        PreferenceHelper.setIsLogin(false);
    }

    /**
     * 文件复制
     *
     * @param oldFilePath
     * @param newFilePath
     * @return
     * @throws IOException
     */
    public static boolean copy(String oldFilePath, String newFilePath) throws IOException {
        //如果原文件不存在
        if (!fileExists(oldFilePath)) {
            return false;
        }
        //获得原文件流
        FileInputStream inputStream = new FileInputStream(new File(oldFilePath));
        byte[] data = new byte[1024];
        //输出流
        FileOutputStream outputStream = new FileOutputStream(new File(newFilePath));
        //开始处理流
        while (inputStream.read(data) != -1) {
            outputStream.write(data);
        }
        inputStream.close();
        outputStream.close();
        return true;
    }

    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
}
