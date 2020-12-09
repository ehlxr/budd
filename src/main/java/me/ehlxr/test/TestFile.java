package me.ehlxr.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TestFile {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\ehlxr\\Desktop\\20160628\\161845");
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                String[] tempArr = tempString.split("\\|");
                long time = Long.parseLong(tempArr[0]);
                long rang = 1467100800000L;
                if (time < rang) {
                    System.out.println(tempString);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

    }
}
