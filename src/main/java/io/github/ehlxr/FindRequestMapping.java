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
 */
public class FindRequestMapping {
    private static final int total = 0;
    private static final int unDeal = 0;

    public static void main(String[] args) throws IOException {
        File dir = new File("/Users/ehlxr/WorkSpaces/enncloud/ceres-epns/ceres-epns-web/src/main/java/com/ceres/epns/web");
        deal(dir);
    }

    private static void deal(File file) throws IOException {
        if (file.isDirectory()) {
            File[] fs = file.listFiles(((dir, name) -> {
                File f = new File(dir.getPath() + File.separator + name);

                return (f.getPath().contains("src") && name.endsWith(".java")) || f.isDirectory();
            }));

            for (File f : fs != null ? fs : new File[0]) {
                deal(f);
            }

        } else {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;

            while ((lineTxt = bufferedReader.readLine()) != null) {
                if (lineTxt.contains("@RequestMapping")) {
                    System.out.println(lineTxt);
                }
            }
            bufferedReader.close();
            read.close();
        }
    }
}