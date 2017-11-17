package me.ehlxr;

import java.io.*;

/**
 * Created by lixiangrong on 2017/11/17.
 */
public class ContentReplace {
    private static int total = 0;
    private static int unDeal = 0;

    public static void main(String[] args) throws IOException {
        File dir = new File("/Users/ehlxr/WorkSpaces/enncloud/Ceres");
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
                    if (new String(buf).startsWith("/*")) {
                        ++unDeal;
                        System.out.println("未处理文件：" + file.getPath());
                        return;
                    }
                    // 把原有内容读入临时文件
                    tmpOut.write(buf, 0, hasRead);
                }
                raf.seek(0L);
                String tmpl = "/*\n" +
                        " * Copyright 2017-2017 the original author or authors.\n" +
                        " *\n" +
                        " * Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                        " * you may not use this file except in compliance with the License.\n" +
                        " * You may obtain a copy of the License at\n" +
                        " *\n" +
                        " *      http://www.apache.org/licenses/LICENSE-2.0\n" +
                        " *\n" +
                        " * Unless required by applicable law or agreed to in writing, software\n" +
                        " * distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                        " * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                        " * See the License for the specific language governing permissions and\n" +
                        " * limitations under the License.\n" +
                        " */\n\n";
                raf.write(tmpl.getBytes());
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