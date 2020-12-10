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
