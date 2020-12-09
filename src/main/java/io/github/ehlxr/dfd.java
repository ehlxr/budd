package io.github.ehlxr;

/**
 * Created by ehlxr on 2016/12/23.
 */
public class dfd {

    public static void main(String[] args) {
        // var map = Maps.newHashMap();
        // map.put("d",1);
        // System.out.println(map);
    }
    public void printCircle(int[][] matrix, int startX, int startY, int endX, int endY) {
        // only one column left
        if (startY == endY) {
            for (int i = startX; i <= endX; i++ ) {
                System.out.println(matrix[i][endY]);
            }
            return;
        }
        // only one row left
        if (startX == endX) {
            for (int i = startY; i <= endY; i++ ) {
                System.out.println(matrix[startX][i]);
            }
            return;
        }
        for (int i = startY; i < endY; i++ ) {
            System.out.println(matrix[startX][i]);
        }
        for (int i = startX; i < endX; i++ ) {
            System.out.println(matrix[i][endY]);
        }
        for (int i = endY; i > startY; i-- ) {
            System.out.println(matrix[endX][i]);
        }
        for (int i = endX; i > startX; i-- ) {
            System.out.println(matrix[i][startY]);
        }

    }

    public void printMatrix(int[][] matrix) {

        if (matrix == null) {
            return;
        }
        int startX = 0;
        int startY = 0;
        int endY = matrix[0].length - 1;
        int endX = matrix.length - 1;

        while ((startX <= endX) && (startY <= endY)) {
            printCircle(matrix, startX, startY, endX, endY);
            startX++;
            startY++;
            endX--;
            endY--;
        }
    }
}
