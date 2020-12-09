package io.github.ehlxr.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestReadFile {

    public static void readFile() {
        File file = new File("C:/Users/ehlxr/Desktop/IPB.txt");
        BufferedReader reader = null;
        Map<String, Object> resultMap = null;
        List<Long> startList = null;
        List<Long> endList = null;
        try {
            resultMap = new HashMap<String, Object>();
            startList = new ArrayList<Long>();
            endList = new ArrayList<Long>();

            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                String[] tempArr = tempString.split("	");

                resultMap.put(tempArr[0], tempArr);
                startList.add(Long.parseLong(tempArr[0]));
                endList.add(Long.parseLong(tempArr[1]));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        long des = 17301504l;
        long key = binarySearch(startList.toArray(new Long[startList.size()]), des);
        String[] tempArr = (String[]) resultMap.get(key);
        for (int i = 0; i < tempArr.length; i++) {
            System.out.println(tempArr[i]);
        }
    }

    public static void readFile1() {
        File file = new File("C:\\Users\\ehlxr\\Desktop\\白名单\\IMEI\\000000_0");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                System.out.println(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public static void main(String[] args) {
        readFile1();
    }

    /**
     * * 二分查找算法 * *
     *
     * @param srcArray 有序数组 *
     * @param des      查找元素 *
     * @return des的数组下标，没找到返回-1
     */
    public static long binarySearch(Long[] srcArray, long des) {
        int low = 0;
        int high = srcArray.length - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (des == srcArray[middle]) {
                return middle;
            } else if (des < srcArray[middle]) {
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }
        return -1;
    }
}
