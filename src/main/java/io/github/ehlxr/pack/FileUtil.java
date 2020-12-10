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

package io.github.ehlxr.pack;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {
    private static int sumError = 0;
    private static int sumExistError = 0;
    private static int sumNotFoundError = 0;
    private static int sumSuccess = 0;
    private static int sumNum = 1;

    private static String getDate(Date date) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.format(date);
    }

    private static BufferedReader getBufferedReader(String path) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(path)));
    }

    public static void setInfo(String info, JFrame root) {
        sumError += 1;
        info.equals("");

        Component[] components = root.getRootPane().getContentPane().getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i].getClass().toString().equals("class javax.swing.JScrollPane")) {
                JTextArea textarea = (JTextArea) ((JScrollPane) components[i]).getViewport().getView();
                if (info.equals("")) {
                    sumError = 0;
                    sumExistError = 0;
                    sumNotFoundError = 0;
                    sumSuccess = 0;
                    sumNum = 1;
                    textarea.setText("");
                } else if ((textarea.getText().equals("")) || (textarea.getText() == null)) {
                    textarea.setText(sumNum + ". " + info);
                } else {
                    textarea.setText(textarea.getText() + "\n" + sumNum + ". " + info);
                }
            }
        }
    }

    private static boolean copy(String from, String dirPath, JFrame root) {
        boolean isCommon = true;
        File fromFile = new File(from);
        if (!fromFile.exists()) {
            sumExistError += 1;
            setInfo(from + "-------未找到！", root);

            System.out.println(from + "---------------未找到！");
            return false;
        }
        makeDirs(dirPath);
        try {
            File toFile = new File(dirPath + File.separatorChar + fromFile.getName());
            if (toFile.exists()) {
                sumNotFoundError += 1;
                int k = checkFileVersion(fromFile, toFile);
                if (k == -1) {
                    setInfo(fromFile.getAbsolutePath() + "--输出失败（已存在！）", root);
                    System.out.println(
                            "文件版本在目标版本之前，处理为不覆盖！若要处理请人工处理！\n原文件:" + fromFile.getAbsolutePath() + "\n目标文件:" + toFile.getAbsolutePath());
                    JOptionPane jp = new JOptionPane();
                    jp.setBounds(new Rectangle(new Point(400, 400)));

                    int isYes = JOptionPane.showConfirmDialog(root, "发现相同的文件，文件版本在目标版本之前！是否要进行覆盖？\n当前文件：" +

                            fromFile.getAbsolutePath() + "，修改日期:" + getDate(new Date(fromFile.lastModified())) + "\n目标文件："
                            + toFile.getAbsolutePath() + "，修改日期:" + getDate(new Date(toFile.lastModified())));
                    if (isYes == 0) {
                        isCommon = false;
                        System.out.println("您选择了是！");
                    } else {
                        return false;
                    }
                }
                if (k == 0) {
                    setInfo(fromFile.getAbsolutePath() + "--输出失败（已存在）", root);
                    System.out
                            .println("相同文件重复，处理为不覆盖！若要处理请人工处理！\n原文件:" + fromFile.getAbsolutePath() + "\n目标文件:" + toFile.getAbsolutePath());
                    return true;
                }
                if (k == 1) {
                    isCommon = false;
                }
            } else if (!toFile.exists()) {
                toFile.createNewFile();
                isCommon = false;
            }
            if (!isCommon) {
                InputStream is = new FileInputStream(fromFile);
                OutputStream out = new FileOutputStream(toFile);
                byte[] b = new byte[1024];
                int len = -1;
                while ((len = is.read(b)) != -1) {
                    out.write(b, 0, len);
                }
                out.flush();
                out.close();
                is.close();
                toFile.setLastModified(fromFile.lastModified());
                sumSuccess += 1;
                return true;
            }
        } catch (Exception e) {
            System.out.println("Copy Error!");
        }
        return false;
    }

    private static void makeDirs(String path) {
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    private static int checkFileVersion(File file1, File file2) {
        long file1LastTime = file1.lastModified();
        long file2LastTime = file2.lastModified();
        if (file1LastTime > file2LastTime) {
            return 1;
        }
        if (file1LastTime < file2LastTime) {
            return -1;
        }
        return 0;
    }

    public static boolean becomePackage(String fileList, String cutStr, String dir, JFrame root) throws Exception {
        dir = dir + "\\";

        String filePath = null;
        String addStr = null;
        String fromFile = null;
        String toFile = null;
        boolean flag = false;
        try {
            BufferedReader br = getBufferedReader(fileList);
            addStr = br.readLine();
            addStr = addStr.trim();
            setInfo("", root);
            while ((filePath = br.readLine()) != null) {
                sumNum += 1;
                if (!"".equals(filePath.trim())) {
                    filePath = filePath.replaceAll("/", "\\\\");

                    System.out.println(filePath.replaceAll("\\\\", "/"));
                    if (filePath.startsWith(cutStr)) {
                        fromFile = filePath.trim();
                        toFile = dir + addStr + File.separatorChar + getCenter(cutStr, fromFile);
                        flag = copy(fromFile, toFile, root);
                    } else {
                        fromFile = cutStr + File.separatorChar + filePath.trim();
                        toFile = dir + addStr + File.separatorChar + filePath.substring(0, filePath.trim().lastIndexOf("\\"));

                        flag = copy(fromFile, toFile, root);
                    }
                }
            }
            br.close();
            setInfo("----成功：" + sumSuccess + "\n" + "----失败：" + sumError + "\n" + "--------未找到：" + sumNotFoundError + "\n" + "--------已存在:"
                    + sumExistError, root);
            return flag;
        } catch (FileNotFoundException e) {
            System.out.println("列表文件没有找到！");
            throw new Exception("列表文件没有找到！");
        } catch (IOException e) {
            System.out.println("列表文件读取出错！");
            throw new Exception("列表文件读取出错！");
        }
    }

    private static String getCenter(String flag, String message) {
        int k1 = message.trim().indexOf(flag);
        int k2 = message.trim().lastIndexOf("\\");
        if ((k1 != -1) && (k2 != -1)) {
            return message.substring(flag.length() + 1, k2 + 1);
        }
        return null;
    }
}