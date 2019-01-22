package me.ehlxr;

import java.io.*;


/**
 * @author ehlxr
 */
public class FindRequestMapping {
    private static int total = 0;
    private static int unDeal = 0;

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