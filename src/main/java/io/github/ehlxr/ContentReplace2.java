/*
 * The MIT License (MIT)
 *
 * Copyright © 2020 xrv <xrg@live.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
