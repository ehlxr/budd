package io.github.ehlxr;

import java.io.*;

/*
 * 替换文件（如果该文件含有子目录，则包括子目录所有文件）中某个字符串并写入新内容（Java代码实现）.
 *
 *原理：逐行读取源文件的内容，一边读取一边同时写一个*.tmp的文件。
 *当读取的行中发现有需要被替换和改写的目标内容‘行’时候，用新的内容‘行’替换之。
 *最终，删掉源文件，把*.tmp的文件重命名为源文件名字。
 *
 *注意！代码功能是逐行读取一个字符串，然后检测该字符串‘行’中是否含有替换的内容，有则用新的字符串‘行’替换源文件中该处整个字符串‘行’。没有则继续读。
 *注意！替换是基于‘行’，逐行逐行的替换！
 *
 * */
public class Modify {

    private final String target;
    private final String newContent;
    private final String path;

    public Modify(String path, String target, String newContent) {
        // 操作目录。从该目录开始。该文件目录下及其所有子目录的文件都将被替换。
        this.path = path;
        // target:需要被替换、改写的内容。
        this.target = target;
        // newContent:需要新写入的内容。
        this.newContent = newContent;

        operation();
    }

    public static void main(String[] args) {
        //代码测试：假设有一个test文件夹，test文件夹下含有若干文件或者若干子目录，子目录下可能也含有若干文件或者若干子目录（意味着可以递归操作）。
        //把test目录下以及所有子目录下（如果有）中文件含有"hi"的字符串行替换成新的"hello,world!"字符串行。
        new Modify("/Users/ehlxr/WorkSpaces/budd/src/main/java/me/ehlxr/test.txt", "hi", "hello,world!");
    }

    private void operation() {
        File file = new File(path);
        opeationDirectory(file);
    }

    private void opeationDirectory(File file) {
        if (file.isFile()) {
            operationFile(file);
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    opeationDirectory(f);
                }
            }
        }

    }

    private void operationFile(File file) {

        try {
            InputStream is = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is));

            String filename = file.getName();
            // tmpfile为缓存文件，代码运行完毕后此文件将重命名为源文件名字。
            File tmpfile = new File(file.getParentFile().getAbsolutePath() + "\\" + filename + ".tmp");

            BufferedWriter writer = new BufferedWriter(new FileWriter(tmpfile));

            boolean flag = false;
            String str;
            while (true) {
                str = reader.readLine();

                if (str == null)
                    break;

                if (str.contains(target)) {
                    writer.write(newContent + "\n");

                    flag = true;
                } else
                    writer.write(str + "\n");
            }

            is.close();

            writer.flush();
            writer.close();

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
