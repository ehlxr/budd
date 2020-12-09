package me.ehlxr;

import java.io.*;

/**
 * @author ehlxr
 * @date 2017/3/27
 */
public class Rename {
    public static void main(String[] args) {
        File dir = new File("/Users/ehlxr/ehlxr/blog/posts");

        File[] files = dir.listFiles();
        if (null == files || files.length <= 0) {
            System.out.println("sources is null!");
            return;
        }
        int count = 0;
        for (File file : files) {
            try {
                String oName = file.getName();

                String date = oName.substring(oName.lastIndexOf("-201") + 1, oName.indexOf(".md"));
                String title = oName.substring(0, oName.lastIndexOf("-201"));

                String nName = date + "-" + title + ".md";

                copyFileUsingFileStreams(file, new File("/Users/ehlxr/Desktop/post/" + nName));
                count++;
            } catch (Exception e) {
                System.out.println("exce file [ " + file.getName() + " ] error, reason: " + e.getMessage());
            }
        }
        System.out.println("complete: " + count);
    }

    private static void copyFileUsingFileStreams(File source, File dest)
            throws IOException {
        try (InputStream input = new FileInputStream(source); OutputStream output = new FileOutputStream(dest)) {
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        }
    }
}
