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
 * Created by ehlxr on 2017/11/17.
 * <p>
 * 递归扫描 Java 文件，添加 CopyRight 信息，已有跳过
 */
public class ContentReplace {
    private static int total = 0;
    private static int unDeal = 0;

    public static void main(String[] args) throws IOException {
        File dir = new File("/Users/ehlxr/WorkSpaces/Budd");
        deal(dir);
        System.out.println("总文件数：" + total);
        System.out.println("未处理文件数：" + unDeal);
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
            ++total;
            // System.out.println(file.getPath());

            File tmp = File.createTempFile("tmp", null);
            try (
                    FileOutputStream tmpOut = new FileOutputStream(tmp);
                    FileInputStream tmpIn = new FileInputStream(tmp);
                    RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
                byte[] buf = new byte[64];
                int hasRead;
                while ((hasRead = raf.read(buf)) > 0) {
                    if (new String(buf).contains("Copyright")) {
                        ++unDeal;
                        System.out.println("未处理文件：" + file.getPath());
                        return;
                    }
                    // 把原有内容读入临时文件
                    tmpOut.write(buf, 0, hasRead);
                }
                raf.seek(0L);
                String tmpl = "/*\n" +
                        " * The MIT License (MIT)\n" +
                        " *\n" +
                        " * Copyright © 2020 xrv <xrg@live.com>\n" +
                        " *\n" +
                        " * Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
                        " * of this software and associated documentation files (the \"Software\"), to deal\n" +
                        " * in the Software without restriction, including without limitation the rights\n" +
                        " * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                        " * copies of the Software, and to permit persons to whom the Software is\n" +
                        " * furnished to do so, subject to the following conditions:\n" +
                        " *\n" +
                        " * The above copyright notice and this permission notice shall be included in\n" +
                        " * all copies or substantial portions of the Software.\n" +
                        " *\n" +
                        " * THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                        " * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                        " * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
                        " * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                        " * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                        " * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN\n" +
                        " * THE SOFTWARE.\n" +
                        " */\n\n";
                raf.write(tmpl.getBytes());
                // raf.write("/*\n".getBytes());
                // raf.write(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + File.separator + "LICENSE")));
                // raf.write("\n*/\n\n".getBytes());

                // 追加临时文件内容
                while ((hasRead = tmpIn.read(buf)) > 0) {
                    raf.write(buf, 0, hasRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}