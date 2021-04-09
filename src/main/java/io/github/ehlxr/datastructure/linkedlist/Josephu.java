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

package io.github.ehlxr.datastructure.linkedlist;

/**
 * @author ehlxr
 * @since 2021-04-09 21:14.
 */
public class Josephu {
    public static void main(String[] args) {
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addBoy(5);
        circleSingleLinkedList.show();

        circleSingleLinkedList.countBoy(1, 2, 5);
    }
}

class CircleSingleLinkedList {
    private Boy first = null;

    public void addBoy(int nums) {
        if (nums < 1) {
            return;
        }
        Boy curBoy = null;
        for (int i = 1; i <= nums; i++) {
            Boy boy = new Boy(i);
            if (i == 1) {
                first = boy;
                curBoy = first;
                first.setNext(first);
            } else {
                curBoy.setNext(boy);
                boy.setNext(first);
                curBoy = boy;
            }
        }
    }

    public void show() {
        if (first == null) {
            System.out.println("没有boy");
            return;
        }

        Boy curBoy = first;
        while (true) {
            System.out.printf("boy no %d\n", curBoy.getNo());
            if (curBoy.getNext() == first) {
                break;
            }

            curBoy = curBoy.getNext();
        }
    }

    public void countBoy(int startNo, int countNum, int size) {
        if (first == null || startNo > size || startNo < 1) {
            return;
        }

        // 找到最后一个节点
        Boy helper = first;
        while (true) {
            if (helper.getNext() == first) {
                break;
            }
            helper = helper.getNext();
        }

        // 找到开始报数的位置
        for (int i = 0; i < startNo - 1; i++) {
            helper = helper.getNext();
            first = first.getNext();
        }

        while (true) {
            if (helper == first) {
                break;
            }

            for (int i = 0; i < countNum - 1; i++) {
                helper = helper.getNext();
                first = first.getNext();
            }
            System.out.printf("出圈的小孩 %d\n", first.getNo());

            first = first.getNext();
            helper.setNext(first);
        }
        System.out.printf("最后出圈的小孩 %d\n", first.getNo());
    }
}

class Boy {
    private int no;
    private Boy next;

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}