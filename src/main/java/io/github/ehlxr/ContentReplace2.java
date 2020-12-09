package io.github.ehlxr;

import java.io.*;

@SuppressWarnings("ALL")
public class ContentReplace2 {
    public static void main(String[] args) {
        File file = new File("/Users/ehlxr/ehlxr/blog/hugoBlog/content/post");
        File[] files = file.listFiles();

        for (File f : files) {
            operationFile(f);
        }
    }

    private static void operationFile(File file) {
        File tmpfile = new File(file.getParentFile().getAbsolutePath() + "\\" + file.getName() + ".tmp");
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tmpfile))) {

            boolean flag = false;
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.startsWith("title")) {
                    System.out.println(line);
                    // StringBuilder sb = new StringBuilder();
                    // line = sb.append("title: \"").append(line.substring(line.indexOf("title") + 7)).append("\"").toString();
                    line = line.replaceAll("'", "");
                    System.out.println(line);

                    flag = true;
                }
                writer.write(line + "\n");
            }

            if (flag) {
                file.delete();
                tmpfile.renameTo(new File(file.getAbsolutePath()));
            } else {
                tmpfile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
