package com.icbc.digitalhuman.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils {

    public static void saveDialog(String content, String username) {
        try {
            String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String filePath = "./" + currentDate + "_" + username + ".txt";
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }

            try (BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(filePath, false), "UTF-8")
            )) {
                out.write(content + "\r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void appendToDialog(StringBuilder dialogBuilder, String sender, String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        dialogBuilder.append("[").append(timestamp).append("] ");
        dialogBuilder.append(sender).append(": ").append(message).append("\n");
    }
}