package me.ehlxr;

import java.io.*;

/**
 * Created by lixiangrong on 2017/3/27.
 */
public class Rename {
    public static void main(String[] args) {
        File dir = new File("/Users/ehlxr/Desktop/_posts/");

        File[] files = dir.listFiles();
        for (File file : files) {
            try {
                String oName = file.getName();

                String date = oName.substring(oName.lastIndexOf("-201") + 1, oName.indexOf(".md"));
                String title = oName.substring(0, oName.lastIndexOf("-201"));

                String nName = date + "-" + title + ".md";


                copyFileUsingFileStreams(file, new File("/Users/ehlxr/Desktop/posts/" + nName));
            } catch (Exception e) {
                continue;
            }
        }
    }

    private static void copyFileUsingFileStreams(File source, File dest)
            throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }


}
