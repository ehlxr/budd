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

import java.util.Stack;

/**
 * @author ehlxr
 * @since 2021-04-04 14:19.
 */
public class SingleLinkedListDemo {
    public static void main(String[] args) {
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(3, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(5, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(8, "林冲", "豹子头");

        SingleLinkedList singleLinkedList = new SingleLinkedList();

        // 加入
        // singleLinkedList.add(hero1);
        // singleLinkedList.add(hero4);
        // singleLinkedList.add(hero2);
        // singleLinkedList.add(hero3);

        singleLinkedList.addByOrder(hero1);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero2);
        singleLinkedList.addByOrder(hero3);
        singleLinkedList.list();

        // 反转链表
        // reverseList(singleLinkedList.getHead());
        // System.out.println("反转之后的链表");
        // singleLinkedList.list();
        //
        // HeroNode newHeroNode = new HeroNode(3, "小吴", "智多星..");
        // singleLinkedList.update(newHeroNode);
        // System.out.println("修改后的链表");
        // singleLinkedList.list();
        //
        // singleLinkedList.del(3);
        // System.out.println("删除 no 为 3 的节点");
        // singleLinkedList.list();

        SingleLinkedList singleLinkedList2 = new SingleLinkedList();
        singleLinkedList2.addByOrder(new HeroNode(2, "2", "2"));
        singleLinkedList2.addByOrder(new HeroNode(3, "3", "3"));
        singleLinkedList2.addByOrder(new HeroNode(8, "8", "8"));
        singleLinkedList2.addByOrder(new HeroNode(9, "9", "9"));
        singleLinkedList2.list();

        bind2(singleLinkedList.getHead(), singleLinkedList2.getHead());

    }

    /**
     * 反转链表
     */
    public static void reverseList(HeroNode head) {
        HeroNode pre = null;
        HeroNode cur = head.next;
        while (cur != null) {
            HeroNode next = cur.next;

            cur.next = pre;

            pre = cur;

            cur = next;
        }

        head.next = pre;
    }

    /**
     * 合并有序链表
     */
    public static void bind2(HeroNode node1, HeroNode node2) {
        Stack<HeroNode> stack = new Stack<>();

        HeroNode cur = node1.next;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        cur = node2.next;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        SingleLinkedList result = new SingleLinkedList();
        for (HeroNode node : stack) {
            result.addByOrder(node);
        }

        // 打印结果
        System.out.println("合并结果");
        result.list();
    }

    /**
     * 合并有序链表
     */
    public static void bind(HeroNode node1, HeroNode node2) {
        SingleLinkedList result = new SingleLinkedList();

        if (node1.next == null) {
            result.add(node2.next);
            return;
        }
        if (node2.next == null) {
            result.add(node1.next);
            return;
        }
        while (node1.next != null || node2.next != null) {
            HeroNode temp = null;
            if (node1.next == null) {
                result.add(node2.next);
                break;
            }
            if (node2.next == null) {
                result.add(node1.next);
                break;
            }

            if (node1.next.no < node2.next.no) {
                temp = node1.next;
                node1.next = node1.next.next;
            } else {
                temp = node2.next;
                node2.next = node2.next.next;
            }

            temp.next = null;
            result.add(temp);
        }

        // 打印结果
        System.out.println("合并结果");
        result.list();
    }
}

class SingleLinkedList {
    private final HeroNode head = new HeroNode(0, "", "");

    public HeroNode getHead() {
        return head;
    }

    public void list() {
        HeroNode temp = head.next;
        while (temp != null) {
            System.out.println(temp);
            temp = temp.next;
        }
    }

    public void add(HeroNode heroNode) {
        HeroNode temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = heroNode;
    }

    public void addByOrder(HeroNode heroNode) {
        HeroNode temp = head;
        while (true) {
            if (temp.next == null) {
                break;
            }

            // 找到需要插入位置的前一个节点
            if (temp.next.no > heroNode.no) {
                break;
            }

            temp = temp.next;
        }

        heroNode.next = temp.next;
        temp.next = heroNode;
    }

    public void update(HeroNode newHeroNode) {
        HeroNode temp = head.next;
        if (temp == null) {
            System.out.println("链表为空！");
        }

        while (true) {
            if (temp == null) {
                System.out.printf("没找到 %d 的节点\n", newHeroNode.no);
                break;
            }

            if (temp.no == newHeroNode.no) {
                temp.name = newHeroNode.name;
                temp.nickname = newHeroNode.nickname;

                break;
            }

            temp = temp.next;
        }
    }

    public void del(int no) {
        HeroNode temp = head;
        if (temp.next == null) {
            System.out.println("链表为空！");
        }

        while (true) {
            if (temp.next == null) {
                System.out.printf("没找到要删除的 %d 节点\n", no);
                break;
            }

            // 找到待删除节点的前一个节点
            if (temp.next.no == no) {
                temp.next = temp.next.next;

                break;
            }

            temp = temp.next;
        }
    }
}

/**
 * 定义HeroNode ， 每个HeroNode 对象就是一个节点
 */
class HeroNode {
    public int no;
    public String name;
    public String nickname;
    public HeroNode next; //指向下一个节点

    // 构造器
    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    /**
     * 为了显示方法，我们重新 toString
     */
    @Override
    public String toString() {
        return "HeroNode [no=" + no + ", name=" + name + ", nickname=" + nickname + "]";
    }

}
