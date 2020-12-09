package me.ehlxr;

/**
 * Created by ehlxr on 2018/2/13.
 */
public class PrintMatrixClockWisely {
    /**
     * 题目：输入一个矩阵，按照从外向里以顺时针的顺序打印出每一个数字。
     * 思路：
     * 循环打印:
     * 1：先打印一行（第 1 行肯定会打印）
     * 2：再打印当前矩阵的最后一列
     * 3：再倒序打印当前矩阵的最后一行
     * 4：再倒序打印当前矩阵的第一列
     * 起始坐标的规律：
     * (0,0),(1,1),(2,2)...(startX,startY), 起始坐标的两个坐标值相等。
     * 并且 startX<= (rows-1)/2,startY<=(columns-1)/2
     * 当前矩阵，第 1 行 坐标 (start,columns-1-start)      =>   (start,endX)
     * 当前矩阵，最后 1 列 坐标 (start+1,rows-1-start)  =>   (start+1,endY)
     * 当前矩阵，最后 1 行 坐标 (start,columns-1-start+1)   =>   (start,endX-1)
     * 当前矩阵，第 1 行 坐标 (start+1,columns-1+1)        =>   (start+1,endY-1)
     *
     * @author WangSai
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // 初始化矩阵 arr[][]
        int[][] arr = new int[5][5];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = i * arr[i].length + j;
                System.out.print(arr[i][j] + " " + '\t');
            }
            System.out.println();
        }
        System.out.println("顺时针打印矩阵：");
        // 顺时针打印矩阵 arr[][]
        printMatrixWisely(arr);
    }

    // 循环打印
    private static void printMatrixWisely(int[][] arr) {
        if (arr == null || arr.length < 1 || arr[0].length < 1)
            return;
        int start = 0;
        int rows = arr.length;
        int columns = arr[0].length;
        while (2 * start < columns && 2 * start < rows) {
            printMatrix(arr, rows, columns, start);
            start++;
        }
    }

    // 打印一圈
    private static void printMatrix(int[][] arr, int rows, int columns, int start) {
        int endX = columns - 1 - start; // 最后一列的列号
        int endY = rows - 1 - start; // 最后一行的行号
        // 打印该圈第一行
        for (int i = start; i <= endX; i++)
            System.out.print(arr[start][i] + " ");
        // 打印该圈最后一列 （至少是两行）
        if (start < endY)
            for (int i = start + 1; i <= endY; i++)
                System.out.print(arr[i][endX] + " ");
        // 打印该圈最后一行 （至少是两行两列）
        if ((start < endX) && (start < endY))
            for (int i = endX - 1; i >= start; i--)
                System.out.print(arr[endY][i] + " ");
        // 打印该圈的第一列 （至少是三行两列）
        if ((start < endX) && (start < endY - 1))
            for (int i = endY - 1; i >= start + 1; i--)
                System.out.print(arr[i][start] + " ");
    }
}
