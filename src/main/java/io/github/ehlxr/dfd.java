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

/**
 * Created by ehlxr on 2016/12/23.
 */
public class dfd {

    public static void main(String[] args) {
        // var map = Maps.newHashMap();
        // map.put("d", 1);
        // System.out.println(map);
    }

    public void printCircle(int[][] matrix, int startX, int startY, int endX, int endY) {
        // only one column left
        if (startY == endY) {
            for (int i = startX; i <= endX; i++) {
                System.out.println(matrix[i][endY]);
            }
            return;
        }
        // only one row left
        if (startX == endX) {
            for (int i = startY; i <= endY; i++) {
                System.out.println(matrix[startX][i]);
            }
            return;
        }
        for (int i = startY; i < endY; i++) {
            System.out.println(matrix[startX][i]);
        }
        for (int i = startX; i < endX; i++) {
            System.out.println(matrix[i][endY]);
        }
        for (int i = endY; i > startY; i--) {
            System.out.println(matrix[endX][i]);
        }
        for (int i = endX; i > startX; i--) {
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
