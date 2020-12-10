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

package io.github.ehlxr.hbase;

import io.github.ehlxr.utils.Pair;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Arrays;
import java.util.List;

/**
 * @author ehlxr
 * @since 2020/2/26.
 */
public class Main {
    private static final String TABLE_NAME = "class";
    private static final String TEACHER = "teacher";
    private static final String STUDENT = "student";

    public static void main(String[] args) {
        createTable();
        insertData();
        getScanner();
        // deleteTable();
    }


    private static void createTable() {
        // 新建表
        List<String> columnFamilies = Arrays.asList(TEACHER, STUDENT);
        boolean table = HBaseUtils.createTable(TABLE_NAME, columnFamilies);
        System.out.println("表创建结果:" + table);
    }

    private static void insertData() {
        List<Pair<String, String>> pairs1 = Arrays.asList(new Pair<>("name", "Tom"),
                new Pair<>("age", "22"),
                new Pair<>("gender", "1"));
        HBaseUtils.putRow(TABLE_NAME, "rowKey1", STUDENT, pairs1);

        List<Pair<String, String>> pairs2 = Arrays.asList(new Pair<>("name", "Jack"),
                new Pair<>("age", "33"),
                new Pair<>("gender", "2"));
        HBaseUtils.putRow(TABLE_NAME, "rowKey2", STUDENT, pairs2);

        List<Pair<String, String>> pairs3 = Arrays.asList(new Pair<>("name", "Mike"),
                new Pair<>("age", "44"),
                new Pair<>("gender", "1"));
        HBaseUtils.putRow(TABLE_NAME, "rowKey3", STUDENT, pairs3);
    }


    private static void getRow() {
        Result result = HBaseUtils.getRow(TABLE_NAME, "rowKey1");
        if (result != null) {
            System.out.println(Bytes
                    .toString(result.getValue(Bytes.toBytes(STUDENT), Bytes.toBytes("name"))));
        }

    }

    private static void getCell() {
        String cell = HBaseUtils.getCell(TABLE_NAME, "rowKey2", STUDENT, "age");
        System.out.println("cell age :" + cell);

    }

    private static void getScanner() {
        ResultScanner scanner = HBaseUtils.getScanner(TABLE_NAME);
        if (scanner != null) {
            scanner.forEach(result -> System.out.println(Bytes.toString(result.getRow()) + "->" + Bytes
                    .toString(result.getValue(Bytes.toBytes(STUDENT), Bytes.toBytes("name")))));
            scanner.close();
        }
    }


    private static void getScannerWithFilter() {
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        SingleColumnValueFilter nameFilter = new SingleColumnValueFilter(Bytes.toBytes(STUDENT),
                Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL, Bytes.toBytes("Jack"));
        filterList.addFilter(nameFilter);
        ResultScanner scanner = HBaseUtils.getScanner(TABLE_NAME, filterList);
        if (scanner != null) {
            scanner.forEach(result -> System.out.println(Bytes.toString(result.getRow()) + "->" + Bytes
                    .toString(result.getValue(Bytes.toBytes(STUDENT), Bytes.toBytes("name")))));
            scanner.close();
        }
    }

    private static void deleteColumn() {
        boolean b = HBaseUtils.deleteColumn(TABLE_NAME, "rowKey2", STUDENT, "age");
        System.out.println("删除结果: " + b);
    }

    private static void deleteRow() {
        boolean b = HBaseUtils.deleteRow(TABLE_NAME, "rowKey2");
        System.out.println("删除结果: " + b);
    }

    private static void deleteTable() {
        boolean b = HBaseUtils.deleteTable(TABLE_NAME);
        System.out.println("删除结果: " + b);
    }
}
